package com.example.backendhvz.services.mission;

import com.example.backendhvz.models.Mission;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface MissionService extends CRUDService<Mission, Long> {

    Collection<Mission> findMissionsByGameId(Long gameId);
    Mission findMissionByIdAndGameId(Long gameId, Long missionId);

}
