package ru.itis.tracker.api.mapper;

import org.mapstruct.Mapper;
import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.bank.CreateBankRequestDto;
import ru.itis.tracker.api.dto.bank.UpdateRequestBankDto;
import ru.itis.tracker.api.model.Bank;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {

    Bank toModel(CreateBankRequestDto bankRequestDto);

    Bank toModel(UpdateRequestBankDto bankDto);

    BankDto toDto(Bank bank);

    List<BankDto> toDtoList(List<Bank> banks);

}
