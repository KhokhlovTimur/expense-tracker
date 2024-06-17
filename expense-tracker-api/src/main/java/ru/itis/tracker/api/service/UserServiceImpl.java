package ru.itis.tracker.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.user.SignUpRequestDto;
import ru.itis.tracker.api.dto.user.UserDto;
import ru.itis.tracker.api.exception.AlreadyExistsException;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.UserMapper;
import ru.itis.tracker.api.model.User;
import ru.itis.tracker.api.repository.UserRepository;

import java.util.UUID;

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
        user.setRole(User.Role.USER);

        log.info(String.format("Save user... %s", userDto));
        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    @Override
    public UserDto findById(UUID id) {
        return userMapper.toDto(
                getOrThrow(id)
        );
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findModelById(UUID id) {
        return getOrThrow(id);
    }

    private User getOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователя с id [%s] не существует", id)
                ));
    }


}
