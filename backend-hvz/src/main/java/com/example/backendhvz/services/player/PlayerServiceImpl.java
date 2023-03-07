package com.example.backendhvz.services.player;

import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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
