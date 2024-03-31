package ru.itis.tracker.api.dto.bank;

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
@Schema(description = "Дто информации о банке")
public class BankDto {

    @Schema(description = "Id банка")
    private UUID id;

    @Schema(description = "Наименование", example = "Банк")
    private String name;

    @Schema(description = "БИК", example = "222222222")
    private String bic;

    @Schema(description = "Корреспондентский счёт", example = "22222222200000000600")
    private String correspondentAccount;

    @Schema(description = "КПП", example = "111111111")
    private String kpp;

    @Schema(description = "ИНН", example = "111111111111")
    private String inn;

    @Schema(description = "ОГРН", example = "1111111111111")
    private String ogrn;
}
