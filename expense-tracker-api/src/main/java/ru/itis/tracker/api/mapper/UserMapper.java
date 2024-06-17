package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.user.SignUpRequestDto;
import ru.itis.tracker.api.dto.user.UserDto;
import ru.itis.tracker.api.model.BankAccount;
import ru.itis.tracker.api.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserDto userDto);

    UserDto toDto(User user);

    User toModel(SignUpRequestDto userDto);

    @Mapping(target = "user", ignore = true)
    BankAccountDto toBankAccountDto(BankAccount bankAccount);

}
