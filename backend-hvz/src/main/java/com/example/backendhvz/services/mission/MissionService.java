package com.example.backendhvz.services.mission;

import com.example.backendhvz.models.Mission;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface MissionService extends CRUDService<Mission, Long> {

    Collection<Mission> findMissionsByGameId(Long gameId, Long playerId);
    Mission findMissionByIdAndGameId(Long gameId, Long missionId, Long playerId);

    Mission addMission(Mission mission);

    Mission updateMission(Mission mission, Long playerId);

    void deleteMissionById(Long missionId, Long playerId);
}
