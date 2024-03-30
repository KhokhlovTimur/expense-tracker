package ru.itis.tracker.api.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.tracker.api.dto.SignUpRequestDto;
import ru.itis.tracker.api.dto.UserDto;

public interface UserApi {

    @PostMapping("/user")
    ResponseEntity<UserDto> save(@RequestBody @Valid SignUpRequestDto userDto);
}
