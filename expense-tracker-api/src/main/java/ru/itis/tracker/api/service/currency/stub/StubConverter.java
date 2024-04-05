package ru.itis.tracker.api.service.currency.stub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.itis.tracker.api.service.currency.CurrencyConverter;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "false")
@Slf4j
public class StubConverter implements CurrencyConverter {

    @Override
    public Double convert(String from, String to, Double amount) {
        return Double.parseDouble("100");
    }
}
