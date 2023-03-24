package com.example.backendhvz.repositories;

import com.example.backendhvz.models.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SquadMemberRepository extends JpaRepository<SquadMember, Long> {
    void deleteByPlayerId(Long playerId);

    Optional deleteAllByGameId(Long gameId);
    Optional deleteAllBySquadId(Long squadId);
    Optional<Collection<SquadMember>> findAllBySquadId(Long squadId);
    Boolean existsBySquadIdAndPlayerId(Long squadId, Long playerId);
    Optional<SquadMember> findSquadMemberByGame_IdAndPlayerId(Long gameId, Long playerId);
}
