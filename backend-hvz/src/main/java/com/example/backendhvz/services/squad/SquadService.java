package com.example.backendhvz.services.squad;

import com.example.backendhvz.dtos.SquadDetailsDTO;
import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.models.SquadCheckIn;
import com.example.backendhvz.models.SquadMember;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface SquadService extends CRUDService<Squad, Long> {
    Collection<Squad> findSquadsByGameId(Long gameId);

    Squad addSquad(Long gameId, SquadPostDTO squadPostDTO);

    SquadMember joinSquad(Long gameId, Long squadId, Long playerId);

    SquadCheckIn addCheckIn(Long squadId, SquadCheckIn checkIn);
    Collection<SquadCheckIn> getSquadCheckIns(Long squadId, Long playerId);

    Squad updateSquad(Squad squad, Long playerId);

    void deleteSquad(Squad squad, Long playerId);

    SquadDetailsDTO findDetailedSquad(Long gameId, Long squadId, Long playerId);
}
