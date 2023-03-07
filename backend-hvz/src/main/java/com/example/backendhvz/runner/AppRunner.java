package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.GameController;
import com.example.backendhvz.controllers.MissionController;
import com.example.backendhvz.controllers.SquadController;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.mappers.GameMapper;
import com.example.backendhvz.mappers.MissionMapper;
import com.example.backendhvz.mappers.SquadMapper;

import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Mission;
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
    private final MissionController missionController;
    private final MissionMapper missionMapper;


    public AppRunner (SquadController squadController, SquadMapper squadMapper, GameController gameController, GameMapper gameMapper, MissionController missionController, MissionMapper missionMapper) {

        this.squadController = squadController;
        this.squadMapper = squadMapper;
        this.gameController = gameController;
        this.gameMapper = gameMapper;
        this.missionController = missionController;
        this.missionMapper = missionMapper;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Game game = new Game(1L, "sdklasdksa", GameState.COMPLETE, new Date(2022, 2, 2), new Date(2022, 2, 7), "", "", "", "");
        gameController.add(gameMapper.gameToGameDto(game));
        Mission mission = new Mission(1L, "kajaja", true, false, "ez", new Date(2022, 2, 2), new Date(2022, 2, 7), null);
        missionController.add(1L, missionMapper.missionToMissionDto(mission));
        System.out.println((missionController.findMissionsByGameId(1L)));
    }
}

