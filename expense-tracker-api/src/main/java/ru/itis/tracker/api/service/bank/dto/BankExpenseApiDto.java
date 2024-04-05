package ru.itis.tracker.api.service.bank.dto;

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
public class BankExpenseApiDto {

    private UUID id;

    private UUID userId;

    private Double amount;

    private Timestamp time;

    private String description;
}
