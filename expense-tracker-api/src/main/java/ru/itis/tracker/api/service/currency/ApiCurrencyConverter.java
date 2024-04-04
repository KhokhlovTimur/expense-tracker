package ru.itis.tracker.api.service.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.itis.tracker.api.service.currency.CurrencyRateInitializer.getCodeAndRate;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "true")
@Slf4j
public class ApiCurrencyConverter implements CurrencyConverter {

    @Override
    public Double convert(String from, String to, Double amount) {
        BigDecimal converted = BigDecimal.valueOf(amount / getCodeAndRate().getRates().get(from) * getCodeAndRate().getRates().get(to));
        converted = converted.setScale(5, RoundingMode.HALF_UP);
        return converted.doubleValue();
    }
}
