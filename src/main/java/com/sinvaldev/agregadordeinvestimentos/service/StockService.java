package com.sinvaldev.agregadordeinvestimentos.service;

import com.sinvaldev.agregadordeinvestimentos.dtos.stock.RequestStockDto;
import com.sinvaldev.agregadordeinvestimentos.model.Stock;
import com.sinvaldev.agregadordeinvestimentos.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public void createStock(RequestStockDto requestStockDto) {
        Stock stock = Stock
                .builder()
                .StockId(requestStockDto.stockId())
                .description(requestStockDto.description())
                .build();

        stockRepository.save(stock);
    }
}
