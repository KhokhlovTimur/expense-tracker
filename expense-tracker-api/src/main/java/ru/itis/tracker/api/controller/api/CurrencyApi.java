package ru.itis.tracker.api.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.tracker.api.dto.CurrenciesPage;
import ru.itis.tracker.api.dto.CurrencyDto;

@Tag(name = "Currency")
public interface CurrencyApi {

    @GetMapping("/currencies")
    @Operation(summary = "Получение всех валют")
    ResponseEntity<CurrenciesPage> findAll();

    @GetMapping("/currency")
    @Operation(summary = "Получение валюты по коду")
    ResponseEntity<CurrencyDto> findByCode(@RequestParam("code") String code);

}
