package ru.itis.tracker.api.service.currency;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import ru.itis.tracker.api.service.currency.dto.CurrencyRateDto;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "true")
@Slf4j
public class CurrencyRateInitializer {

    private final WebClient currencyWebClient;
    private final ExecutorService executorService;
    private final String RATE_URL = "/latest";

    @Value(value = "${api.currency.key}")
    private String apiKey;
    private static final Map<String, Float> codeAndRate = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        initializeCurrenciesRate();
    }

    @Scheduled(cron = "@hourly")
    public void initializeCurrenciesRate() {
        currencyWebClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(RATE_URL)
                                .queryParam("api_key", apiKey)
                                .build())
                .retrieve()
                .bodyToFlux(CurrencyRateDto.class)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.fixedDelay(5, Duration.ofMillis(1000)))

                .subscribeOn(Schedulers.fromExecutorService(executorService))
                .subscribe(res -> {
                    log.info("Currencies rate have been initialized");

                    codeAndRate.putAll(res.getRates());
                });
    }

    public static CurrencyRateDto getCodeAndRate() {
        return CurrencyRateDto.builder()
                .rates(codeAndRate)
                .build();
    }
}
