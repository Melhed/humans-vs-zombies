package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Chat;
import com.example.backendhvz.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface KillRepository extends JpaRepository<Kill, Long> {
    Optional<Collection<Kill>> findAllByGameId(Long gameId);
    Optional deleteAllByGameId(Long gameId);

    @Modifying
    @Query("delete Kill k where k.killer = ?1 or k.victim = ?1")
    Optional deleteKillByPlayerId(Long playerId);

    Optional<Boolean> existsByKillerId(Long playerId);
    Optional<Boolean> existsByVictimId(Long playerId);
}
