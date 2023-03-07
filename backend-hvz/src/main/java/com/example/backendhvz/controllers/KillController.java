package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.services.kill.KillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/kill")
public class KillController {
    private final KillService killService;
    private final KillMapper killMapper;

    public KillController(KillService killService, KillMapper killMapper) {
        this.killService = killService;
        this.killMapper = killMapper;
    }

    @GetMapping("{killId}")
    public ResponseEntity<KillDTO> findById(@PathVariable Long killId) {
        return ResponseEntity.ok(killMapper.killToKillDto(killService.findById(killId)));
    }

    @GetMapping
    public ResponseEntity<Collection<KillDTO>> findAll() {
        return ResponseEntity.ok(killMapper.killsToKillDtos(killService.findAll()));
    }

    @PostMapping
    public ResponseEntity<KillDTO> add(@RequestBody KillDTO killDTO) {
        Kill kill = killMapper.killDtoToKill(killDTO);
        killService.add(kill);
        URI location = URI.create("/" + kill.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{killId}")
    public ResponseEntity update(@RequestBody KillDTO killDTO, @PathVariable Long killId) {
        if (killId != killDTO.getId()) return ResponseEntity.badRequest().build();
        Kill kill = killMapper.killDtoToKill(killDTO);
        return ResponseEntity.ok(killService.update(kill));
    }

    @DeleteMapping("{killId}")
    public ResponseEntity deleteById(@PathVariable Long killId) {
        killService.deleteById(killId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody KillDTO killDTO) {
        killService.delete(killMapper.killDtoToKill(killDTO));
        return ResponseEntity.noContent().build();
    }
}
