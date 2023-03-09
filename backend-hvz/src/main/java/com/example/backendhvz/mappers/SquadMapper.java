package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.SquadDTO;
import com.example.backendhvz.dtos.SquadDetailsDTO;
import com.example.backendhvz.dtos.SquadMemberDetailsDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.models.SquadMember;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.SquadMemberRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class SquadMapper {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SquadMemberMapper squadMemberMapper;

    @Autowired
    private SquadMemberRepository squadMemberRepository;

    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    public abstract Squad squadDtoToSquad(SquadDTO squadDto);

    @Mapping(target = "gameId", source = "game", qualifiedByName = "gameToGameId")
    public abstract SquadDTO squadToSquadDto(Squad squad);

    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    public abstract Collection<Squad> squadDtosToSquads(Collection<SquadDTO> squadDtos);

    @Mapping(target = "gameId", source = "game", qualifiedByName = "gameToGameId")
    public abstract Collection<SquadDTO> squadsToSquadDtos(Collection<Squad> squads);


    @Named("gameToGameId")
    public Long mapGameToGameId(Game game) {
        if(game == null) return null;
        return game.getId();
    }

    @Named("gameIdToGame")
    public Game mapGameIdToGame(Long gameId) {
        if(gameId == null) return null;
        return gameRepository.findById(gameId).get();
    }
}
