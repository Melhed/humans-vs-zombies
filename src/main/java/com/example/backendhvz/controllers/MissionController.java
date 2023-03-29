package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.MissionDTO;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.MissionMapper;
import com.example.backendhvz.models.Mission;
import com.example.backendhvz.services.mission.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("api/v1/game/{gameId}/mission")
public class MissionController {
    private final MissionService missionService;
    private final MissionMapper missionMapper;
    private final RestResponseEntityExceptionHandler exceptionHandler;

    public MissionController(MissionService missionService, MissionMapper missionMapper, RestResponseEntityExceptionHandler exceptionHandler) {
        this.missionService = missionService;
        this.missionMapper = missionMapper;
        this.exceptionHandler = exceptionHandler;
    }
    // All but unregistered faction appropriate
    @GetMapping
    public ResponseEntity<Object> findMissionsByGameId(@PathVariable Long gameId) {
        try {
            if (gameId == null) throw new BadRequestException("Game ID cannot be null.");
            return ResponseEntity.ok(missionMapper.missionsToMissionDtos(missionService.findMissionsByGameId(gameId)));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    // All but unregistered faction appropriate
    @GetMapping("{missionId}")
    public ResponseEntity<Object> findMissionByIdAndGameId(@PathVariable Long gameId, @PathVariable Long missionId) {
        try {
            if (gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if (missionId == null) throw new BadRequestException("Mission ID cannot be null.");

            return ResponseEntity.ok(missionMapper.missionToMissionDto(missionService.findMissionByIdAndGameId(gameId, missionId)));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    // Admin only
    @PostMapping
    public ResponseEntity add(@PathVariable Long gameId, @RequestBody MissionDTO missionDTO) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(missionDTO == null) throw new BadRequestException("Mission cannot be null.");

            missionDTO.setGameId(gameId);
            Mission mission = missionMapper.missionDtoToMission(missionDTO);
            return ResponseEntity.ok(missionMapper.missionToMissionDto(missionService.add(mission)));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch(ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }
    // Admin only
    @PutMapping("{missionId}")
    public ResponseEntity update(@PathVariable Long gameId, @PathVariable Long missionId, @RequestBody MissionDTO missionDTO) {
        try {
            if (gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if (missionId == null) throw new BadRequestException("Mission ID cannot be null.");
            if (missionDTO == null) throw new BadRequestException("Mission cannot be null.");
            if (!gameId.equals(missionDTO.getGameId()))
                throw new BadRequestException("Mission's game ID doesn't match with the provided game ID.");

            missionService.updateMission(missionMapper.missionDtoToMission(missionDTO));
            URI location = URI.create("/" + missionDTO.getMissionID());
            return ResponseEntity.created(location).build();
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch(ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }
    // Admin only
    @DeleteMapping("{missionId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long missionId) {
        try {
            if (gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if (missionId == null) throw new BadRequestException("Mission ID cannot be null.");

            missionService.deleteMissionById(missionId);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }
}
