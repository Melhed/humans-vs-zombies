package com.example.backendhvz.services.chat;

import com.example.backendhvz.models.Chat;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface ChatService extends CRUDService<Chat, Long> {

    Chat findChatByIdAndGameId(Long gameId, Long chatId);

    Collection<Chat> findAllNonSquad();

    Collection<Chat> findAllByGameId(Long gameId, boolean playerIsHuman);

    Chat addGameChat(Long gameId, Chat chat);
    Collection<Chat> findAllBySquadId(Long squadId, Long gameId);
    Chat addSquadChat(Chat chat, Long gameId, Long playerId);
}
