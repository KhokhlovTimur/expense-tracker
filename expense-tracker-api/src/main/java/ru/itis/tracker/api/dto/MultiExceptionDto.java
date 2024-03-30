package ru.itis.tracker.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Информация об ошибках")
public class MultiExceptionDto {

    @Schema(description = "Сообщения об ошибках")
    private List<String> messages;
}
