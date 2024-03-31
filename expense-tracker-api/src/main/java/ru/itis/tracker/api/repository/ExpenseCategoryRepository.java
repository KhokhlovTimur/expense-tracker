package ru.itis.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.ExpenseCategory;

import java.util.UUID;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, UUID> {
}
