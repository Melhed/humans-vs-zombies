package com.example.backendhvz.services.game;

import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final ChatRepository chatRepository;
    private final KillRepository killRepository;
    private final MissionRepository missionRepository;
    private final SquadRepository squadRepository;
    private final SquadCheckInRepository squadCheckInRepository;
    private final SquadMemberRepository squadMemberRepository;

    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, ChatRepository chatRepository, KillRepository killRepository, MissionRepository missionRepository, SquadRepository squadRepository, SquadCheckInRepository squadCheckInRepository, SquadMemberRepository squadMemberRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.chatRepository = chatRepository;
        this.killRepository = killRepository;
        this.missionRepository = missionRepository;
        this.squadRepository = squadRepository;
        this.squadCheckInRepository = squadCheckInRepository;
        this.squadMemberRepository = squadMemberRepository;
    }

    @Override
    public Game findById(Long gameId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
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


//    @Override
//    public Game addGame(Game game, Long creatingPlayerId) {
//        Player creatingPlayer = playerRepository.findById(creatingPlayerId).get();
//        if(creatingPlayer.getState() != PlayerState.ADMINISTRATOR) return null;
//        return gameRepository.save(game);
//    }

    @Override
    public Game addNewGame(Game game) {
        game.setState(GameState.REGISTRATION);
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game) {
        if(!gameRepository.existsById(game.getId())) throw new NotFoundException("Game with ID " + game.getId() + " not found.");
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(Game game) {
        if(!gameRepository.existsById(game.getId())) throw new NotFoundException("Game with ID " + game.getId() + " not found.");
        return gameRepository.save(game);
    }

    @Override
    public void deleteById(Long gameId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        gameRepository.deleteById(gameId);
    }

    @Override
    @Transactional
    public void deleteGameById(Long gameId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        squadCheckInRepository.deleteAllByGameId(gameId);
        squadMemberRepository.deleteAllByGameId(gameId);
        squadRepository.deleteAllByGameId(gameId);
        killRepository.deleteAllByGameId(gameId);
        missionRepository.deleteAllByGameId(gameId);
        chatRepository.deleteAllByGameId(gameId);
        playerRepository.deleteAllByGameId(gameId);
        gameRepository.deleteById(gameId);
    }

    @Override
    public void delete(Game game) {
        if(!gameRepository.existsById(game.getId())) throw new NotFoundException("Game with ID " + game.getId() + " not found.");
        gameRepository.delete(game);
    }
}
