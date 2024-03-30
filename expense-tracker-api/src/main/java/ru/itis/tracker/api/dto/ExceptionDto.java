package ru.itis.tracker.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Информация об ошибке")
public class ExceptionDto {
    @Schema(description = "Сообщение об ошибке")
    private String message;
}
