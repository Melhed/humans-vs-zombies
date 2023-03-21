package com.example.backendhvz.services.kill;

import com.example.backendhvz.dtos.KillDTO;
import com.example.backendhvz.dtos.KillPostDTO;
import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.mappers.KillMapper;
import com.example.backendhvz.models.Game;
import com.example.backendhvz.models.Kill;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.repositories.GameRepository;
import com.example.backendhvz.repositories.KillRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Service
public class KillServiceImpl implements KillService {

    private final KillRepository killRepository;
    private final KillMapper killMapper;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public KillServiceImpl(KillRepository killRepository, KillMapper killMapper, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.killRepository = killRepository;
        this.killMapper = killMapper;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Kill findById(Long killId) {
        if(!killRepository.existsById(killId)) throw new NotFoundException("Kill with id " + killId + " doesn't exist.");
        return killRepository.findById(killId).get();
    }

    @Override
    public Collection<Kill> findAll() {
        Collection<Kill> kills = killRepository.findAll();
        return kills;
    }

    @Override
    public Kill add(Kill kill) {
        return killRepository.save(kill);
    }

    @Override
    public Kill addKill(Long gameId, KillPostDTO killPostDTO) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        if(!playerRepository.existsById(killPostDTO.getKillPosterId())) throw new NotFoundException("Kill poster with ID " + killPostDTO.getKillPosterId() + " not found.");
        if(!playerRepository.existsById(killPostDTO.getKillerId())) throw new NotFoundException("Killer with ID " + killPostDTO.getKillerId() + " not found.");
        if(!playerRepository.existsPlayerByBiteCodeAndGame_Id(killPostDTO.getBiteCode(), gameId)) throw new NotFoundException("Player with bite code " + killPostDTO.getBiteCode() + " not found.");

//        Player killPoster = playerRepository.findById(killPostDTO.getKillPosterId()).get();
//        if(killPoster.getState() != PlayerState.ADMINISTRATOR) throw new ForbiddenException("You need to be an " + PlayerState.ADMINISTRATOR + " to post a new kill!");

        Player killer = playerRepository.findById(killPostDTO.getKillerId()).get();
        if(!killer.getGame().getId().equals(gameId)) throw new BadRequestException("Killer is not in the game with the provided ID.");
        if(killer.isHuman()) throw new BadRequestException("Killer cannot be human.");

        Player victim = playerRepository.findByBiteCode(killPostDTO.getBiteCode()).get();
        if(!victim.getGame().getId().equals(gameId)) throw new BadRequestException("Victim is not in the game with the provided ID.");
        if(!victim.isHuman()) throw new BadRequestException("Victim is already a zombie.");

        Game game = gameRepository.findById(gameId).get();
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
    public Kill updateKill(Long gameId, Long killId, KillDTO killDTO) {
        if(killDTO.getId() != killId) throw new BadRequestException("ID of kill object and kill you're trying to update is not the same.");
        if(!killDTO.getGame().equals(gameId)) throw new BadRequestException("Kill isn't attributed to the provided game with ID " + killDTO.getGame() + ".");
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        if(!killRepository.existsById(killId)) throw new NotFoundException("Kill with ID " + killId + " not found.");

        Kill kill = killMapper.killDtoToKill(killDTO);
        return killRepository.save(kill);
    }

    @Override
    public void deleteById(Long killId) {
        if(!killRepository.existsById(killId)) throw new NotFoundException("Kill with ID " + killId + " not found.");
        killRepository.deleteById(killId);
    }

    @Override
    public void deleteKillById(Long gameId, Long killId) {
        if(!killRepository.existsById(killId)) throw new NotFoundException("Kill with ID " + killId + " not found.");

        Kill kill = killRepository.findById(killId).get();
        if(!gameId.equals(kill.getGame().getId())) throw new BadRequestException("Kill isn't attributed to the game with ID " + gameId + ".");

        killRepository.deleteById(killId);
    }

    @Override
    public void delete(Kill kill) {
        killRepository.delete(kill);
    }

    @Override
    public Collection<Kill> findAll(Long gameId) {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("No game found with ID " + gameId + ".");
        Collection<Kill> kills = killRepository.findAllByGameId(gameId).get();
        return kills;
    }

}
