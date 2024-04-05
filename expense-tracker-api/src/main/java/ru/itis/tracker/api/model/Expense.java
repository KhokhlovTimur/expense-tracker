package ru.itis.tracker.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity(name = "expenses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp time;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
}
