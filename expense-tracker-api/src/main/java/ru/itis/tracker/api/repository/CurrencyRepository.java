package ru.itis.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.Currency;

import java.util.Optional;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

    Optional<Currency> findByCode(String code);

}
