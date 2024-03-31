package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.bank.BanksPage;
import ru.itis.tracker.api.dto.bank.CreateBankRequestDto;
import ru.itis.tracker.api.dto.bank.UpdateRequestBankDto;

import java.util.UUID;

public interface BankService {

    BankDto save(CreateBankRequestDto bankDto);

    BankDto update(UUID id, UpdateRequestBankDto bankDto);

    BankDto findById(UUID id);

    BanksPage findAll(int pageNumber);

}
