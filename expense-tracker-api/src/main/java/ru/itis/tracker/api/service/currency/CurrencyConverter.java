package ru.itis.tracker.api.service.currency;


public interface CurrencyConverter {

    Double convert(String from, String to, Double amount);

}
