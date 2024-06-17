package ru.itis.tracker.api.service.currency.stub;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.CurrenciesPage;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.model.Currency;
import ru.itis.tracker.api.service.currency.CurrencyService;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "false")
@Slf4j
public class StubApiCurrencyService implements CurrencyService {

    /**
     * Сделано для заглушки, если не надо подключать апи.
     * Устанавливет в валюту null.
     */
    @PostConstruct
    public void init() {
        log.info("Currencies stub is enabled");
    }

    @Override
    public CurrenciesPage getAvailableCurrencies() {
        return CurrenciesPage.builder()
                .currencies(getCurrencies())
                .build();
    }

    @Override
    public CurrencyDto findByCode(String code) {
        return CurrencyDto.builder()
                .code("EUR")
                .name("Евро")
                .build();
    }

    @Override
    public Currency findModelByCode(String code) {
        return null;
    }

    private List<CurrencyDto> getCurrencies() {
        return List.of(
                CurrencyDto.builder()
                        .code("EUR")
                        .name("Евро")
                        .build(),
                CurrencyDto.builder()
                        .code("RUB")
                        .name("Рубль")
                        .build(),
                CurrencyDto.builder()
                        .code("USD")
                        .name("Доллар")
                        .build()
        );
    }

}
