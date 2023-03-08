package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.SquadDTO;
import com.example.backendhvz.dtos.SquadMemberDTO;
import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.mappers.SquadMapper;
import com.example.backendhvz.mappers.SquadMemberMapper;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.services.squad.SquadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/game/{gameId}/squad")
public class SquadController {
    private final SquadService squadService;
    private final SquadMapper squadMapper;
    private final SquadMemberMapper squadMemberMapper;

    public SquadController(SquadService squadService, SquadMapper squadMapper, SquadMemberMapper squadMemberMapper) {
        this.squadService = squadService;
        this.squadMapper = squadMapper;
        this.squadMemberMapper = squadMemberMapper;
    }

    @GetMapping("{squadId}") // GET /game/<game_id>/squad/<squad_id>
    public ResponseEntity<SquadDTO> findSquadByIdAndGameId(@PathVariable Long gameId, @PathVariable Long squadId) {
        return ResponseEntity.ok(squadMapper.squadToSquadDto(squadService.findSquadByIdAndGameId(gameId, squadId)));
    }

    @GetMapping // GET /game/<game_id>/squad
    public ResponseEntity<Collection<SquadDTO>> findAllSquadsByGameId(@PathVariable Long gameId) {
        return ResponseEntity.ok(squadMapper.squadsToSquadDtos(squadService.findSquadsByGameId(gameId)));
    }

    @PostMapping // POST /game/<game_id>/squad
    public ResponseEntity<SquadDTO> add(@PathVariable Long gameId, @RequestBody SquadPostDTO squadPostDTO) {
        if(squadPostDTO == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(squadMapper.squadToSquadDto(squadService.addSquad(gameId, squadPostDTO)));
    }

    @PostMapping("{squadId}/join")
    public ResponseEntity<SquadMemberDTO> join(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        return ResponseEntity.ok(squadMemberMapper.squadMemberToSquadMemberDto(squadService.joinSquad(gameId, squadId, playerId)));
    }

    @PutMapping("{squadId}") // PUT /game/<game_id>/squad/<squad_id>
    public ResponseEntity update(
            @PathVariable Long gameId,
            @PathVariable Long squadId,
            @RequestBody SquadDTO squadDTO
    ) {
        if(squadDTO.getGameId() != gameId || squadDTO.getId() != squadId) return ResponseEntity.badRequest().build();

        Squad squad = squadMapper.squadDtoToSquad(squadDTO);
        return ResponseEntity.ok(squadService.update(squad));
    }

    @DeleteMapping("{squadId}")
    public ResponseEntity delete(
            @PathVariable Long gameId,
            @PathVariable Long squadId,
            @RequestBody SquadDTO squadDTO
    ) {
        if(squadDTO == null
                ||gameId != squadDTO.getGameId()
                || squadId != squadDTO.getGameId())
            return ResponseEntity.badRequest().build();

        squadService.delete(squadMapper.squadDtoToSquad(squadDTO));
        return ResponseEntity.noContent().build();
    }
}
