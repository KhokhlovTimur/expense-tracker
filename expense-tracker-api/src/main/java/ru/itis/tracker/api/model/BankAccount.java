package ru.itis.tracker.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "bank_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BankAccount {

    @Id
    private String accountNumber;

    @OneToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
