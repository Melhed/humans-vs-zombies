package com.example.backendhvz.services.squad;

import com.example.backendhvz.dtos.SquadDetailsDTO;
import com.example.backendhvz.dtos.SquadMemberDTO;
import com.example.backendhvz.dtos.SquadMemberDetailsDTO;
import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.SquadMemberMapper;
import com.example.backendhvz.models.*;
import com.example.backendhvz.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class SquadServiceImpl implements SquadService{
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SquadMemberRepository squadMemberRepository;
    private final SquadCheckInRepository squadCheckInRepository;
    private final SquadMemberMapper squadMemberMapper;

    public SquadServiceImpl(SquadRepository squadRepository, PlayerRepository playerRepository, GameRepository gameRepository, SquadMemberRepository squadMemberRepository, SquadCheckInRepository squadCheckInRepository, SquadMemberMapper squadMemberMapper) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.squadMemberRepository = squadMemberRepository;
        this.squadCheckInRepository = squadCheckInRepository;
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
    public Squad updateSquad(Squad squad, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if (player == null || player.getState() != PlayerState.ADMINISTRATOR) return null;
        return update(squad);
    }

    @Override
    public void delete(Squad squad) {
        if(squad == null) return;
        squadRepository.delete(squad);
    }

    @Override
    public void deleteSquad(Squad squad, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if (player == null || player.getState() != PlayerState.ADMINISTRATOR) return;
        delete(squad);
    }

    @Override
    public void deleteById(Long squadId) {
        if(squadId == null) return;
        squadRepository.deleteById(squadId);
    }

    @Override
    public Collection<Squad> findSquadsByGameId(Long gameId) {
        if(gameId == null) return null;
        return squadRepository.findSquadsByGame_Id(gameId).get();
    }

    @Override
    public SquadDetailsDTO findDetailedSquad(Long gameId, Long squadId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        Squad squad = findById(squadId);
        if (gameId == null || squad == null || player == null || player.getState() != PlayerState.SQUAD_MEMBER) return null;
        Collection<SquadMemberDetailsDTO> squadMembers = squadMemberMapper.squadMembersToSquadMemberDetailsDtos(squadMemberRepository.findAllBySquadId(squadId).get());
        return new SquadDetailsDTO(squadId, squad.getName(), squad.isHuman(), (Set<SquadMemberDetailsDTO>) squadMembers);
    }

    @Override
    public Squad addSquad(Long gameId, SquadPostDTO squadPostDTO) {
        Player player = playerRepository.findById(squadPostDTO.getPlayerId()).get();
        Game game = gameRepository.findById(gameId).get();
        if (player == null || game == null || player.getState() != PlayerState.NO_SQUAD) return null;

        player.setState(PlayerState.SQUAD_MEMBER);
        Squad squad = new Squad(null, squadPostDTO.getSquadName(), player.isHuman(), game);
        SquadMember squadMember = new SquadMember(null, true, game, squad, player);

        playerRepository.save(player);
        squadMemberRepository.save(squadMember);
        return add(squad);
    }

    @Override
    public SquadMember joinSquad(Long gameId, Long squadId, Long playerId) {
        Game game = gameRepository.findById(gameId).get();
        Squad squad = findById(squadId);
        Player player = playerRepository.findById(playerId).get();
        if (game == null || squad == null || squad.isHuman() != player.isHuman() || player.getState() != PlayerState.NO_SQUAD)
            return null;
        player.setState(PlayerState.SQUAD_MEMBER);
        SquadMember squadMember = new SquadMember(null, false, game, squad, player);
        playerRepository.save(player);
        return squadMemberRepository.save(squadMember);
    }

    @Override
    public void leaveSquad(Long gameId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        if(gameId != player.getGame().getId() || player.getState() != PlayerState.SQUAD_MEMBER) return;
        player.setState(PlayerState.NO_SQUAD);
        squadMemberRepository.deleteByPlayerId(playerId);
        playerRepository.save(player);
    }

    @Override
    public SquadCheckIn addCheckIn(Long squadId, SquadCheckIn checkIn) {
        Squad squad = squadRepository.findById(squadId).get();
        if (squad == null) return null;
        Player player = checkIn.getSquadMember().getPlayer();
        if (player == null || squad.isHuman() != player.isHuman() || player.getState() == PlayerState.NO_SQUAD || player.getState() == PlayerState.UNREGISTERED) return null;
        return squadCheckInRepository.save(checkIn);
    }

    @Override
    public Collection<SquadCheckIn> getSquadCheckIns(Long squadId, Long playerId) {
        Squad squad = findById(squadId);
        Player player = playerRepository.findById(playerId).get();
        if (    squad == null ||
                player == null ||
                squad.isHuman() != player.isHuman() ||
                player.getState() == PlayerState.UNREGISTERED ||
                player.getState() == PlayerState.NO_SQUAD)
            return null;
        return squadCheckInRepository.findAllBySquad_Id(squadId).get();
    }
}
