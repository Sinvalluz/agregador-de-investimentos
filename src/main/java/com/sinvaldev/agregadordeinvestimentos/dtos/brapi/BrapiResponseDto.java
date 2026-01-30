package com.sinvaldev.agregadordeinvestimentos.dtos.brapi;

import com.sinvaldev.agregadordeinvestimentos.dtos.stock.StockDto;

import java.util.List;

public record BrapiResponseDto(List<StockDto> results) {
}
