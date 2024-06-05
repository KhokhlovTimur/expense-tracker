package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.UserApi;
import ru.itis.tracker.api.dto.user.SignUpRequestDto;
import ru.itis.tracker.api.dto.user.UserDto;
import ru.itis.tracker.api.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3001")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> save(SignUpRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.save(userDto));
    }

    @Override
    public ResponseEntity<UserDto> findById(UUID id) {
        return ResponseEntity.ok(
                userService.findById(id)
        );
    }

    @GetMapping("/admin")
    public void testAdminUrl(){}
}
