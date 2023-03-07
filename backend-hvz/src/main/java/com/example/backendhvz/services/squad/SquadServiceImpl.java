package com.example.backendhvz.services.squad;

import com.example.backendhvz.models.Squad;
import com.example.backendhvz.repositories.SquadRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SquadServiceImpl implements SquadService{
    private final SquadRepository squadRepository;

    public SquadServiceImpl(SquadRepository squadRepository) {
        this.squadRepository = squadRepository;
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
}
