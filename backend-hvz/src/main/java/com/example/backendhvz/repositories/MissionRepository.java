package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Modifying
    @Query("select m from Mission m where m.game.id = ?1 and m.isHumanVisible = true")
    Optional<Collection<Mission>> findMissionsByGameIdAndHumanVisible(Long gameId);
    @Modifying
    @Query("select m from Mission m where m.game.id = ?1 and m.isZombieVisible = true")
    Optional<Collection<Mission>> findMissionsByGameIdAndZombieVisible(Long gameId);
    Optional<Mission> findMissionByIdAndGame_Id(Long gameId, Long missionId);
}
