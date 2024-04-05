package ru.itis.tracker.api.service.bank;

import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountPage;

import java.util.UUID;

public interface BankAccountService {

    BankAccountDto save(AddBankAccountDto accountDto);

    BankAccountDto findByNumber(String number);

    BankAccountPage findAllByUserId(UUID id, int pageNumber);

    void delete(UUID userId, String number);

}
