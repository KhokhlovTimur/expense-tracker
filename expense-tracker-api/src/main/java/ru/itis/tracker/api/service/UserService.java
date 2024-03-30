package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.SignUpRequestDto;
import ru.itis.tracker.api.dto.UserDto;

public interface UserService {
    UserDto save(SignUpRequestDto userDto);
}
