package ru.itis.tracker.api.service.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Информация о расходах из банка")
public class BankStatement {

    private List<BankExpenseApiDto> expenses;
}
