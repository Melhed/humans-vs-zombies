package com.example.backendhvz.services.squad;

import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.models.SquadCheckIn;
import com.example.backendhvz.models.SquadMember;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface SquadService extends CRUDService<Squad, Long> {
    Collection<Squad> findSquadsByGameId(Long gameId);
    Squad findSquadByIdAndGameId(Long gameId, Long squadId);

    Squad addSquad(Long gameId, SquadPostDTO squadPostDTO);

    SquadMember joinSquad(Long gameId, Long squadId, Long playerId);

    SquadCheckIn addCheckIn(Long squadId, SquadCheckIn checkIn);
}
