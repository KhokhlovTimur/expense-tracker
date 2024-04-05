package ru.itis.tracker.api.service.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Расход из выписки")
public class BankExpenseApiDto {

    @Schema(description = "Сумма расхода")
    private Double amount;

    @Schema(description = "Время совершения операции")
    private Timestamp time;

    @Schema(description = "Краткое описание")
    private String description;
}
