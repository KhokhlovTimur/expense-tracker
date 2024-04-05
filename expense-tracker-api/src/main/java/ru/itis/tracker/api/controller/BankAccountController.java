package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.BankAccountApi;
import ru.itis.tracker.api.dto.bank.AddBankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountDto;
import ru.itis.tracker.api.dto.bank.BankAccountPage;
import ru.itis.tracker.api.service.bank.BankAccountService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BankAccountController implements BankAccountApi {

    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<BankAccountDto> findByAccountNumber(String number) {
        return ResponseEntity.ok(
                bankAccountService.findByNumber(number)
        );
    }

    @Override
    public ResponseEntity<BankAccountPage> findAllByUserId(UUID id, int pageNumber) {
        return ResponseEntity.ok(
                bankAccountService.findAllByUserId(id, pageNumber)
        );
    }

    @Override
    public ResponseEntity<Void> delete(UUID id, String number) {
        bankAccountService.delete(id, number);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @Override
    public ResponseEntity<BankAccountDto> save(AddBankAccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankAccountService.save(accountDto));
    }
}
