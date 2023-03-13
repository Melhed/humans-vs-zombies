package com.example.backendhvz.services.player;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface PlayerService extends CRUDService<Player, Long> {
    Collection<Player> findAll(Long gameId);

    Object findByIdAndPlayerState(Long gameId, Long playerId, Long requestingPlayerId);

    Collection<Object> findAllByPlayerState(Long gameId, Long playerId);

    Player addNewPlayer(Long gameId, HvZUserDTO hvZUserDTO);

    Player updatePlayer(Player player, Long updatingPlayerId);

    void deletePlayerById(Long playerId, Long deletingPlayerId, Long gameId);

}

