package com.example.backendhvz.mappers;

import com.example.backendhvz.dtos.SquadMemberDTO;
import com.example.backendhvz.dtos.SquadMemberDetailsDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.models.SquadMember;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.repositories.SquadMemberRepository;
import com.example.backendhvz.repositories.SquadRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class SquadMemberMapper {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private SquadRepository squadRepository;

    @Autowired
    private SquadMemberRepository squadMemberRepository;

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "squadId", source = "squad.id")
    public abstract SquadMemberDTO squadMemberToSquadMemberDto(SquadMember squadMember);
    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "squadId", source = "squad.id")
    public abstract Collection<SquadMemberDTO> squadMembersToSquadMemberDtos(Collection<SquadMember> squadMember);
    @Mapping(target = "player", source = "playerId", qualifiedByName = "playerIdToPlayer")
    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    @Mapping(target = "squad", source = "squadId", qualifiedByName = "squadIdToSquad")
    public abstract SquadMember squadMemberDtoToSquad(SquadMemberDTO squadMemberDTO);
    @Mapping(target = "player", source = "playerId", qualifiedByName = "playerIdToPlayer")
    @Mapping(target = "game", source = "gameId", qualifiedByName = "gameIdToGame")
    @Mapping(target = "squad", source = "squadId", qualifiedByName = "squadIdToSquad")
    public abstract Collection<SquadMember> squadMemberDtosToSquads(Collection<SquadMemberDTO> squadMemberDTO);

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "playerIsHuman", source = "player.isHuman")
    @Mapping(target = "squadId", source = "squad.id")
    public abstract Collection<SquadMemberDetailsDTO> squadMembersToSquadMemberDetailsDtos(Collection<SquadMember> squadMembers);

    @Named("playerIdToPlayer")
    Player mapPlayerIdToPlayer(Long playerId) {
        if (playerId == null) return null;
        return playerRepository.findById(playerId).get();
    }

    @Named("gameIdToGame")
    Game mapGameIdToGame(Long gameId) {
        if(gameId == null) return null;
        return gameRepository.findById(gameId).get();
    }
    @Named("squadIdToSquad")
    Squad mapSquadIdToSquad(Long squadId) {
        if(squadId == null) return null;
        return squadRepository.findById(squadId).get();
    }
}
