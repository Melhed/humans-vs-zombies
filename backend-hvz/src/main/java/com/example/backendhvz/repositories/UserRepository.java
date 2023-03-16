package com.example.backendhvz.repositories;

import com.example.backendhvz.models.HvZUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<HvZUser, String> {
}
