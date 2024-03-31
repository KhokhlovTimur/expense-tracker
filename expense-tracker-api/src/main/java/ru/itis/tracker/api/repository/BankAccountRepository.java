package ru.itis.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    boolean existsByAccountNumber(String number);

    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
