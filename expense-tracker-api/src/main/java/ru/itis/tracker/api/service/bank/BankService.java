package ru.itis.tracker.api.service.bank;

import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.bank.BanksPage;
import ru.itis.tracker.api.dto.bank.CreateBankRequestDto;
import ru.itis.tracker.api.dto.bank.UpdateBankRequestDto;
import ru.itis.tracker.api.model.Bank;

import java.util.UUID;

public interface BankService {

    BankDto save(CreateBankRequestDto bankDto);

    BankDto update(UUID id, UpdateBankRequestDto bankDto);

    BankDto findById(UUID id);

    Bank findModelById(UUID id);

    BanksPage findAll(int pageNumber);

}
