package com.example.backendhvz.services.player;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface PlayerService extends CRUDService<Player, Long> {
    Collection<Player> findAll(Long gameId);
    Player addNewPlayer(Long gameId, HvZUserDTO hvZUserDTO);
}
