package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.*;
import com.example.backendhvz.mappers.ChatMapper;
import com.example.backendhvz.mappers.SquadCheckInMapper;
import com.example.backendhvz.mappers.SquadMapper;
import com.example.backendhvz.mappers.SquadMemberMapper;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.services.chat.ChatService;
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
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final PlayerRepository playerRepository;
    private final SquadCheckInMapper squadCheckInMapper;

    public SquadController(SquadService squadService, SquadMapper squadMapper, SquadMemberMapper squadMemberMapper, ChatService chatService, ChatMapper chatMapper, PlayerRepository playerRepository, SquadCheckInMapper squadCheckInMapper) {
        this.squadService = squadService;
        this.squadMapper = squadMapper;
        this.squadMemberMapper = squadMemberMapper;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.playerRepository = playerRepository;
        this.squadCheckInMapper = squadCheckInMapper;
    }
    // Only squad member
    @GetMapping("{squadId}") // GET /game/<game_id>/squad/<squad_id>
    public ResponseEntity<SquadDetailsDTO> findDetailedSquad(@PathVariable Long gameId, @PathVariable Long squadId, @PathVariable Long playerId) {
        return ResponseEntity.ok(squadService.findDetailedSquad(gameId, squadId, playerId));
    }
    // everyone?, unsure should we have a different DTO for this one
    @GetMapping // GET /game/<game_id>/squad
    public ResponseEntity<Collection<SquadDTO>> findAllSquadsByGameId(@PathVariable Long gameId) {
        return ResponseEntity.ok(squadMapper.squadsToSquadDtos(squadService.findSquadsByGameId(gameId)));
    }
    // Only squad member and admin
    @GetMapping("{squadId}/chat")
    public ResponseEntity<Collection<ChatDTO>> findAllSquadChats(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        return ResponseEntity.ok(chatMapper.chatsToChatDtos(chatService.findAllBySquadIdAndFaction(squadId, playerId)));
    }
    // Only squad members and admin
    @GetMapping("{squadId}/check-in")
    public ResponseEntity<Collection<CheckInDTO>> findAllSquadCheckIns(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        if(squadId == null || playerId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(squadCheckInMapper.checkInsTocheckInDTOs(squadService.getSquadCheckIns(squadId, playerId)));
    }
    // Only non squad member
    @PostMapping // POST /game/<game_id>/squad
    public ResponseEntity<SquadDTO> add(@PathVariable Long gameId, @RequestBody SquadPostDTO squadPostDTO) {
        if(squadPostDTO == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(squadMapper.squadToSquadDto(squadService.addSquad(gameId, squadPostDTO)));
    }
    // Only non squad member
    @PostMapping("{squadId}/join")
    public ResponseEntity<SquadMemberDTO> join(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        return ResponseEntity.ok(squadMemberMapper.squadMemberToSquadMemberDto(squadService.joinSquad(gameId, squadId, playerId)));
    }
    // Only squad members and admin
    @PostMapping("{squadId}/chat")
    public ResponseEntity<ChatDTO> addSquadChat(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody ChatDTO chatDTO) {
        if (chatDTO == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(chatMapper.chatToChatDto(chatService.addSquadChat(chatMapper.chatDtoToChat(chatDTO))));
    }
    // Only squad members and admin
    @PostMapping("{squadId}/check-in")
    public ResponseEntity<CheckInDTO> addSquadCheckIn(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody CheckInDTO checkInDTO) {
        return ResponseEntity.ok(squadCheckInMapper.checkInTocheckInDTO(squadService.addCheckIn(squadId, squadCheckInMapper.checkInDTOTocheckIn(checkInDTO))));
    }
    // Only admin
    @PutMapping("{squadId}") // PUT /game/<game_id>/squad/<squad_id>
    public ResponseEntity update(
            @PathVariable Long gameId,
            @PathVariable Long squadId,
            @RequestBody SquadDTO squadDTO,
            @RequestBody Long playerId
    ) {
        if(squadDTO.getGameId() != gameId || squadDTO.getId() != squadId) return ResponseEntity.badRequest().build();
        Squad squad = squadMapper.squadDtoToSquad(squadDTO);
        return ResponseEntity.ok(squadService.updateSquad(squad, playerId));
    }
    // Only admin
    @DeleteMapping("{squadId}")
    public ResponseEntity delete(
            @PathVariable Long gameId,
            @PathVariable Long squadId,
            @RequestBody SquadDTO squadDTO,
            @RequestBody Long playerId
    ) {
        if(squadDTO == null
                ||gameId != squadDTO.getGameId()
                || squadId != squadDTO.getGameId())
            return ResponseEntity.badRequest().build();

        squadService.deleteSquad(squadMapper.squadDtoToSquad(squadDTO), playerId);
        return ResponseEntity.noContent().build();
    }
}
