package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.dtos.PlayerAdminDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.exceptions.RestResponseEntityExceptionHandler;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.player.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("api/v1/game/{gameId}/player")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final RestResponseEntityExceptionHandler exceptionHandler;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper, RestResponseEntityExceptionHandler exceptionHandler) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.exceptionHandler = exceptionHandler;
    }


//    @GetMapping("{playerId}/squad")
//    public ResponseEntity<Object> findPlayerSquadsById(@PathVariable Long gameId, @PathVariable Long playerId) {
//
//    }

    @GetMapping("{playerId}")
    public ResponseEntity<Object> findById(@PathVariable Long gameId, @PathVariable Long playerId) {
        try {
            if (gameId == null || playerId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(playerService.findByIdAndPlayerState(gameId, playerId));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@PathVariable Long gameId) {
        try {
            return ResponseEntity.ok(playerService.findAll(gameId));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> findByUserId(@PathVariable Long gameId, @PathVariable String userId) {
        try {
            if (gameId == null || userId == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(playerService.findByUserId(gameId, userId));
        } catch (BadRequestException e) {
            return exceptionHandler.handleBadRequest(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@PathVariable Long gameId, @RequestBody PlayerAdminDTO playerDTO) {
        if (gameId == null || playerDTO == null)
            return exceptionHandler.handleBadRequest(new BadRequestException("Invalid input"));
        if (!Objects.equals(gameId, playerDTO.getGame()))
            return exceptionHandler.handleBadRequest(new BadRequestException("Game id does not match players params"));
        Player player = playerMapper.playerAdminDtoToPlayer(playerDTO);
        return ResponseEntity.ok(playerMapper.playerToPlayerDto(playerService.add(player)));
    }

    @PostMapping("u")
    public ResponseEntity<Object> addNewPlayer(@PathVariable Long gameId, @RequestBody HvZUserDTO hvZUserDTO) {
        try {
            if (gameId == null || hvZUserDTO == null) throw new BadRequestException("Invalid input");
            return ResponseEntity.ok(playerMapper.playerToPlayerDto(playerService.addNewPlayer(gameId, hvZUserDTO)));
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @PutMapping("{playerId}")
    public ResponseEntity update(@RequestBody PlayerAdminDTO playerDTO, @PathVariable Long gameId, @PathVariable Long playerId) {
        try {
            if (gameId == null || playerDTO == null || playerId == null) throw new BadRequestException("Invalid input");
            if (!Objects.equals(gameId, playerDTO.getGame()))
                throw new BadRequestException("Game id does not match players params");
            Player player = playerMapper.playerAdminDtoToPlayer(playerDTO);
            return ResponseEntity.ok(playerService.updatePlayer(player));
        } catch (ForbiddenException e) {
            return exceptionHandler.handleForbidden(e);
        } catch (NotFoundException e) {
            return exceptionHandler.handleNotFound(e);
        }
    }

    @DeleteMapping("{playerId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long playerId) {
        try {
            if (gameId == null || playerId == null) throw new BadRequestException("Invalid input");
            playerService.deletePlayerById(playerId, gameId);
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
