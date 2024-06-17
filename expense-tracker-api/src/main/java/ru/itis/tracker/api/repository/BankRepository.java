package ru.itis.tracker.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.Bank;

import java.util.Optional;
import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {

    Boolean existsByBic(String bic);

    Boolean existsByInn(String inn);

    Boolean existsByCorrespondentAccount(String corr);

    Optional<Bank> findByName(String name);

    Boolean existsByOgrn(String ogrn);

}
