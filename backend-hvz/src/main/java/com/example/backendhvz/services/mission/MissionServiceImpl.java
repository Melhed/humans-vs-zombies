package com.example.backendhvz.services.mission;

import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.models.Mission;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.MissionRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MissionServiceImpl implements MissionService{

    private final MissionRepository missionRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public MissionServiceImpl(MissionRepository missionRepository, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.missionRepository = missionRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Mission findById(Long missionId) {
        if(!missionRepository.existsById(missionId)) throw new NotFoundException("Mission with ID " + missionId + " not found.");
        return missionRepository.findById(missionId).get();
    }

    @Override
    public Collection<Mission> findAll() {
        return missionRepository.findAll();
    }

    @Override
    public Mission add(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public Mission addMission(Mission mission) {
        return add(mission);
    }

    @Override
    public Mission update(Mission mission) {
        if(mission == null) return null;
        return missionRepository.save(mission);
    }

    @Override
    public Mission updateMission(Mission mission) {
        if(!missionRepository.existsById(mission.getId())) throw new NotFoundException("Mission with ID " + mission.getId() + " not found.");
        return update(mission);
    }

    @Override
    public void deleteById(Long missionId) {
        if(!missionRepository.existsById(missionId)) throw new NotFoundException("Mission with ID " + missionId + " not found.");
        missionRepository.deleteById(missionId);
    }

    @Override
    public void delete(Mission mission) {
        missionRepository.delete(mission);
    }
    @Override
    public void deleteMissionById(Long missionId) {
        if(!missionRepository.existsById(missionId)) throw new NotFoundException("Mission with ID " + missionId + " not found.");
        deleteById(missionId);
    }

    @Override
    public Collection<Mission> findMissionsByGameId(Long gameId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        return missionRepository.findMissionsByGameId(gameId).get();
    }

    @Override
    public Mission findMissionByIdAndGameId(Long gameId, Long missionId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        if(!missionRepository.existsById(missionId)) throw new NotFoundException("Mission with ID " + missionId + " not found.");

        Mission mission = findById(missionId);
        if(!mission.getGame().getId().equals(gameId)) throw new BadRequestException("Mission isn't registered to the game with ID " + gameId + ".");

        return mission;
    }
}
