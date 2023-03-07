package com.example.backendhvz.services.mission;

import com.example.backendhvz.models.Mission;
import com.example.backendhvz.repositories.MissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MissionServiceImpl implements MissionService{

    private final MissionRepository missionRepository;

    public MissionServiceImpl(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
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
    public Mission update(Mission mission) {
        if(mission == null) return null;
        return missionRepository.save(mission);
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
    public Collection<Mission> findMissionsByGameId(Long gameId) {
        if(gameId == null) return null;
        return missionRepository.findMissionsByGame_Id(gameId).get();
    }

    @Override
    public Mission findMissionByIdAndGameId(Long gameId, Long missionId) {
        if(gameId == null || missionId == null) return null;
        return missionRepository.findMissionByIdAndGame_Id(gameId, missionId).get();
    }
}
