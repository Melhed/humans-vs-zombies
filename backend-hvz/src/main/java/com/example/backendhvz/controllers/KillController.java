package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.services.kill.KillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.Collection;

@RestController
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
    public ResponseEntity<KillDTO> findById(@PathVariable Long gameId, @PathVariable Long killId) {
        return ResponseEntity.ok(killMapper.killToKillDto(killService.findById(killId)));
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@PathVariable Long gameId) {
        try{
            Collection<Kill> kills = killService.findAll(gameId);
            return ResponseEntity.ok(killMapper.killsToKillDtos(kills));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e, null);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e, null);
        }
    }

    @PostMapping
    public ResponseEntity<KillDTO> add(@PathVariable Long gameId, @RequestBody KillPostDTO killPostDTO) {
        Kill kill = killService.addKill(gameId, killPostDTO);
        if (kill == null) return ResponseEntity.badRequest().build();
        URI location = URI.create("/" + kill.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{killId}")
    public ResponseEntity update(@PathVariable Long gameId, @PathVariable Long killId, @RequestBody Long updatingPlayerId, @RequestBody KillDTO killDTO) {
        return ResponseEntity.ok(killService.updateKill(gameId, killId, updatingPlayerId, killDTO));
    }

    @DeleteMapping("{killId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long killId) {
        killService.deleteById(killId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable Long gameId, @RequestBody KillDTO killDTO) {
        killService.delete(killMapper.killDtoToKill(killDTO));
        return ResponseEntity.noContent().build();
    }
}
