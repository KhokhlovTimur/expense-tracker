package ru.itis.tracker.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class AppConfig {

    @Value(value = "${api.currency.uri}")
    private String currencyApiUri;
    @Value(value = "${api.bank.uri}")
    private String bankApiUri;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(20));
    }

    @Bean(name = "currencyWebClient")
    @ConditionalOnProperty(name = "api.currency.enable", havingValue = "true")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(currencyApiUri)
                .build();
    }

    @Bean(name = "banksWebClient")
    public WebClient banksWebClient() {
        return WebClient.builder()
                .baseUrl(bankApiUri)
                .build();
    }
}
