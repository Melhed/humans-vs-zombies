package com.example.backendhvz.repositories;

import com.example.backendhvz.models.SquadCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SquadCheckInRepository extends JpaRepository<SquadCheckIn, Long> {
    Optional<Collection<SquadCheckIn>> findAllBySquad_Id(Long squadId);
}
