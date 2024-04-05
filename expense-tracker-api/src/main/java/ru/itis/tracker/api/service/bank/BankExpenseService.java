package ru.itis.tracker.api.service.bank;

import ru.itis.tracker.api.service.bank.dto.BankStatement;

import java.util.UUID;

public interface BankExpenseService {

    BankStatement getAll(UUID userId);

}
