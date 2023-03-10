package com.example.backendhvz.services.chat;

import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.models.Chat;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {

    private  final ChatRepository chatRepository;
    private final PlayerRepository playerRepository;
    private final SquadRepository squadRepository;
    private final GameRepository gameRepository;
    private final SquadMemberRepository squadMemberRepository;

    public ChatServiceImpl(ChatRepository chatRepository, PlayerRepository playerRepository, SquadRepository squadRepository, GameRepository gameRepository, SquadMemberRepository squadMemberRepository) {
        this.chatRepository = chatRepository;
        this.playerRepository = playerRepository;
        this.squadRepository = squadRepository;
        this.gameRepository = gameRepository;
        this.squadMemberRepository = squadMemberRepository;
    }

    @Override
    public Chat findById(Long chatId) {
        return chatRepository.findById(chatId).get();
    }

    @Override
    public Chat findChatByIdAndGameId(Long gameId, Long chatId) {
        return chatRepository.findChatByIdAndGameId(gameId, chatId).get();
    }

    @Override
    public Collection<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public Collection<Chat> findAllNonSquad() {
        return chatRepository.findAllBySquad(null).get();
    }
    @Override
    public Collection<Chat> findAllByGameId(Long gameId, boolean playerIsHuman) {
        if (playerIsHuman) return chatRepository.findAllByGameIdAndHumanGlobal(gameId).get();
        return chatRepository.findAllByGameIdAndZombieGlobal(gameId).get();
    }

    @Override
    public Collection<Chat> findAllBySquadIdAndFaction(Long squadId, Long playerId, Long gameId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);
        if (!playerRepository.existsById(playerId)) throw new NotFoundException("Player id " + playerId);
        Player player = playerRepository.findById(playerId).get();
        Squad squad = squadRepository.findById(squadId).get();

        if (!Objects.equals(player.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match players params");
        if (!Objects.equals(squad.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match squads params");
        if (!squadMemberRepository.existsBySquad_IdAndPlayer_Id(squadId, playerId))
            throw new ForbiddenException("player with player id " + playerId + " is not part of squad with squad id " + squadId);
        if (player.isHuman() != squad.isHuman())
            throw new ForbiddenException("To get squad chat player needs to be the same fraction as squad");
        if (player.getState() == PlayerState.NO_SQUAD || player.getState() == PlayerState.UNREGISTERED)
            throw new ForbiddenException("To get squad chat player needs to be in a squad or admin");
        if (squad.isHuman())
            return chatRepository.findAllBySquad_IdAndHumanGlobal(squadId).get();
        return chatRepository.findAllBySquad_IdAndZombieGlobal(squadId).get();
    }
    @Override
    public Chat add(Chat chat) {
        chat.setSquad(null);
        return chatRepository.save(chat);
    }
    @Override
    public Chat addSquadChat(Chat chat, Long gameId, Long playerId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!playerRepository.existsById(playerId)) throw new NotFoundException("Player id " + playerId);

        Player player = playerRepository.findById(playerId).get();

        if (!Objects.equals(player, chat.getPlayer())) throw new BadRequestException("requested player is not same as chat is pointing at");
        if (!Objects.equals(player.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match players params");

        if (chat.getPlayer().getState() == PlayerState.NO_SQUAD || chat.getPlayer().getState() == PlayerState.UNREGISTERED)
            throw new ForbiddenException("To add squad chat player needs to be in a squad or admin");
        if (!squadMemberRepository.existsBySquad_IdAndPlayer_Id(chat.getSquad().getId(), playerId))
            throw new ForbiddenException("player with player id " + playerId + " is not part of squad with squad id " + chat.getSquad().getId());

        return chatRepository.save(chat);
    }

    @Override
    public Chat update(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public void deleteById(Long chatId) {
        chatRepository.deleteById(chatId);
    }

    @Override
    public void delete(Chat chat) {
        chatRepository.delete(chat);
    }
}
