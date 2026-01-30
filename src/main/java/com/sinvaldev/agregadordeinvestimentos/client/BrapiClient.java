package com.sinvaldev.agregadordeinvestimentos.client;

import com.sinvaldev.agregadordeinvestimentos.dtos.brapi.BrapiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "brapiClient", url = "https://brapi.dev")

public interface BrapiClient {

    @GetMapping("/api/quote/{stockId}")
    BrapiResponseDto getQuote(@RequestParam("token") String token, @PathVariable String stockId);
}
