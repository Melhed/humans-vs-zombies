package com.example.backendhvz.services.squad;

import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.models.*;
import com.example.backendhvz.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SquadServiceImpl implements SquadService{
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SquadMemberRepository squadMemberRepository;
    private final SquadCheckInRepository squadCheckInRepository;

    public SquadServiceImpl(SquadRepository squadRepository, PlayerRepository playerRepository, GameRepository gameRepository, SquadMemberRepository squadMemberRepository, SquadCheckInRepository squadCheckInRepository) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.squadMemberRepository = squadMemberRepository;
        this.squadCheckInRepository = squadCheckInRepository;
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
    public void delete(Squad squad) {
            if(squad == null) return;
            squadRepository.delete(squad);
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
    public Squad findSquadByIdAndGameId(Long gameId, Long squadId) {
        if(gameId == null || squadId == null) return null;
        return squadRepository.findSquadByIdAndGame_Id(gameId, squadId).get();
    }

    @Override
    public Squad addSquad(Long gameId, SquadPostDTO squadPostDTO) {
        Player player = playerRepository.findById(squadPostDTO.getPlayerId()).get();
        Game game = gameRepository.findById(gameId).get();
        Squad squad = new Squad(null, squadPostDTO.getSquadName(), player.isHuman(), game);
        SquadMember squadMember = new SquadMember(null, true, game, squad, player);
        squadMemberRepository.save(squadMember);
        return add(squad);
    }

    @Override
    public SquadMember joinSquad(Long gameId, Long squadId, Long playerId) {
        Game game = gameRepository.findById(gameId).get();
        Squad squad = findById(squadId);
        Player player = playerRepository.findById(playerId).get();
        if (squad.isHuman() != player.isHuman()) return null;
        SquadMember squadMember = new SquadMember(null, false, game, squad, player);
        return squadMemberRepository.save(squadMember);
    }

    @Override
    public SquadCheckIn addCheckIn(Long squadId, SquadCheckIn checkIn) {
        Squad squad = squadRepository.findById(squadId).get();
        if (squad.isHuman() != checkIn.getSquadMember().getPlayer().isHuman()) return null;
        return squadCheckInRepository.save(checkIn);
    }

    @Override
    public Collection<SquadCheckIn> getSquadCheckIns(Long squadId, Long playerId) {
        if(squadId == null) return null;
        Squad squad = findById(squadId);
        Player player = playerRepository.findById(playerId).get();
        if(squad == null || squad.isHuman() != player.isHuman()) return null;
        return squadCheckInRepository.findAllBySquad_Id(squadId).get();
    }
}
