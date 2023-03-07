package com.example.backendhvz.services.squad;

import com.example.backendhvz.models.Squad;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface SquadService extends CRUDService<Squad, Long> {
    Collection<Squad> findSquadsByGameId(Long gameId);
    Squad findSquadByIdAndGameId(Long gameId, Long squadId);
}
