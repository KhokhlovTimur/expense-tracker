package ru.itis.tracker.api.service.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.CurrenciesPage;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.CurrencyMapper;
import ru.itis.tracker.api.model.Currency;
import ru.itis.tracker.api.repository.CurrencyRepository;


@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "true")
public class ExternalCurrencyApiService implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public CurrencyDto findByCode(String code) {
        return currencyMapper.toDto(
                getOrThrow(code)
        );
    }

    @Override
    public Currency findModelByCode(String code) {
        return getOrThrow(code);
    }

    @Override
    public CurrenciesPage getAvailableCurrencies() {
        return CurrenciesPage.builder()
                .currencies(currencyMapper.toDtoList(currencyRepository.findAll()))
                .build();
    }

    private Currency getOrThrow(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Кода [%s] не существует", code)
                ));
    }
}
