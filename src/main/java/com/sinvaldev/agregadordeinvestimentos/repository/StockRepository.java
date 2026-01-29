package com.sinvaldev.agregadordeinvestimentos.repository;

import com.sinvaldev.agregadordeinvestimentos.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
