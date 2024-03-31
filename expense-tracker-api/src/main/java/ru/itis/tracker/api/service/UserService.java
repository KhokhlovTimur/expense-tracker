package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.user.SignUpRequestDto;
import ru.itis.tracker.api.dto.user.UserDto;

public interface UserService {
    UserDto save(SignUpRequestDto userDto);
}
