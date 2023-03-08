package com.example.backendhvz.services.chat;

import com.example.backendhvz.models.Chat;
import com.example.backendhvz.models.Player;
import com.example.backendhvz.models.Squad;
import com.example.backendhvz.repositories.ChatRepository;
import com.example.backendhvz.repositories.PlayerRepository;
import com.example.backendhvz.repositories.SquadRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChatServiceImpl implements ChatService {

    private  final ChatRepository chatRepository;
    private final PlayerRepository playerRepository;
    private final SquadRepository squadRepository;

    public ChatServiceImpl(ChatRepository chatRepository, PlayerRepository playerRepository, SquadRepository squadRepository) {
        this.chatRepository = chatRepository;
        this.playerRepository = playerRepository;
        this.squadRepository = squadRepository;
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
    public Collection<Chat> findAllByGameId(Long gameId, boolean playerIsHuman) {
        System.out.println(gameId);
        System.out.println(playerIsHuman);
        if (playerIsHuman) return chatRepository.findAllByGameIdAndHumanGlobal(gameId).get();
        return chatRepository.findAllByGameIdAndZombieGlobal(gameId).get();
    }

    @Override
    public Collection<Chat> findAllBySquadIdAndFaction(Long squadId, Long playerId) {
        Player player = playerRepository.findById(playerId).get();
        Squad squad = squadRepository.findById(squadId).get();
        if(squad == null || player == null || player.isHuman() != squad.isHuman()) return null;
        if(squad.isHuman()) {
            return chatRepository.findAllBySquad_IdAndHumanGlobal(squadId).get();
        }
        return chatRepository.findAllBySquad_IdAndZombieGlobal(squadId).get();
    }

    @Override
    public Chat add(Chat chat) {
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
