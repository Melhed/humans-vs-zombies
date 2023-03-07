package com.example.backendhvz.services.chat;

import com.example.backendhvz.models.Chat;
import com.example.backendhvz.repositories.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChatServiceImpl implements ChatService {

    private  final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
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
