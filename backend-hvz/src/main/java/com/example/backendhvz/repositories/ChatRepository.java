package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Collection<Chat>> findAllByGameId(Long gameId);

    Optional<Collection<Chat>> findAllBySquad(Long squadId);
    @Modifying
    @Query("select c from Chat c where c.game.id = ?1 and c.isHumanGlobal = true and c.squad = null")
    Optional<Collection<Chat>> findAllByGameIdAndHumanGlobal(Long gameId);
    @Modifying
    @Query("select c from Chat c where c.game.id = ?1 and c.isZombieGlobal = true and c.squad = null")
    Optional<Collection<Chat>> findAllByGameIdAndZombieGlobal(Long gameId);
    Optional<Chat> findChatByIdAndGameId(Long gameId, Long chatId);
    @Modifying
    @Query("select c from Chat c where c.squad.id = ?1 and c.isHumanGlobal = true")
    Optional<Collection<Chat>> findAllBySquad_IdAndHumanGlobal(Long squadId);
    @Modifying
    @Query("select c from Chat c where c.squad.id = ?1 and c.isZombieGlobal = true")
    Optional<Collection<Chat>> findAllBySquad_IdAndZombieGlobal(Long squadId);
}
