package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.client.BrapiClient;
import com.sinvaldev.agregadordeinvestimentos.dtos.associate.RequestAssociateAccountStockDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.associate.ResponseAccountStockDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.brapi.BrapiResponseDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

    @Value("#{environment.TOKEN}")
    private  String TOKEN;

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


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
                        new ResponseAccountStockDto(accountStock.getStock().getStockId(), accountStock.getQuantity(), getTotal(accountStock.getStock().getStockId(), accountStock.getQuantity())))
                .toList();
    }

    private double getTotal(String stockId, Integer quantity) {
        BrapiResponseDto brapiResponseDto = brapiClient.getQuote(TOKEN, stockId);

        double price = brapiResponseDto.results().getFirst().regularMarketPrice();

        return Math.round((quantity * price) * 100.0) / 100.0;
    }
}
