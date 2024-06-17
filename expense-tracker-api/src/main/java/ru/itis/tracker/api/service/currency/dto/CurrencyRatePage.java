package ru.itis.tracker.api.service.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CurrencyRatePage {

    private List<CurrencyRateDto> response;

}
