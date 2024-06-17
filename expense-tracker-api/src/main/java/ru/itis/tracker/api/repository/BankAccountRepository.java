package ru.itis.tracker.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.BankAccount;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    boolean existsByAccountNumber(String number);

    Optional<BankAccount> findByAccountNumber(String accountNumber);

    Page<BankAccount> findAllByUserId(Pageable pageable, UUID uuid);
}
