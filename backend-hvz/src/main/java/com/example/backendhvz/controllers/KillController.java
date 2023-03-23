package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.services.kill.KillService;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.Collection;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping(path = "api/v1/game/{gameId}/kill")
public class KillController {
    private final KillService killService;
    private final KillMapper killMapper;
    private final RestResponseEntityExceptionHandler exceptionHandler;

    public KillController(KillService killService, KillMapper killMapper, RestResponseEntityExceptionHandler exceptionHandler) {
        this.killService = killService;
        this.killMapper = killMapper;
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping("{killId}")
    public ResponseEntity<Object> findById(@PathVariable Long gameId, @PathVariable Long killId) {
        try{
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(killId == null) throw new BadRequestException("Kill ID cannot be null.");

            Kill kill = killService.findById(killId);
            if(!kill.getGame().getId().equals(gameId)) throw new BadRequestException("The game ID provided isn't the one the kill is attributed to.");
            return ResponseEntity.ok(killMapper.killToKillDto(killService.findById(killId)));
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@PathVariable Long gameId) {
        try{
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");

            Collection<Kill> kills = killService.findAll(gameId);
            return ResponseEntity.ok(killMapper.killsToKillDtos(kills));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@PathVariable Long gameId, @RequestBody KillPostDTO killPostDTO) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(killPostDTO == null) throw new BadRequestException("Kill cannot be null.");

            Kill kill = killService.addKill(gameId, killPostDTO);
            URI location = URI.create("/" + kill.getId());
            return ResponseEntity.created(location).build();
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch(ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }

    @PutMapping("{killId}")
    public ResponseEntity update(@PathVariable Long gameId, @PathVariable Long killId, @RequestBody KillDTO killDTO) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(killId == null) throw new BadRequestException("Kill ID cannot be null.");
            if(killDTO == null) throw new BadRequestException("Kill cannot be null.");

            Kill kill = killService.updateKill(gameId, killId, killDTO);
            URI location = URI.create("/" + kill.getId());
            return ResponseEntity.created(location).build();
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch(ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }

    @DeleteMapping("{killId}")
    public ResponseEntity deleteKillById(@PathVariable Long gameId, @PathVariable Long killId) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(killId == null) throw new BadRequestException("Kill ID cannot be null.");

            killService.deleteKillById(gameId, killId);
            return ResponseEntity.noContent().build();
        } catch(NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch(BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch(ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable Long gameId, @RequestBody KillDTO killDTO) {
        try {
            if(gameId == null) throw new BadRequestException("Game ID cannot be null.");
            if(killDTO == null) throw new BadRequestException("Kill cannot be null.");
            if(!killDTO.getGame().equals(gameId)) throw new BadRequestException("Kill isn't attributed to the game with ID " + gameId + ".");

            killService.delete(killMapper.killDtoToKill(killDTO));
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }
}
