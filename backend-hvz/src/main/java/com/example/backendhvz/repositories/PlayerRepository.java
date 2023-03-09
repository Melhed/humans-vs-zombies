package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Collection<Player> findAllByGameId(Long gameId);
    boolean existsPlayerByBiteCodeAndGame_Id(String biteCode, Long gameId);
    Optional<Player> findByBiteCode(String biteCode);
}
