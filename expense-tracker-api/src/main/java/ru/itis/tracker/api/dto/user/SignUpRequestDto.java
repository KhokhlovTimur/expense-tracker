package ru.itis.tracker.api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Дто регистрации пользователя")
public class SignUpRequestDto {

    @Size(min = 1, max = 50, message = "{user.name.size}")
    @Schema(description = "Имя", example = "Harry", requiredMode = REQUIRED)
    private String name;

    @Size(min = 1, max = 50, message = "{user.surname.size}")
    @Schema(description = "Фамилия", example = "Styles", requiredMode = REQUIRED)
    private String surname;

    @Size(min = 1, max = 50, message = "{user.email.size}")
    @Email(message = "{user.email.regex}")
    @Schema(description = "Почта", example = "123@ok.com", requiredMode = REQUIRED)
    private String email;

    @Size(min = 5, max = 20, message = "{user.password.size}")
    @Schema(description = "Пароль", example = "123456789", requiredMode = REQUIRED)
    private String password;
}
