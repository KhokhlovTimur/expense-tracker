package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.UserApi;
import ru.itis.tracker.api.dto.SignUpRequestDto;
import ru.itis.tracker.api.dto.UserDto;
import ru.itis.tracker.api.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> save(SignUpRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(userDto));
    }

    @GetMapping("/us")
    public void re()
    {}

    @GetMapping("/admin/1")
    public void d(){}
}
