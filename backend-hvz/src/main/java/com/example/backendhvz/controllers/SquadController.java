package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.*;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.ChatMapper;
import com.example.backendhvz.mappers.SquadCheckInMapper;
import com.example.backendhvz.mappers.SquadMapper;
import com.example.backendhvz.mappers.SquadMemberMapper;
import com.example.backendhvz.models.Squad;
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
    private final SquadCheckInMapper squadCheckInMapper;
    private final RestResponseEntityExceptionHandler exceptionHandler;

    public SquadController(SquadService squadService, SquadMapper squadMapper, SquadMemberMapper squadMemberMapper, ChatService chatService, ChatMapper chatMapper, SquadCheckInMapper squadCheckInMapper, RestResponseEntityExceptionHandler exceptionHandler) {
        this.squadService = squadService;
        this.squadMapper = squadMapper;
        this.squadMemberMapper = squadMemberMapper;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.exceptionHandler = exceptionHandler;
        this.squadCheckInMapper = squadCheckInMapper;
    }

    @GetMapping("{squadId}") // GET /game/<game_id>/squad/<squad_id>
    public ResponseEntity<Object> findDetailedSquad(@PathVariable Long gameId, @PathVariable Long squadId, @PathVariable Long playerId) {
        try {
            if (gameId == null || playerId == null || squadId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(squadService.findDetailedSquad(gameId, squadId, playerId));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @GetMapping // GET /game/<game_id>/squad
    public ResponseEntity<Object> findAllSquadsByGameId(@PathVariable Long gameId) {
        try {
            if (gameId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(squadMapper.squadsToSquadDtos(squadService.findSquadsByGameId(gameId)));
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @GetMapping("{squadId}/chat")
    public ResponseEntity<Object> findAllSquadChats(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        try {
            if (gameId == null || squadId == null || playerId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(chatMapper.chatsToChatDtos(chatService.findAllBySquadIdAndFaction(squadId, playerId, gameId)));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @GetMapping("{squadId}/check-in")
    public ResponseEntity<Object> findAllSquadCheckIns(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        try {
            if (gameId == null || squadId == null || playerId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(squadCheckInMapper.checkInsTocheckInDTOs(squadService.getSquadCheckIns(gameId, squadId, playerId)));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @PostMapping // POST /game/<game_id>/squad
    public ResponseEntity<Object> add(@PathVariable Long gameId, @RequestBody SquadPostDTO squadPostDTO) {
        try {
            if (squadPostDTO == null || gameId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(squadMapper.squadToSquadDto(squadService.addSquad(gameId, squadPostDTO)));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @PostMapping("{squadId}/join")
    public ResponseEntity<Object> join(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        try {
            if (gameId == null || squadId == null || playerId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(squadMemberMapper.squadMemberToSquadMemberDto(squadService.joinSquad(gameId, squadId, playerId)));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @PostMapping("{squadId}/chat")
    public ResponseEntity<Object> addSquadChat(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody ChatDTO chatDTO) {
        try {
            if (gameId == null || squadId == null || chatDTO == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(chatMapper.chatToChatDto(chatService.addSquadChat(chatMapper.chatDtoToChat(chatDTO), gameId, squadId)));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @PostMapping("{squadId}/check-in")
    public ResponseEntity<Object> addSquadCheckIn(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody CheckInDTO checkInDTO) {
        try {
            if (gameId == null || squadId == null || checkInDTO == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(squadCheckInMapper.checkInTocheckInDTO(squadService.addCheckIn(gameId, squadId, squadCheckInMapper.checkInDTOTocheckIn(checkInDTO))));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @PutMapping("{squadId}") // PUT /game/<game_id>/squad/<squad_id>
    public ResponseEntity<Object> update(
            @PathVariable Long gameId,
            @PathVariable Long squadId,
            @RequestBody SquadDTO squadDTO,
            @RequestBody Long playerId
    ) {
        try {
            if (gameId == null || squadId == null || squadDTO == null || playerId == null) throw new BadRequestException("Invalid input");
            Squad squad = squadMapper.squadDtoToSquad(squadDTO);
            return ResponseEntity.ok(squadService.updateSquad(gameId, squadId, squad, playerId));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }

    @DeleteMapping("{squadId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long squadId, @RequestBody Long playerId) {
        try {
            if (gameId == null || squadId == null || playerId == null) throw new BadRequestException("Invalid input");
            squadService.deleteSquadById(gameId, squadId, playerId);
            return ResponseEntity.noContent().build();
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        }
    }
}
