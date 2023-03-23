package com.example.backendhvz.services.player;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerMapper playerMapper, GameRepository gameRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
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
    public Collection<Object> findAll(Long gameId) {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("game id " + gameId);

        return new ArrayList<>(playerMapper.playersToPlayerDtos(playerRepository.findAllByGameId(gameId)));
    }

    @Override
    public Object findByIdAndPlayerState(Long gameId, Long playerId) throws BadRequestException, NotFoundException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("game id " + gameId);
        if (!playerRepository.existsById(playerId)) throw new NotFoundException("player id " + playerId);

        Player player = findById(playerId);

        if (!Objects.equals(player.getGame().getId(), gameId))
            throw new BadRequestException("Game id does not match players params");

        return playerMapper.playerToPlayerDto(player);
    }
//
//    @Override
//    public Collection<Object> findAllByPlayerState(Long gameId, Long requestingPlayerId) throws BadRequestException, NotFoundException {
//        if (!gameRepository.existsById(gameId)) throw new NotFoundException("game id " + gameId);
//        if (!playerRepository.existsById(requestingPlayerId)) throw new NotFoundException("player id " + requestingPlayerId);
//
//        Player requestingPlayer = findById(requestingPlayerId);
//        if (requestingPlayer.getGame().getId() != gameId)
//            throw new BadRequestException("Game id does not match players params");
//
//        if (requestingPlayer.getState() == PlayerState.ADMINISTRATOR) {
//            return new ArrayList<>(playerMapper.playersToPlayerAdminDtos(findAll(gameId)));
//        }
//        return new ArrayList<>(playerMapper.playersToPlayerDtos(findAll(gameId)));
//    }

    @Override
    public Player addNewPlayer(Long gameId, HvZUserDTO hvZUserDTO) throws NotFoundException {
        PlayerDTO playerDTO = new PlayerDTO();
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("game id " + gameId);
        if (!userRepository.existsById(hvZUserDTO.getId())) throw new NotFoundException("HvZUser " + hvZUserDTO.getId());
        playerDTO.setGame(gameId);
        playerDTO.setUser(hvZUserDTO.getId());
        playerDTO.setHuman(true);
        String biteCode = generateBiteCode();

        while (true) {
            if (playerRepository.existsPlayerByBiteCodeAndGame_Id(biteCode, gameId))
                biteCode = generateBiteCode();
            else
                break;
        }

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
    public Player updatePlayer(Player player) throws NotFoundException, ForbiddenException {
        return playerRepository.save(player);
    }

    @Override
    public void deleteById(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    // TODO: This should be cascading
    @Override
    public void deletePlayerById(Long playerId, Long gameId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!playerRepository.existsById(playerId)) throw new NotFoundException("player id " + playerId);
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("game id " + gameId);
        playerRepository.deleteById(playerId);
    }

    @Override
    public Player findByUserId(Long gameId, String userId) {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("game id " + gameId);
        if (!userRepository.existsById(userId)) throw new NotFoundException("user id " + userId);
        return playerRepository.findByGameIdAndUserId(gameId, userId).get();
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }
}
