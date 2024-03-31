package ru.itis.tracker.api.service;

import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;

public interface BankAccountService {

    BankAccountDto save(AddBankAccountDto accountDto);

    BankAccountDto findByNumber(String number);

}
