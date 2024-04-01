package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.tracker.api.dto.bank.*;
import ru.itis.tracker.api.dto.user.UserDto;
import ru.itis.tracker.api.model.Bank;
import ru.itis.tracker.api.model.BankAccount;
import ru.itis.tracker.api.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {

    Bank toModel(CreateBankRequestDto bankRequestDto);

    Bank toModel(UpdateBankRequestDto bankDto);

    BankDto toDto(Bank bank);

    List<BankDto> toDtoList(List<Bank> banks);

    List<BankAccountDto> toAccountDtoList(List<BankAccount> bankAccounts);
    
    @Mapping(target = "bankAccounts", ignore = true)
    UserDto toUserDto(User user);

    BankAccountDto toDto(BankAccount bankAccount);

    BankAccount toModel(AddBankAccountDto accountDto);

}
