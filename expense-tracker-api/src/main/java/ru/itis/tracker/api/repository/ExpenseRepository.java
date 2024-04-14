package ru.itis.tracker.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.tracker.api.model.Expense;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    Page<Expense> getAllByUserId(Pageable pageable, UUID userId);

    List<Expense> getAllByUserId(UUID userId);

}
