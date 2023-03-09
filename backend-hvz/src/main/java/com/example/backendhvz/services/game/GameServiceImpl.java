package com.example.backendhvz.services.game;

import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Game findById(Long gameId) {
        return gameRepository.findById(gameId).get();
    }

    @Override
    public Collection<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game add(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game addGame(Game game, Long creatingPlayerId) {
        Player creatingPlayer = playerRepository.findById(creatingPlayerId).get();
        if(creatingPlayer.getState() != PlayerState.ADMINISTRATOR) return null;
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(Game game, Long updatingPlayerId) {
        Player updatingPlayer = playerRepository.findById(updatingPlayerId).get();
        if(updatingPlayer.getState() != PlayerState.ADMINISTRATOR) return null;
        return gameRepository.save(game);
    }

    @Override
    public void deleteById(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    @Override
    public void deleteGameById(Long gameId, Long deletingPlayerId) {
        Player deletingPlayer = playerRepository.findById(deletingPlayerId).get();
        if(deletingPlayer.getState() != PlayerState.ADMINISTRATOR) return;
        gameRepository.deleteById(gameId);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }
}
