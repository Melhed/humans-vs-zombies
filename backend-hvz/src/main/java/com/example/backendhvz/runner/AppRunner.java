package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.GameController;
import com.example.backendhvz.controllers.SquadController;
import com.example.backendhvz.dtos.SquadDTO;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.mappers.GameMapper;
import com.example.backendhvz.mappers.SquadMapper;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Squad;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class AppRunner implements ApplicationRunner {
    private final SquadController squadController;
    private final SquadMapper squadMapper;
    private final GameController gameController;
    private final GameMapper gameMapper;


    public AppRunner (SquadController squadController, SquadMapper squadMapper, GameController gameController, GameMapper gameMapper) {

        this.squadController = squadController;
        this.squadMapper = squadMapper;
        this.gameController = gameController;
        this.gameMapper = gameMapper;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Game game = new Game(
                1L, "jakjsa", GameState.COMPLETE,
                new Date(2022, 1, 1),
                new Date(2022, 2, 1), "", "", "", "");
        gameController.add(gameMapper.gameToGameDto(game));
        Squad squad = new Squad(1L, "Squad", true, null);
        squadController.add(1L, squadMapper.squadToSquadDto(squad));


    }
}

