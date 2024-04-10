package ru.itis.tracker.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name = "banks")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Bank {

    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String bic;
    private String correspondentAccount;
    private String kpp;
    private String inn;
    private String ogrn;

}
