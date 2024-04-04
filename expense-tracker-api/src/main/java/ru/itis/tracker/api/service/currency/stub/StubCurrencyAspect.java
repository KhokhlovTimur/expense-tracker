package ru.itis.tracker.api.service.currency.stub;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.itis.tracker.api.dto.CurrencyDto;
import ru.itis.tracker.api.dto.expense.ExpenseDto;
import ru.itis.tracker.api.dto.expense.ExpensePage;

@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.currency.enable", havingValue = "false")
@Slf4j
public class StubCurrencyAspect {

    @PostConstruct
    public void init() {
        log.info("Currencies stub activated");
    }

    //Подставляет валюту в ответ, если нет подключения к апи
    @Around("execution(* ru.itis.tracker.api.service.ExpenseServiceImpl.*(..))")
    public Object logAfterAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("Валюты дописались");

        Object res = joinPoint.proceed();
        if (res instanceof ExpensePage expenses) {
            expenses.getExpenses().forEach(
                    x -> x.setCurrency(getCurrency())
            );

            return expenses;
        } else {
            ExpenseDto expense = (ExpenseDto) res;
            expense.setCurrency(getCurrency());
            return expense;
        }
    }

    private CurrencyDto getCurrency() {
        return CurrencyDto.builder()
                .code("RUB")
                .name("Рубль")
                .build();
    }
}
