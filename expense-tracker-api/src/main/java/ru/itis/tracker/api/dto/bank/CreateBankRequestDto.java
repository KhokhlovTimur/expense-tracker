package ru.itis.tracker.api.dto.bank;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Дто создания информации о банке")
public class CreateBankRequestDto {

    @Schema(description = "Наименование", example = "Банк", requiredMode = REQUIRED)
    @Size(max = 100, min = 1)
    private String name;

    @Schema(description = "БИК", example = "222222222", requiredMode = REQUIRED)
    @Size(min = 9, max = 9)
    private String bic;

    @Schema(description = "Корреспондентский счёт", example = "22222222200000000600", requiredMode = REQUIRED)
    @Size(min = 20, max = 20)
    private String correspondentAccount;

    @Schema(description = "КПП", example = "111111111", requiredMode = REQUIRED)
    @Size(min = 9, max = 9)
    private String kpp;

    @Schema(description = "ИНН", example = "111111111111", requiredMode = REQUIRED)
    @Size(min = 12, max = 12)
    private String inn;

    @Schema(description = "ОГРН", example = "1111111111111", requiredMode = REQUIRED)
    @Size(min = 13, max = 13)
    private String ogrn;

}
