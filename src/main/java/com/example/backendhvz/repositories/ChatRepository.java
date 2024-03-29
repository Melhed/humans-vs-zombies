package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Collection<Chat>> findAllBySquad(Long squadId);
    @Modifying
    @Query("select c from Chat c where c.game.id = ?1 and c.squad = null")
    Optional<Collection<Chat>> findAllByGameId(Long gameId);
    Optional deleteAllByGameId(Long gameId);
    Optional<Chat> findChatByIdAndGameId(Long gameId, Long chatId);
    @Modifying
    @Query("select c from Chat c where c.squad.id = ?1 and c.isHumanGlobal = true")
    Optional<Collection<Chat>> findAllBySquad_IdAndHumanGlobal(Long squadId);
    @Modifying
    @Query("select c from Chat c where c.squad.id = ?1 and c.isZombieGlobal = true")
    Optional<Collection<Chat>> findAllBySquad_IdAndZombieGlobal(Long squadId);

    Optional<Boolean> existsByPlayerId(Long playerId);
    Optional<Boolean> existsBySquadId(Long squadId);

    Optional deleteAllByPlayerId(Long playerId);

    Optional deleteAllBySquadId(Long squadId);
}
