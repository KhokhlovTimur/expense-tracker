package ru.itis.tracker.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.exception.AlreadyExistsException;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.BankMapper;
import ru.itis.tracker.api.model.BankAccount;
import ru.itis.tracker.api.repository.BankAccountRepository;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankMapper bankMapper;
    private final BankService bankService;
    private final UserService userService;

    @Override
    public BankAccountDto save(AddBankAccountDto accountDto) {
        if (bankAccountRepository.existsByAccountNumber(accountDto.getAccountNumber())) {
            throw new AlreadyExistsException(String.format("Банковский счет [%s] уже существует",
                    accountDto.getAccountNumber()));
        }

        BankAccount bankAccount = bankMapper.toModel(accountDto);

        bankAccount.setBank(bankService.findModelById(accountDto.getBankId()));
        bankAccount.setUser(userService.findModelById(accountDto.getUserId()));

        return bankMapper.toDto(
                bankAccountRepository.save(bankAccount)
        );
    }

    @Override
    public BankAccountDto findByNumber(String number) {
        return bankMapper.toDto(
                getOrThrow(number)
        );
    }

    private BankAccount getOrThrow(String number) {
        return bankAccountRepository.findByAccountNumber(number)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Счета с номером [%s] не существует", number)
                ));
    }
}
