package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.user.SignUpRequestDto;
import ru.itis.tracker.api.dto.user.UserDto;
import ru.itis.tracker.api.model.User;

import java.util.UUID;

public interface UserService {
    UserDto save(SignUpRequestDto userDto);

    User findModelById(UUID id);

    UserDto findById(UUID id);
}
