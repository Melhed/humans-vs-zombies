package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    Optional<Collection<Mission>> findMissionsByGame_Id(Long gameId);
    Optional<Mission> findMissionByIdAndGame_Id(Long gameId, Long missionId);
}
