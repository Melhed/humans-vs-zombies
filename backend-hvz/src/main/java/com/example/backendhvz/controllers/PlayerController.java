package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.PlayerAdminDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.player.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("api/v1/game{gameId}/player")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }


    @GetMapping("{playerId}")
    public ResponseEntity<PlayerDTO> findById(@PathVariable Long gameId, @PathVariable Long playerId) {
        return ResponseEntity.ok(playerMapper.playerToPlayerDto(playerService.findById(playerId)));
    }

    @GetMapping
    public ResponseEntity<Collection<PlayerDTO>> findAll(@PathVariable Long gameId) {
        return ResponseEntity.ok(playerMapper.playersToPlayerDtos(playerService.findAll(gameId)));
    }

    @PostMapping
    public ResponseEntity<PlayerAdminDTO> add(@PathVariable Long gameId, @RequestBody PlayerAdminDTO playerDTO) {
        if (gameId != playerDTO.getGame()) return ResponseEntity.badRequest().build();
        Player player = playerMapper.playerAdminDtoToPlayer(playerDTO);
        URI location = URI.create("/" + player.getId());
        System.out.println(location.getPath());
        playerService.add(player);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{playerId}")
    public ResponseEntity update(@RequestBody PlayerAdminDTO playerDTO, @PathVariable Long gameId, @PathVariable Long playerId) {
        if (playerId != playerDTO.getId() || gameId != playerDTO.getGame()) return ResponseEntity.badRequest().build();
        Player player = playerMapper.playerAdminDtoToPlayer(playerDTO);
        return ResponseEntity.ok(playerService.update(player));
    }

    @DeleteMapping("{playerId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long playerId) {
        playerService.deleteById(playerId);
        return ResponseEntity.noContent().build();
    }
}
