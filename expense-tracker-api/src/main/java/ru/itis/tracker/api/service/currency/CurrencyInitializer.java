package ru.itis.tracker.api.service.currency;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import ru.itis.tracker.api.mapper.CurrencyMapper;
import ru.itis.tracker.api.repository.CurrencyRepository;
import ru.itis.tracker.api.service.currency.dto.CurrenciesCreatePage;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "true")
@Slf4j
public class CurrencyInitializer {

    private final WebClient currencyWebClient;
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final ExecutorService executorService;

    @Value(value = "${api.currency.key}")
    private String apiKey;

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    @PostConstruct
    public void initialCheck() {
        log.info("Api currencies initializer is enabled");
        if (!currencyRepository.findAll().isEmpty()) {
            isInitialized.set(true);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeCurrencies() {

        if (isInitialized.get()) {
            log.info("Currencies is already initialized");
            return;
        }

        currencyWebClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/currencies")
                                .queryParam("type", "fiat")
                                .queryParam("api_key", apiKey)
                                .build())
                .retrieve()
                .bodyToFlux(CurrenciesCreatePage.class)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.fixedDelay(5, Duration.ofMillis(1000)))

                .subscribeOn(Schedulers.fromExecutorService(executorService))
                .subscribe(res -> {
                    log.info("Currencies have been initialized");

                    currencyRepository.saveAll(res.getCurrencies().stream()
                            .map(currencyMapper::toModel)
                            .toList());

                    isInitialized.set(true);
                });

    }

}
