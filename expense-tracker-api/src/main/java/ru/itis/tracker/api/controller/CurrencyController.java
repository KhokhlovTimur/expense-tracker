package ru.itis.tracker.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.CurrencyApi;
import ru.itis.tracker.api.dto.CurrenciesPage;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.service.currency.CurrencyService;

@RestController
@RequiredArgsConstructor
public class CurrencyController implements CurrencyApi {

    private final CurrencyService currencyService;

    @Override
    public ResponseEntity<CurrencyDto> findByCode(String code) {
        return ResponseEntity.ok(
                currencyService.findByCode(code)
        );
    }

    @Override
    public ResponseEntity<CurrenciesPage> findAll() {
        return ResponseEntity.ok(
                currencyService.getAvailableCurrencies()
        );
    }
}
