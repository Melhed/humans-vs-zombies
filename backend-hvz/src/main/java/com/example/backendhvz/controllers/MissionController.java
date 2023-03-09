package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.MissionDTO;
import com.example.backendhvz.mappers.MissionMapper;
import com.example.backendhvz.models.Mission;
import com.example.backendhvz.services.mission.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/game/{gameId}/mission")
public class MissionController {
    private final MissionService missionService;
    private final MissionMapper missionMapper;

    public MissionController(MissionService missionService, MissionMapper missionMapper) {
        this.missionService = missionService;
        this.missionMapper = missionMapper;
    }
    // All but unregistered faction appropriate
    @GetMapping
    public ResponseEntity<Collection<MissionDTO>> findMissionsByGameId(@PathVariable Long gameId, @RequestBody Long playerId) {
        if(gameId == null || playerId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(missionMapper.missionsToMissionDtos(missionService.findMissionsByGameId(gameId, playerId)));
    }
    // All but unregistered faction appropriate
    @GetMapping("{missionId}")
    public ResponseEntity<MissionDTO> findMissionByIdAndGameId(@PathVariable Long gameId, @PathVariable Long missionId, @RequestBody Long playerId) {
        if(gameId == null || missionId == null || playerId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(missionMapper.missionToMissionDto(missionService.findMissionByIdAndGameId(gameId, missionId, playerId)));
    }
    // Admin only
    @PostMapping
    public ResponseEntity add(@PathVariable Long gameId, @RequestBody MissionDTO missionDTO, @RequestBody Long playerId) {
        if(gameId == null || playerId == null) return ResponseEntity.badRequest().build();
        missionDTO.setGameId(gameId);
        Mission mission = missionMapper.missionDtoToMission(missionDTO);
        return ResponseEntity.ok(missionMapper.missionToMissionDto(missionService.addMission(mission, playerId)));
    }
    // Admin only
    @PutMapping("{missionId}")
    public ResponseEntity update(@PathVariable Long gameId, @PathVariable Long missionId, @RequestBody MissionDTO missionDTO, @RequestBody Long playerId) {
        if(gameId == null
                || missionId == null
                || missionId != missionDTO.getId()
                || gameId != missionDTO.getGameId()
        ) return ResponseEntity.badRequest().build();

        missionService.updateMission(missionMapper.missionDtoToMission(missionDTO), playerId);
        URI location = URI.create("/" + missionDTO.getId());
        return ResponseEntity.created(location).build();
    }
    // Admin only
    @DeleteMapping("{missionId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long missionId, @RequestBody Long playerId) {
        if(gameId == null || missionId == null) return ResponseEntity.badRequest().build();
        missionService.deleteMissionById(missionId, playerId);
        return ResponseEntity.noContent().build();
    }
}
