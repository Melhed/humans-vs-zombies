package com.example.backendhvz.services.kill;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.KillRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.repositories.SquadMemberRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;

@Service
public class KillServiceImpl implements KillService {

    private final KillRepository killRepository;
    private final KillMapper killMapper;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SquadMemberRepository squadMemberRepository;

    public KillServiceImpl(KillRepository killRepository, KillMapper killMapper, PlayerRepository playerRepository, GameRepository gameRepository, SquadMemberRepository squadMemberRepository) {
        this.killRepository = killRepository;
        this.killMapper = killMapper;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.squadMemberRepository = squadMemberRepository;
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
    public Kill addKill(Long gameId, KillPostDTO killPostDTO) {
        Game game = gameRepository.findById(gameId).get();
        Player killPoster = playerRepository.findById(killPostDTO.getKillPosterId()).get();
        // Hmm verkar inte funka?
        if(killPoster.getState() != PlayerState.ADMINISTRATOR
                || !playerRepository.existsPlayerByBiteCodeAndGame_Id(killPostDTO.getBiteCode(), gameId)
        ) return null;

        Player killer = playerRepository.findById(killPostDTO.getKillerId()).get();
        Player victim = playerRepository.findByBiteCode(killPostDTO.getBiteCode()).get();

        victim.setHuman(false);
        playerRepository.save(victim);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Kill kill = new Kill(null, timestamp, killPostDTO.getStory(), killPostDTO.getLat(), killPostDTO.getLng(), game, killer, victim);
        return add(kill);
    }

    @Override
    public Kill update(Kill kill) {
        return killRepository.save(kill);
    }

    @Override
    public Kill updateKill(Long gameId, Long killId, Long updatingPlayerId, KillDTO killDTO) {
        Kill kill = killMapper.killDtoToKill(killDTO);
        Player updatingPlayer = playerRepository.findById(updatingPlayerId).get();
        if(kill.getGame().getId() != gameId) return null;
        if(updatingPlayer.getId() != kill.getKiller().getId()
                && updatingPlayer.getState() != PlayerState.ADMINISTRATOR
        ) return null;

        return killRepository.save(kill);
    }

    @Override
    public void deleteById(Long killId) {
        killRepository.deleteById(killId);
    }

    @Override
    public void deleteKillById(Long killId, Long deletingPlayerId) {
        Player deletingPlayer = playerRepository.findById(deletingPlayerId).get();
        if(deletingPlayer.getState() != PlayerState.ADMINISTRATOR) return;
        killRepository.deleteById(killId);
    }

    @Override
    public void delete(Kill kill) {
        killRepository.delete(kill);
    }

    @Override
    public Collection<Kill> findAll(Long gameId) {
        return killRepository.findAllByGameId(gameId).get();
    }

}
