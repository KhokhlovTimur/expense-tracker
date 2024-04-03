package ru.itis.tracker.api.dto.bank;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Дто добавления информации о банковском счете")
public class AddBankAccountDto {

    @Schema(description = "Номер счета", example = "11111111111111111111", requiredMode = REQUIRED)
    @Size(min = 20, max = 20, message = "{addBunkDto.accountNumber.size}")
    private String accountNumber;

    @Schema(description = "Id банка данного счета", requiredMode = REQUIRED)
    private UUID bankId;

    @Schema(description = "Id владельца счета", requiredMode = REQUIRED)
    private UUID userId;

}
