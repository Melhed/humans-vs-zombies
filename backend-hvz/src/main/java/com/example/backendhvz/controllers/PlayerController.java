package com.example.backendhvz.controllers;

import com.example.backendhvz.dtos.HvZUserDTO;
import com.example.backendhvz.dtos.PlayerAdminDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.PlayerMapper;
import com.example.backendhvz.models.HvZUser;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.player.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/game/{gameId}/player")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }


    @GetMapping("{playerId}")
    public ResponseEntity<Object> findById(@PathVariable Long gameId, @PathVariable Long playerId, @RequestBody Long requestingPlayerId) {
        return ResponseEntity.ok(playerService.findByIdAndPlayerState(gameId, playerId, requestingPlayerId));
    }

    @GetMapping
    public ResponseEntity<Collection<Object>> findAll(@PathVariable Long gameId, @RequestBody Long requestingPlayerId) {
        return ResponseEntity.ok(playerService.findAllByPlayerState(gameId, requestingPlayerId));
    }

    // TODO: Generate bite_code & merge the following two together
    @PostMapping
    public ResponseEntity<PlayerAdminDTO> add(@PathVariable Long gameId, @RequestBody PlayerAdminDTO playerDTO) {
        if (gameId != playerDTO.getGame()) return ResponseEntity.badRequest().build();
        Player player = playerMapper.playerAdminDtoToPlayer(playerDTO);
        URI location = URI.create("/" + player.getId());
        playerService.add(player);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("u")
    public ResponseEntity<PlayerDTO> addNewPlayer(@PathVariable Long gameId, @RequestBody HvZUserDTO hvZUserDTO) {
        playerService.addNewPlayer(gameId, hvZUserDTO);
        return null;
    }

    @PutMapping("{playerId}")
    public ResponseEntity update(@RequestBody PlayerAdminDTO playerDTO, @PathVariable Long gameId, @PathVariable Long playerId) {
        if (gameId != playerDTO.getGame()) return ResponseEntity.badRequest().build();
        Player player = playerMapper.playerAdminDtoToPlayer(playerDTO);
        return ResponseEntity.ok(playerService.update(player));
    }

    @DeleteMapping("{playerId}")
    public ResponseEntity deleteById(@PathVariable Long gameId, @PathVariable Long playerId) {
        playerService.deleteById(playerId);
        return ResponseEntity.noContent().build();
    }
}
