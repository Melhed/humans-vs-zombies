package com.example.backendhvz.repositories;

import com.example.backendhvz.models.SquadMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadMemberRepository extends JpaRepository<SquadMember, Long> {
}
