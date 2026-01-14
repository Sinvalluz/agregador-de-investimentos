package com.sinvaldev.agregadordeinvestimentos.repository;

import com.sinvaldev.agregadordeinvestimentos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByEmail(String email);
}
