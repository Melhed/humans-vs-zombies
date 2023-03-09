package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.services.kill.KillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/game/{gameId}/kill")
public class KillController {
    private final KillService killService;
    private final KillMapper killMapper;

    public KillController(KillService killService, KillMapper killMapper) {
        this.killService = killService;
        this.killMapper = killMapper;
    }

    @GetMapping("{killId}")
    public ResponseEntity<KillDTO> findById(@PathVariable Long gameId, @PathVariable Long killId) {
        return ResponseEntity.ok(killMapper.killToKillDto(killService.findById(killId)));
    }

    @GetMapping
    public ResponseEntity<Collection<KillDTO>> findAll(@PathVariable Long gameId) {
        return ResponseEntity.ok(killMapper.killsToKillDtos(killService.findAll(gameId)));
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
