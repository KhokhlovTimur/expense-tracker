package ru.itis.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.Expense;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
