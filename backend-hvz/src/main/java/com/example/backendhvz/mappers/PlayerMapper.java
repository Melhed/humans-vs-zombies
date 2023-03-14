package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.PlayerAdminDTO;
import com.example.backendhvz.dtos.PlayerDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.HvZUser;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.services.game.GameService;
import com.example.backendhvz.services.user.UserService;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class PlayerMapper {
    @Autowired
    private GameService gameService;
    @Autowired
    protected UserService userService;
    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "game", source = "game.id")
    public abstract PlayerDTO playerToPlayerDto(Player player);
    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "game", source = "game.id")
    public abstract Collection<PlayerDTO> playersToPlayerDtos(Collection<Player> player);
    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "game", source = "game.id")
    public abstract PlayerAdminDTO playerToPlayerAdminDto(Player player);
    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "game", source = "game.id")
    public abstract Collection<PlayerAdminDTO> playersToPlayerAdminDtos(Collection<Player> players);
    @Mapping(target = "user", source = "user", qualifiedByName = "userIdToUser")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameIdToGame")
    public abstract Player playerDtoToPlayer(PlayerDTO playerDTO);
    @Mapping(target = "user", source = "user", qualifiedByName = "userIdToUser")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameIdToGame")
    public abstract Collection<Player> playerDtosToPlayers(Collection<Player> players);
    @Mapping(target = "user", source = "user", qualifiedByName = "userIdToUser")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameIdToGame")
    public abstract Player playerAdminDtoToPlayer(PlayerAdminDTO playerAdminDTO);
    @Mapping(target = "user", source = "user", qualifiedByName = "userIdToUser")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameIdToGame")
    public abstract Collection<Player> playerAdminDtosToPlayers(Collection<PlayerAdminDTO> playerAdminDtos);

    @Named("gameIdToGame")
    Game mapGameIdToGame(Long gameId) {
        if (gameId == null) return null;
        return gameService.findById(gameId);
    }
    @Named("userIdToUser")
    HvZUser mapUserIdToUser(String userId) {
        if (userId == null) return null;
        return userService.findById(userId);
    }
}
