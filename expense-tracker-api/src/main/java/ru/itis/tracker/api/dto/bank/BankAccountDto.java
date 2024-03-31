package ru.itis.tracker.api.dto.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tracker.api.dto.user.UserDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Информация о банковском аккаунте")
public class BankAccountDto {

    @Schema(description = "Номер счета", example = "1111111111111111111")
    private String accountNumber;

    @Schema(description = "Банк данного счета")
    private BankDto bank;

    @Schema(description = "Владелец счета")
    private UserDto user;

}
