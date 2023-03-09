package com.example.backendhvz.services.player;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public Player findById(Long playerId) {
        return playerRepository.findById(playerId).get();
    }

    @Override
    public Collection<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Collection<Player> findAll(Long gameId) {
        return playerRepository.findAllByGameId(gameId);
    }

    @Override
    public Object findByIdAndPlayerState(Long gameId, Long playerId, Long requestingPlayerId) {
        Player requestingPlayer = findById(requestingPlayerId);
        Player player = findById(playerId);
        if(requestingPlayer.getGame().getId() != gameId || player.getGame().getId() != gameId) return null;
        if(requestingPlayer.getState() == PlayerState.ADMINISTRATOR) {
            return playerMapper.playerToPlayerAdminDto(player);
        }
        return playerMapper.playerToPlayerDto(player);
    }

    @Override
    public Collection<Object> findAllByPlayerState(Long gameId, Long requestingPlayerId) {
        Player requestingPlayer = findById(requestingPlayerId);
        if(requestingPlayer.getGame().getId() != gameId) return null;

        if(requestingPlayer.getState() == PlayerState.ADMINISTRATOR) {
            return new ArrayList<>(playerMapper.playersToPlayerAdminDtos(findAll(gameId)));
        }
        return new ArrayList<>(playerMapper.playersToPlayerDtos(findAll(gameId)));
    }

    @Override
    public Player addNewPlayer(Long gameId, HvZUserDTO hvZUserDTO) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setGame(gameId);
        playerDTO.setUser(hvZUserDTO.getId());
        playerDTO.setHuman(true);
        String biteCode = generateBiteCode();
        while (playerRepository.existsPlayerByBiteCodeAndGame_Id(biteCode, gameId))
            biteCode = generateBiteCode();
        playerDTO.setBiteCode(biteCode);
        // TODO: This should be PlayerState.UNREGISTERED (?) later
        playerDTO.setState(PlayerState.NO_SQUAD);
        Player player = playerMapper.playerDtoToPlayer(playerDTO);
        return playerRepository.save(player);
    }

    public static String generateBiteCode() {
        SecureRandom sr = new SecureRandom();
        char[] text = new char[3];
        String characters = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890";
        for (int i = 0; i < 3; i++)
        {
            text[i] = characters.charAt(sr.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Override
    public Player add(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player update(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player, Long updatingPlayerId) {
        Player updatingPlayer = findById(updatingPlayerId);
        if(updatingPlayer.getState() != PlayerState.ADMINISTRATOR) return null;
        return playerRepository.save(player);
    }

    @Override
    public void deleteById(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    // TODO: This should be cascading
    @Override
    public void deletePlayerById(Long playerId, Long deletingPlayerId) {
        Player deletingPlayer = findById(deletingPlayerId);
        if(deletingPlayer.getState() != PlayerState.ADMINISTRATOR) return;
        playerRepository.deleteById(playerId);
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }
}
