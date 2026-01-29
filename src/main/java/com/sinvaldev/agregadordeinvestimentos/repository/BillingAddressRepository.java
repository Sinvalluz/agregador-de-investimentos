package com.sinvaldev.agregadordeinvestimentos.repository;

import com.sinvaldev.agregadordeinvestimentos.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
