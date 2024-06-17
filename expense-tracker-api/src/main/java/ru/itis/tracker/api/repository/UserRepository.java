package ru.itis.tracker.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tracker.api.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
