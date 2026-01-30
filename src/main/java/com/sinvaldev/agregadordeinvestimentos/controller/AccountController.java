package com.sinvaldev.agregadordeinvestimentos.controller;

import com.sinvaldev.agregadordeinvestimentos.dtos.associate.RequestAssociateAccountStockDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.associate.ResponseAccountStockDto;
import com.sinvaldev.agregadordeinvestimentos.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}/stock")
    public ResponseEntity<Void> associateStock(@PathVariable String accountId,
                                              @RequestBody RequestAssociateAccountStockDto dto) {
        accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stock")
    public ResponseEntity<List<ResponseAccountStockDto>> associateStock(@PathVariable String accountId) {
        List<ResponseAccountStockDto> stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
