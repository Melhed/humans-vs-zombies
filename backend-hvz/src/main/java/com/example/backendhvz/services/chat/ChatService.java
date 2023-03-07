package com.example.backendhvz.services.chat;

import com.example.backendhvz.models.Chat;
import com.example.backendhvz.services.CRUDService;

import java.util.Collection;

public interface ChatService extends CRUDService<Chat, Long> {

    Chat findChatByIdAndGameId(Long gameId, Long chatId);

    Collection<Chat> findAllByGameId(Long gameId, boolean playerIsHuman);
}
