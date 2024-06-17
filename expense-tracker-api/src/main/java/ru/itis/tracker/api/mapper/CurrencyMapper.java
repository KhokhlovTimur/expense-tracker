package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.tracker.api.service.currency.dto.CreateCurrencyRequestDto;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.model.Currency;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency toModel(CreateCurrencyRequestDto currencyRequestDtos);

    List<CurrencyDto> toDtoList(List<Currency> currencies);

    CurrencyDto toDto(Currency currency);

}
