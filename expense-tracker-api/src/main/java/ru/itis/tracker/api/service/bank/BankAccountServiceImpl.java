package ru.itis.tracker.api.service.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountPage;
import ru.itis.tracker.api.exception.AlreadyExistsException;
import ru.itis.tracker.api.exception.NotFoundException;
import ru.itis.tracker.api.mapper.BankMapper;
import ru.itis.tracker.api.model.Bank;
import ru.itis.tracker.api.model.BankAccount;
import ru.itis.tracker.api.model.User;
import ru.itis.tracker.api.repository.BankAccountRepository;
import ru.itis.tracker.api.service.UserService;
import ru.itis.tracker.api.service.bank.dto.BankStatement;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankMapper bankMapper;
    private final BankService bankService;
    private final UserService userService;
    private final BankApiService bankApiService;

    @Value(value = "${default.page-size}")
    private int pageSize;

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

    @Override
    public BankAccountPage findAllByUserId(UUID id, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<BankAccount> accounts = bankAccountRepository.findAllByUserId(pageRequest, id);

        return BankAccountPage.builder()
                .bankAccounts(bankMapper.toAccountDtoList(accounts.getContent()))
                .elementsTotalCount(accounts.getNumberOfElements())
                .pagesCount(accounts.getTotalPages())
                .build();
    }

    @Override
    public void delete(UUID userId, String number) {
        BankAccount account = getOrThrow(number);

        User user = account.getUser();
        user.getBankAccounts().remove(account);
        userService.update(user);

        bankAccountRepository.delete(account);
    }

    @Override
    public BankStatement getStatement(String accountNumber) {
        BankAccount acc = getOrThrow(accountNumber);
        Bank bank = bankService.findModelById(acc.getBank().getId());
        return bankApiService.getBankStatement(accountNumber, bank.getId())
                .block();
    }

    private BankAccount getOrThrow(String number) {
        return bankAccountRepository.findByAccountNumber(number)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Счета с номером [%s] не существует", number)
                ));
    }
}
