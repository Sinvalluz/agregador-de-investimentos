package com.sinvaldev.agregadordeinvestimentos.controller;

import com.sinvaldev.agregadordeinvestimentos.dtos.stock.RequestStockDto;
import com.sinvaldev.agregadordeinvestimentos.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/stock")
@AllArgsConstructor
public class StockController {


    private final StockService stockService;

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody RequestStockDto requestStockDto) {
        stockService.createStock(requestStockDto);

        return ResponseEntity.ok().build();

    }
}
