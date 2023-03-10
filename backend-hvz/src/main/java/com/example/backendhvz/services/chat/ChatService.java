package com.example.backendhvz.services.chat;

import com.example.backendhvz.models.Chat;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface ChatService extends CRUDService<Chat, Long> {

    Chat findChatByIdAndGameId(Long gameId, Long chatId);

    Collection<Chat> findAllNonSquad();

    Collection<Chat> findAllByGameId(Long gameId, boolean playerIsHuman);
    Collection<Chat> findAllBySquadIdAndFaction(Long squadId, Long playerId);

    Chat addGameChat(Long gameId, Chat chat);

    Chat addSquadChat(Chat chat);
}
