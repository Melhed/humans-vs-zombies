package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Collection<Player> findAllByGameId(Long gameId);
}
