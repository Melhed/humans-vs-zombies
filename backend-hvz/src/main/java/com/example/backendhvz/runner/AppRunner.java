package com.example.backendhvz.runner;

import com.example.backendhvz.controllers.KillController;
import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.enums.GameState;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Kill;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class AppRunner implements ApplicationRunner {
    private final KillController killController;
    private final KillMapper killMapper;

    public AppRunner(KillController killController, KillMapper killMapper) {
        this.killController = killController;
        this.killMapper = killMapper;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        killController.add(killMapper.killToKillDto(new Kill(1L, new Date(2020, 02, 21), null, "2", "89", null, null, null)));
        killController.add(killMapper.killToKillDto(new Kill(2L, new Date(2020, 02, 21), null, "2", "89", null, null, null)));
        System.out.println(killController.findById(1L));
        Kill kill1 = new Kill(1L, new Date(2020, 02, 21), null, "200", "189", null, null, null);
        KillDTO killDTO = killMapper.killToKillDto(kill1);
        System.out.println(killController.update(killDTO, 1L));
    }
}

