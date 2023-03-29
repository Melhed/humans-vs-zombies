package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.MissionDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Mission;
import com.example.backendhvz.repositories.GameRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class MissionMapper {
    @Autowired
    private GameRepository gameRepository;

    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    public abstract Mission missionDtoToMission(MissionDTO missionDTO);

    @Mapping(target = "gameId", source = "game", qualifiedByName = "gameToGameId")
    public abstract MissionDTO missionToMissionDto(Mission mission);

    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    public abstract Collection<Mission> missionDtosToMissions(Collection<MissionDTO> missionDTOs);

    @Mapping(target = "gameId", source = "game", qualifiedByName = "gameToGameId")
    public abstract Collection<MissionDTO> missionsToMissionDtos(Collection<Mission> missions);

    @Named("gameIdToGame")
    public Game gameIdToGame(Long gameId) {
        if(gameId == null) return null;
        return gameRepository.findById(gameId).get();
    }

    @Named("gameToGameId")
    public Long gameToGameId(Game game) {
        if(game == null) return null;
        return game.getId();
    }

}
