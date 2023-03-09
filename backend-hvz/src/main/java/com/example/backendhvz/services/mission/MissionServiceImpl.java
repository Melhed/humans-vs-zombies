package com.example.backendhvz.services.mission;

import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.models.Mission;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.MissionRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MissionServiceImpl implements MissionService{

    private final MissionRepository missionRepository;
    private final PlayerRepository playerRepository;

    public MissionServiceImpl(MissionRepository missionRepository, PlayerRepository playerRepository) {
        this.missionRepository = missionRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Mission findById(Long missionId) {
        if(missionId == null) return null;
        return missionRepository.findById(missionId).get();
    }

    @Override
    public Collection<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public Mission add(Mission mission) {
        if(mission == null) return null;
        return missionRepository.save(mission);
    }

    @Override
    public Mission addMission(Mission mission, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if (player == null || player.getState() != PlayerState.ADMINISTRATOR) return null;
        return add(mission);
    }

    @Override
    public Mission update(Mission mission) {
        if(mission == null) return null;
        return missionRepository.save(mission);
    }

    @Override
    public Mission updateMission(Mission mission, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if (player == null || player.getState() != PlayerState.ADMINISTRATOR) return null;
        return update(mission);
    }

    @Override
    public void deleteById(Long missionId) {
        if(missionId == null) return;
        missionRepository.deleteById(missionId);
    }

    @Override
    public void delete(Mission mission) {
        if(mission == null) return;
        missionRepository.delete(mission);
    }
    @Override
    public void deleteMissionById(Long missionId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if (player == null || player.getState() != PlayerState.ADMINISTRATOR) return;
        deleteById(missionId);
    }

    @Override
    public Collection<Mission> findMissionsByGameId(Long gameId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if(player == null) return null;
        if (player.isHuman()) return missionRepository.findMissionsByGameIdAndHumanVisible(gameId).get();
        return missionRepository.findMissionsByGameIdAndZombieVisible(gameId).get();
    }

    @Override
    public Mission findMissionByIdAndGameId(Long gameId, Long missionId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        Mission mission = findById(missionId);
        if(mission == null || player == null) return null;
        if(player.isHuman() && mission.isHumanVisible()) return mission;
        if(!player.isHuman() && mission.isZombieVisible()) return mission;
        return null;
    }
}
