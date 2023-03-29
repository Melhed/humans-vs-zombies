package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.GameDTO;
import com.example.backendhvz.models.Game;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class GameMapper {
    public abstract GameDTO gameToGameDto(Game game);
    public abstract Collection<GameDTO> gamesToGameDtos(Collection<Game> games);
    public abstract Game gameDtoToGame(GameDTO gameDTO);
}
