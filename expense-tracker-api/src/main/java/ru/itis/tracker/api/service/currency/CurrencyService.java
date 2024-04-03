package ru.itis.tracker.api.service.currency;

import ru.itis.tracker.api.dto.CurrenciesPage;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.model.Currency;

public interface CurrencyService {

    CurrenciesPage getAvailableCurrencies();

    CurrencyDto findByCode(String code);

    Currency findModelByCode(String code);

}
