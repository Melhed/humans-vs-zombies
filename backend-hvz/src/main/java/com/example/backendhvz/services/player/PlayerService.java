package com.example.backendhvz.services.player;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface PlayerService extends CRUDService<Player, Long> {
    Collection<Object> findAll(Long gameId);

    Object findByIdAndPlayerState(Long gameId, Long playerId);

    Player addNewPlayer(Long gameId, HvZUserDTO hvZUserDTO);

    Player updatePlayer(Player player);

    void deletePlayerById(Long playerId, Long gameId);

    Player findByUserId(Long gameId, String userId);
}

