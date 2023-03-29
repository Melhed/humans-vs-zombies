package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {
    Optional<Collection<Squad>> findSquadsByGame_Id(Long gameId);
    Optional<Squad> findSquadByIdAndGame_Id(Long gameId, Long squadId);
    Optional deleteAllByGameId(Long gameId);

}
