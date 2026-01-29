package com.sinvaldev.agregadordeinvestimentos.repository;

import com.sinvaldev.agregadordeinvestimentos.model.AccountStock;
import com.sinvaldev.agregadordeinvestimentos.model.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
