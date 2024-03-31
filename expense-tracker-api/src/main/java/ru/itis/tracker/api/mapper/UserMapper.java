package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.tracker.api.dto.user.SignUpRequestDto;
import ru.itis.tracker.api.dto.user.UserDto;
import ru.itis.tracker.api.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserDto userDto);

    UserDto toDto(User user);

    User toModel(SignUpRequestDto userDto);

}
