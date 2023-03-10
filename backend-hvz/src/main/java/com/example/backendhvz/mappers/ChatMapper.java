package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.ChatDTO;
import com.example.backendhvz.models.Chat;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.services.chat.ChatService;
import com.example.backendhvz.services.game.GameService;
import com.example.backendhvz.services.player.PlayerService;
import com.example.backendhvz.services.squad.SquadService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ChatMapper {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameService gameService;
    @Autowired
    private SquadService squadService;

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "squadId", source = "squad.id")
    public abstract ChatDTO chatToChatDto(Chat chat);
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "squadId", source = "squad.id")
    public abstract Collection<ChatDTO> chatsToChatDtos(Collection<Chat> chats);
    @Mapping(target = "player", source = "playerId", qualifiedByName = "playerIdToPlayer")
    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    @Mapping(target = "squad", source = "squadId", qualifiedByName = "squadIdToSquad")
    public abstract Chat chatDtoToChat(ChatDTO chatDTO);
    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    @Mapping(target = "player", source = "playerId", qualifiedByName = "playerIdToPlayer")
    @Mapping(target = "squad", source = "squadId", qualifiedByName = "squadIdToSquad")
    public abstract Collection<Chat> chatDtosToChats(Collection<ChatDTO> chatDTOS);

    @Named("playerIdToPlayer")
    Player mapPlayerIdToPlayer(Long playerId) {
        if (playerId == null) return null;
        return playerService.findById(playerId);
    }

    @Named("gameIdToGame")
    Game mapGameIdToGame(Long gameId) {
        if(gameId == null) return null;
        return gameService.findById(gameId);
    }
    @Named("squadIdToSquad")
    Squad mapSquadIdToSquad(Long squadId) {
        if(squadId == null) return null;
        return squadService.findById(squadId);
    }
}
