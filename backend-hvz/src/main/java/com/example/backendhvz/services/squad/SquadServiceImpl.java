package com.example.backendhvz.services.squad;

import com.example.backendhvz.dtos.SquadPostDTO;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.models.SquadMember;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.repositories.SquadMemberRepository;
import com.example.backendhvz.repositories.SquadRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SquadServiceImpl implements SquadService{
    private final SquadRepository squadRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SquadMemberRepository squadMemberRepository;

    public SquadServiceImpl(SquadRepository squadRepository, PlayerRepository playerRepository, GameRepository gameRepository, SquadMemberRepository squadMemberRepository) {
        this.squadRepository = squadRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.squadMemberRepository = squadMemberRepository;
    }

    @Override
    public Squad findById(Long squadId) {
        System.out.println("squadservice:" + squadId);
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
        System.out.println(squad);
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
        if(player == null) return null;
        Squad squad = new Squad(null, squadPostDTO.getSquadName(), player.isHuman(), game);
        SquadMember squadMember = new SquadMember(null, true, game, squad, player);
        squadMemberRepository.save(squadMember);
        return add(squad);
    }
}
