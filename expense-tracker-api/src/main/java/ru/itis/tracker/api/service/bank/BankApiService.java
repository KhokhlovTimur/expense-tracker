package ru.itis.tracker.api.service.bank;

import reactor.core.publisher.Mono;
import ru.itis.tracker.api.service.bank.dto.BankStatement;

import java.util.UUID;

public interface BankApiService {

    Mono<BankStatement> getBankStatement(String accountNumber,  UUID id);

}
