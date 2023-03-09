package com.example.backendhvz.services.kill;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface KillService extends CRUDService<Kill, Long> {

    Kill updateKill(Long gameId, Long killId, Long updatingPlayerId, KillDTO killDTO);

    void deleteKillById(Long killId, Long deletingPlayerId);

    Collection<Kill> findAll(Long gameId);

    Kill addKill(Long gameId, KillPostDTO killPostDTO);
}
