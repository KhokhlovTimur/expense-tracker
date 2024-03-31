package ru.itis.tracker.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Currency;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private int budget;
}
