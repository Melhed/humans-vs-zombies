package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.CheckInDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.models.SquadCheckIn;
import com.example.backendhvz.models.SquadMember;
import com.example.backendhvz.repositories.SquadMemberRepository;
import com.example.backendhvz.services.game.GameService;
import com.example.backendhvz.services.squad.SquadService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class SquadCheckInMapper {
    @Autowired
    private GameService gameService;
    @Autowired
    private SquadService squadService;
    @Autowired
    private SquadMemberRepository squadMemberRepository;
    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "squadId", source = "squad.id")
    @Mapping(target = "squadMemberId", source = "squadMember.id")
    public abstract CheckInDTO checkInTocheckInDTO(SquadCheckIn checkIn);
    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "squadId", source = "squad.id")
    @Mapping(target = "squadMemberId", source = "squadMember.id")
    public abstract Collection<CheckInDTO> checkInsTocheckInDTOs(Collection<SquadCheckIn> checkIns);
    @Mapping(target = "squad", source = "squadId", qualifiedByName = "squadIdToSquad")
    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    @Mapping(target = "squadMember", source = "squadMemberId", qualifiedByName = "squadMemberIdToSquadMember")
    public abstract SquadCheckIn checkInDTOTocheckIn(CheckInDTO checkInDTO);
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
    @Named("squadMemberIdToSquadMember")
    SquadMember mapSquadMemberIdToSquadMember(Long squadMemberId) {
        if(squadMemberId == null) return null;
        return squadMemberRepository.findById(squadMemberId).get();
    }
}
