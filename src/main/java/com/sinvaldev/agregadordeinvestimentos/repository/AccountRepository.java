package com.sinvaldev.agregadordeinvestimentos.repository;

import com.sinvaldev.agregadordeinvestimentos.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
