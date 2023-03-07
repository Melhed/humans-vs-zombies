package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.game.GameService;
import com.example.backendhvz.services.player.PlayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class KillMapper {
    @Autowired
    protected GameService gameService;
    @Autowired
    protected PlayerService playerService;
    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "killer", source = "killer.id")
    @Mapping(target = "victim", source = "victim.id")
    public abstract KillDTO killToKillDto(Kill kill);
    @Mapping(target = "game", source = "game.id")
    @Mapping(target = "killer", source = "killer.id")
    @Mapping(target = "victim", source = "victim.id")
    public abstract Collection<KillDTO> killsToKillDtos(Collection<Kill> kills);
    @Mapping(target = "game", source = "game", qualifiedByName = "gameIdToGame")
    @Mapping(target = "killer", source = "killer", qualifiedByName = "playerIdToPlayer")
    @Mapping(target = "victim", source = "victim", qualifiedByName = "playerIdToPlayer")
    public abstract Kill killDtoToKill(KillDTO killDTO);

    @Named("gameIdToGame")
    Game mapGameIdToGame(Long gameId) {
        if (gameId == null) return null;
        return gameService.findById(gameId);
    }

    @Named("playerIdToPlayer")
    Player mapPlayerIdToPlayer(Long playerId) {
        if (playerId == null) return null;
        return playerService.findById(playerId);
    }
}
