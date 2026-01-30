package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.dtos.associate.RequestAssociateAccountStockDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.associate.ResponseAccountStockDto;
import com.sinvaldev.agregadordeinvestimentos.exception.NotFoundException;
import com.sinvaldev.agregadordeinvestimentos.model.Account;
import com.sinvaldev.agregadordeinvestimentos.model.AccountStock;
import com.sinvaldev.agregadordeinvestimentos.model.AccountStockId;
import com.sinvaldev.agregadordeinvestimentos.model.Stock;
import com.sinvaldev.agregadordeinvestimentos.repository.AccountRepository;
import com.sinvaldev.agregadordeinvestimentos.repository.AccountStockRepository;
import com.sinvaldev.agregadordeinvestimentos.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;


    public void associateStock(String accountId, RequestAssociateAccountStockDto dto) {
        Account account = accountRepository
                .findById(UUID.fromString(accountId))
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        Stock stock = stockRepository
                .findById(dto.stockId())
                .orElseThrow(() -> new NotFoundException("Ação não encontrada"));

        AccountStockId accountStockId = new AccountStockId(account.getAccountId(), stock.getStockId());

        AccountStock accountStock = AccountStock
                .builder()
                .id(accountStockId)
                .account(account)
                .stock(stock)
                .quantity(dto.quantity())
                .build();

        accountStockRepository.save(accountStock);
    }

    public List<ResponseAccountStockDto> listStocks(String accountId) {
        Account account = accountRepository
                .findById(UUID.fromString(accountId))
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));

        return account
                .getAccountStocks()
                .stream()
                .map(accountStock ->
                        new ResponseAccountStockDto(accountStock.getStock().getStockId(), accountStock.getQuantity(), 0.0))
                .toList();
    }
}
