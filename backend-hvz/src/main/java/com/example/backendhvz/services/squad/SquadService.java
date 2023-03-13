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

    void leaveSquad(Long gameId, Long playerId);

    SquadCheckIn addCheckIn(Long gameId, Long squadId, SquadCheckIn checkIn);
    Collection<SquadCheckIn> getSquadCheckIns(Long gameId, Long squadId, Long playerId);

    Squad updateSquad(Long gameId, Long squadId, Squad squad, Long playerId);

    void deleteSquadById(Long gameId, Long squadId, Long playerId);

    SquadDetailsDTO findDetailedSquad(Long gameId, Long squadId, Long playerId);
}
