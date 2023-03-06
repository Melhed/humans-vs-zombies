package com.example.backendhvz.services.game;

import com.example.backendhvz.models.Game;
import com.example.backendhvz.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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
    public Game update(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteById(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }
}
