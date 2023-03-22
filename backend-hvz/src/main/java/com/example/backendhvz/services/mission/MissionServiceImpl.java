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
    public Mission updateMission(Mission mission, Long playerId) {
        if(!missionRepository.existsById(mission.getId())) throw new NotFoundException("Mission with ID " + mission.getId() + " not found.");
        if(!playerRepository.existsById(playerId)) throw new NotFoundException("Player with ID " + playerId + " not found.");

        Player player = playerRepository.findById(playerId).get();
        if (player.getState() != PlayerState.ADMINISTRATOR) throw new ForbiddenException("Only administrators can update missions.");

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
    public void deleteMissionById(Long missionId, Long playerId) {
        if(!missionRepository.existsById(missionId)) throw new NotFoundException("Mission with ID " + missionId + " not found.");
        if(!playerRepository.existsById(playerId)) throw new NotFoundException("Player with ID " + playerId + " not found.");

        Player player = playerRepository.findById(playerId).get();
        if(player.getState() != PlayerState.ADMINISTRATOR) throw new ForbiddenException("Only administrators can delete missions.");
        deleteById(missionId);
    }

    @Override
    public Collection<Mission> findMissionsByGameId(Long gameId, Long playerId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        if(!playerRepository.existsById(playerId)) throw new NotFoundException("Player with ID " + playerId + " not found.");

        Player player = playerRepository.findById(playerId).get();
        if(!gameId.equals(player.getGame().getId())) throw new BadRequestException("Player isn't registered to the game with the provided ID.");

        if (player.isHuman()) return missionRepository.findMissionsByGameIdAndHumanVisible(gameId).get();
        return missionRepository.findMissionsByGameIdAndZombieVisible(gameId).get();
    }

    @Override
    public Mission findMissionByIdAndGameId(Long gameId, Long missionId, Long playerId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        if(!missionRepository.existsById(missionId)) throw new NotFoundException("Mission with ID " + missionId + " not found.");
        if(!playerRepository.existsById(playerId)) throw new NotFoundException("Player with ID " + playerId + " not found.");

        Player player = playerRepository.findById(playerId).get();
        Mission mission = findById(missionId);
        if(!gameId.equals(player.getGame().getId())) throw new BadRequestException("Player isn't registered to the game with ID " + gameId + ".");
        if(!mission.getGame().getId().equals(gameId)) throw new BadRequestException("Mission isn't registered to the game with ID " + gameId + ".");

        if(player.isHuman() && mission.isHumanVisible()) return mission;
        if(!player.isHuman() && mission.isZombieVisible()) return mission;

        throw new BadRequestException("Player has to be the same faction as the mission to get access.");
    }
}
