package ru.itis.tracker.api.service.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrenciesCreatePage {

    @JsonProperty(value = "response")
    private List<CreateCurrencyRequestDto> currencies;

}
