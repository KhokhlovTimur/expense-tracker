package ru.itis.tracker.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Информация о валюте")
public class CurrencyDto {

    @Schema(description = "Внутренний Id валюты")
    private UUID id;

    @Schema(description = "Код валюты", example = "RUB")
    private String code;

    @Schema(description = "Наименование валюты", example = "Russian Ruble")
    private String name;

}
