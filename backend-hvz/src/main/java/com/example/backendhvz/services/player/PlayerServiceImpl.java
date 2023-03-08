package com.example.backendhvz.services.player;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;

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
    public Player addNewPlayer(Long gameId, HvZUserDTO hvZUserDTO) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setGame(gameId);
        playerDTO.setUser(hvZUserDTO.getId());
        playerDTO.isHuman();
        String biteCode = generateBiteCode();
        while (playerRepository.existsPlayerByBiteCodeAndGame_Id(biteCode, gameId).get())
            biteCode = generateBiteCode();
        playerDTO.setBiteCode(biteCode);
        playerDTO.setState(PlayerState.UNREGISTERED);
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
    public void deleteById(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    @Override
    public void delete(Player player) {
        playerRepository.delete(player);
    }
}
