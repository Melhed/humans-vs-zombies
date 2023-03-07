package com.example.backendhvz.repositories;

import com.example.backendhvz.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KillRepository extends JpaRepository<Kill, Long> {
}
