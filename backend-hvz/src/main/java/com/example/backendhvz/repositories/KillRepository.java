package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface KillRepository extends JpaRepository<Kill, Long> {
    Optional<Collection<Kill>> findAllByGameId(Long gameId);
}
