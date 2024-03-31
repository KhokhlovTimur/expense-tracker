package ru.itis.tracker.api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Дто пользователя")
public class UserDto {

    @Schema(description = "Id пользователя")
    private UUID id;

    @Schema(description = "Имя", example = "Harry")
    private String name;

    @Schema(description = "Фамилия", example = "Styles")
    private String surname;

    @Schema(description = "Почта", example = "123@ok.com")
    private String email;
}
