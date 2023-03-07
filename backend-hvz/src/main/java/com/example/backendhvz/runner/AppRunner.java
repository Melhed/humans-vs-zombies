package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.GameController;
import com.example.backendhvz.controllers.PlayerController;
import com.example.backendhvz.dtos.GameDTO;
import com.example.backendhvz.dtos.PlayerAdminDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.GameMapper;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.HvZUser;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.UserRepository;
import com.example.backendhvz.services.player.PlayerService;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class AppRunner implements ApplicationRunner {
    private final GameController gameController;
    private final GameMapper gameMapper;
    private final PlayerController playerController;
    private final PlayerMapper playerMapper;
    private final PlayerService playerService;
    private final UserRepository userRepository;

    public AppRunner(GameController gameController, GameMapper gameMapper, PlayerController playerController, PlayerMapper playerMapper, PlayerService playerService, UserRepository userRepository) {
        this.gameController = gameController;
        this.gameMapper = gameMapper;
        this.playerController = playerController;
        this.playerMapper = playerMapper;
        this.playerService = playerService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Game game = new Game(1L, "GAME1", GameState.COMPLETE, new Date(1,1,1), new Date(2,2,2), "1", "2", "3", "4");
        GameDTO gameDTO = gameMapper.gameToGameDto(game);
        gameController.add(gameDTO);
        HvZUser user = new HvZUser(1L, "Billy", "Woods");
        userRepository.save(user);
        Player player = new Player(1L, PlayerState.ADMINISTRATOR, true, false, "HT8", user ,game);
        PlayerAdminDTO playerAdminDTO = playerMapper.playerToPlayerAdminDto(player);
        playerController.add(1L, playerAdminDTO);
        playerController.findAll(1L);
        playerController.findById(1L,1L);
        Player newPlayer = new Player(1L, PlayerState.NO_SQUAD, false, false, "HT8", user ,game);
        playerController.update(playerMapper.playerToPlayerAdminDto(newPlayer),1L,1L);
        playerController.deleteById(1L,1L);
    }
}

