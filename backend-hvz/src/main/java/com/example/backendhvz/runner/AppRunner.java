package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.GameController;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.mappers.GameMapper;
import com.example.backendhvz.models.Game;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class AppRunner implements ApplicationRunner {
    private final GameController gameController;
    private final GameMapper gameMapper;

    public AppRunner(GameController gameController, GameMapper gameMapper) {

        this.gameController = gameController;
        this.gameMapper = gameMapper;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        gameController.add(gameMapper.gameToGameDto(new Game(1L, "name", GameState.COMPLETE, Date.valueOf(LocalDate.now()), null, "", "", "", "")));
        System.out.println(gameController.findAll());
    }
}

