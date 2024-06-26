package ru.itis.tracker.api.service.bank;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import ru.itis.tracker.api.dto.bank.BanksCreateRequestPage;
import ru.itis.tracker.api.mapper.BankMapper;
import ru.itis.tracker.api.repository.BankRepository;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class BanksInitializer {

    private final WebClient banksWebClient;
    private final BankRepository bankRepository;
    private final ExecutorService executorService;
    private final BankMapper bankMapper;
    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        updateBanks();
    }

    @Scheduled(cron = "@daily")
    public void updateBanks() {

        if (isInitialized.get()) {
            return;
        }

        banksWebClient
                .get()
                .uri("/banks")
                .retrieve()
                .bodyToFlux(BanksCreateRequestPage.class)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.fixedDelay(5, Duration.ofMillis(1000)))

                .subscribeOn(Schedulers.fromExecutorService(executorService))
                .subscribe(res -> {
                    res.getBanks().stream()
                            .map(bankMapper::toModel)
                            .forEach(bankRepository::save);

                    isInitialized.set(true);
                    log.info("Banks have been initialized");
                });
    }

}
