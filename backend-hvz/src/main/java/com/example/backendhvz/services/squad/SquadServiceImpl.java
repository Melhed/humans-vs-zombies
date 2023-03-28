package com.example.backendhvz.services.squad;

import com.example.backendhvz.dtos.SquadDetailsDTO;
import com.example.backendhvz.dtos.SquadMemberDetailsDTO;
import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.mappers.SquadMemberMapper;
import com.example.backendhvz.models.*;
import com.example.backendhvz.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SquadServiceImpl implements SquadService{
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SquadMemberRepository squadMemberRepository;
    private final SquadCheckInRepository squadCheckInRepository;
    private final ChatRepository chatRepository;
    private final SquadMemberMapper squadMemberMapper;

    public SquadServiceImpl(SquadRepository squadRepository, PlayerRepository playerRepository, GameRepository gameRepository, SquadMemberRepository squadMemberRepository, SquadCheckInRepository squadCheckInRepository, ChatRepository chatRepository, SquadMemberMapper squadMemberMapper) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.squadMemberRepository = squadMemberRepository;
        this.squadCheckInRepository = squadCheckInRepository;
        this.chatRepository = chatRepository;
        this.squadMemberMapper = squadMemberMapper;
    }

    @Override
    public Squad findById(Long squadId) {
        if(squadId == null) return null;
        return squadRepository.findById(squadId).get();
    }

    @Override
    public Collection<Squad> findAll() {
        return squadRepository.findAll();
    }

    @Override
    public Squad add(Squad squad) {
        if(squad == null) return null;
        return squadRepository.save(squad);
    }

    @Override
    public Squad update(Squad squad) {
        if(squad == null) return null;
        return squadRepository.save(squad);
    }

    @Override
    public Squad updateSquad(Long gameId, Long squadId, Squad squad) throws NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);

        if (!Objects.equals(squad.getId(), squadId)) throw new BadRequestException("input squad id and squad id in squadDTO does not match");
        return update(squad);
    }

    @Override
    public void delete(Squad squad) {
        if(squad == null) return;
        squadRepository.delete(squad);
    }

    @Override
    public void deleteSquadById(Long gameId, Long squadId) throws NotFoundException, ForbiddenException  {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);

        chatRepository.deleteAllBySquadId(squadId);
        squadCheckInRepository.deleteAllBySquadId(squadId);
        squadMemberRepository.deleteAllBySquadId(squadId);

        deleteById(squadId);
    }

    @Override
    public void deleteById(Long squadId) {
        if(squadId == null) return;
        squadRepository.deleteById(squadId);
    }

    @Override
    public Collection<Squad> findSquadsByGameId(Long gameId) throws NotFoundException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game Id " + gameId);
        return squadRepository.findSquadsByGame_Id(gameId).get();
    }

    @Override
    public SquadDetailsDTO findDetailedSquad(Long gameId, Long squadId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);
        Squad squad = findById(squadId);

        if (!Objects.equals(squad.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match squads params");

        Set<SquadMemberDetailsDTO> squadMembers = squadMemberMapper.squadMembersToSquadMemberDetailsDtos(squadMemberRepository.findAllBySquadId(squadId).get()).stream().collect(Collectors.toSet());
        return new SquadDetailsDTO(squadId, squad.getName(), squad.isHuman(), squadMembers);
    }

    @Override
    public Squad addSquad(Long gameId, SquadPostDTO squadPostDTO)  throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!playerRepository.existsById(squadPostDTO.getPlayerId())) throw new NotFoundException("player id " + squadPostDTO.getPlayerId());
        Player player = playerRepository.findById(squadPostDTO.getPlayerId()).get();
        Game game = gameRepository.findById(gameId).get();

        if (!Objects.equals(player.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match players params");
        if (player.getState() != PlayerState.NO_SQUAD) throw new ForbiddenException("To create a new squad player has to be squadless");

        player.setState(PlayerState.SQUAD_MEMBER);
        Squad squad = new Squad(null, squadPostDTO.getSquadName(), player.isHuman(), game);
        SquadMember squadMember = new SquadMember(null, true, game, squad, player);

        Squad newSquad = add(squad);
        playerRepository.save(player);
        squadMemberRepository.save(squadMember);
        return newSquad;
    }

    @Override
    public SquadMember joinSquad(Long gameId, Long squadId, Long playerId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("squad id " + squadId);
        if (!playerRepository.existsById(playerId)) throw new NotFoundException("Player id " + playerId);

        Game game = gameRepository.findById(gameId).get();
        Squad squad = findById(squadId);
        Player player = playerRepository.findById(playerId).get();

        if (!Objects.equals(player.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match players params");

        if (squad.isHuman() != player.isHuman()) throw new ForbiddenException("To get squad check ins player needs to be the same fraction as squad");
        if (player.getState() != PlayerState.NO_SQUAD) throw new ForbiddenException("To create a new squad player has to be squadless");

        player.setState(PlayerState.SQUAD_MEMBER);
        SquadMember squadMember = new SquadMember(null, false, game, squad, player);
        playerRepository.save(player);
        return squadMemberRepository.save(squadMember);
    }

    @Override
    public void leaveSquad(Long gameId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if (gameId != player.getGame().getId() || player.getState() != PlayerState.SQUAD_MEMBER) return;
        player.setState(PlayerState.NO_SQUAD);

        squadCheckInRepository.deleteAllBySquadMemberId(playerId);
        squadMemberRepository.deleteByPlayerId(playerId);
        playerRepository.save(player);
    }

    @Override
    public SquadCheckIn addCheckIn(Long gameId, Long squadId, SquadCheckIn checkIn) throws NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);
        if (!playerRepository.existsById(checkIn.getSquadMember().getPlayer().getId())) throw new NotFoundException("player id " + checkIn.getSquadMember().getPlayer().getId());

        Squad squad = squadRepository.findById(squadId).get();
        Player player = checkIn.getSquadMember().getPlayer();

        if (squad.isHuman() != player.isHuman())
            throw new ForbiddenException("To add squad check ins player needs to be the same fraction as squad");
        if (player.getState() == PlayerState.UNREGISTERED || player.getState() == PlayerState.NO_SQUAD)
            throw new ForbiddenException("To add squad chat player needs to be in a squad or admin");
        if (!squadMemberRepository.existsBySquadIdAndPlayerId(squadId, player.getId()))
            throw new ForbiddenException("player with player id " + player.getId() + " is not part of squad with squad id " + squadId);
        return squadCheckInRepository.save(checkIn);
    }

    @Override
    public Collection<SquadCheckIn> getSquadCheckIns(Long gameId, Long squadId) throws NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);

        Squad squad = findById(squadId);
        if (!Objects.equals(squad.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match squads params");

        return squadCheckInRepository.findAllBySquad_Id(squadId).get();
    }
}
