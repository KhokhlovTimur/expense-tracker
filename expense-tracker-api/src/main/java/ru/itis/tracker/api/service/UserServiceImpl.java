package ru.itis.tracker.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.SignUpRequestDto;
import ru.itis.tracker.api.dto.UserDto;
import ru.itis.tracker.api.exception.AlreadyExistsException;
import ru.itis.tracker.api.mapper.UserMapper;
import ru.itis.tracker.api.model.User;
import ru.itis.tracker.api.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(SignUpRequestDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new AlreadyExistsException(
                    String.format("Почта [%s] занята", userDto.getEmail()));
        }

        User user = userMapper.toModel(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        log.info(String.format("Save user... %s", userDto));
        return userMapper.toDto(
                userRepository.save(user)
        );
    }


}
