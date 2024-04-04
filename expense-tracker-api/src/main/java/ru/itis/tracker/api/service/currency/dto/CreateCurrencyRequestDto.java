package ru.itis.tracker.api.service.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCurrencyRequestDto {

    @JsonProperty("short_code")
    private String code;
    @JsonProperty("name")
    private String name;

}
