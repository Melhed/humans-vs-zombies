package com.example.backendhvz.services.chat;

import com.example.backendhvz.enums.PlayerState;
import com.example.backendhvz.exceptions.BadRequestException;
import com.example.backendhvz.exceptions.ForbiddenException;
import com.example.backendhvz.exceptions.NotFoundException;
import com.example.backendhvz.models.Chat;
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
        if(!chatRepository.existsById(chatId)) throw new NotFoundException("Chat with ID " + chatId + " not found.");
        return chatRepository.findById(chatId).get();
    }

    @Override
    public Chat findChatByIdAndGameId(Long gameId, Long chatId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        if(!chatRepository.existsById(chatId)) throw new NotFoundException("Chat with ID " + chatId + " not found.");
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
    public Collection<Chat> findAllByGameId(Long gameId) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        return chatRepository.findAllByGameId(gameId).get();
    }

    @Override
    public Collection<Chat> findAllBySquadId(Long squadId, Long gameId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);
        if (!squadRepository.existsById(squadId)) throw new NotFoundException("Squad id " + squadId);
        Squad squad = squadRepository.findById(squadId).get();

        if (!Objects.equals(squad.getGame().getId(), gameId)) throw new BadRequestException("Game id does not match squads params");
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
    public Chat addGameChat(Long gameId, Chat chat) {
        if(!gameRepository.existsById(gameId)) throw new NotFoundException("Game with ID " + gameId + " not found.");
        chat.setSquad(null);
        return chatRepository.save(chat);
    }

    @Override
    public Chat addSquadChat(Chat chat, Long gameId, Long squadId) throws BadRequestException, NotFoundException, ForbiddenException {
        if (!gameRepository.existsById(gameId)) throw new NotFoundException("Game id " + gameId);

        if (chat.getPlayer().getState() == PlayerState.NO_SQUAD || chat.getPlayer().getState() == PlayerState.UNREGISTERED)
            throw new ForbiddenException("To add squad chat player needs to be in a squad or admin");

        return chatRepository.save(chat);
    }

    @Override
    public Chat update(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public void deleteBySquadId(Long squadId) {
        if(!chatRepository.existsBySquadId(squadId).get()) throw new NotFoundException("No chats found with squad ID " + squadId);
        chatRepository.deleteAllBySquadId(squadId);
    }

    @Override
    public void deleteByPlayerId(Long playerId) {
        if(!chatRepository.existsByPlayerId(playerId).get()) throw new NotFoundException("No chats found by player with ID " + playerId);
        chatRepository.deleteAllByPlayerId(playerId);
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
