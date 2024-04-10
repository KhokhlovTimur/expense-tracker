package ru.itis.tracker.api.service.bank;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.itis.tracker.api.exception.BankApiException;
import ru.itis.tracker.api.service.bank.dto.BankStatement;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankApiDispatcher implements BankApiService {

    private final WebClient banksWebClient;

    @Override
    public Mono<BankStatement> getBankStatement(String accountNumber, UUID id) {

        return banksWebClient
                .get()
                .uri(String.format("/bank/%s/account/%s", id, accountNumber))
                .retrieve()
                .bodyToMono(BankStatement.class)
                .timeout(Duration.ofMillis(10000));
    }
}
