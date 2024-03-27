package ru.itis.tracker.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name = "expense_categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
