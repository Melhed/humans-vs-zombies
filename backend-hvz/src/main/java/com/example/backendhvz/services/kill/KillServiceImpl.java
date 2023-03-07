package com.example.backendhvz.services.kill;

import com.example.backendhvz.models.Kill;
import com.example.backendhvz.repositories.KillRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class KillServiceImpl implements KillService {

    private final KillRepository killRepository;

    public KillServiceImpl(KillRepository killRepository) {
        this.killRepository = killRepository;
    }

    @Override
    public Kill findById(Long killId) {
        return killRepository.findById(killId).get();
    }

    @Override
    public Collection<Kill> findAll() {
        return killRepository.findAll();
    }

    @Override
    public Kill add(Kill kill) {
        return killRepository.save(kill);
    }

    @Override
    public Kill update(Kill kill) {
        return killRepository.save(kill);
    }

    @Override
    public void deleteById(Long killId) {
        killRepository.deleteById(killId);
    }

    @Override
    public void delete(Kill kill) {
        killRepository.delete(kill);
    }
}
