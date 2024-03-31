package ru.itis.tracker.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tracker.api.controller.api.BankApi;
import ru.itis.tracker.api.dto.bank.BankDto;
import ru.itis.tracker.api.dto.bank.BanksPage;
import ru.itis.tracker.api.dto.bank.CreateBankRequestDto;
import ru.itis.tracker.api.dto.bank.UpdateRequestBankDto;
import ru.itis.tracker.api.service.BankService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BankController implements BankApi {

    private final BankService bankService;

    @Override
    public ResponseEntity<BanksPage> findAll(int pageNumber) {
        return ResponseEntity.ok(
                bankService.findAll(pageNumber)
        );
    }

    @Override
    public ResponseEntity<BankDto> findById(UUID id) {
        return ResponseEntity.ok(
                bankService.findById(id)
        );
    }

    @Override
    public ResponseEntity<BankDto> update(UUID id, UpdateRequestBankDto bankDto) {
        return ResponseEntity.accepted()
                .body(bankService.update(id, bankDto));
    }

    @Override
    public ResponseEntity<BankDto> save(CreateBankRequestDto bankDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankService.save(bankDto));
    }
}
