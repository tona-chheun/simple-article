package com.example.simplearticle.repositories;

import com.example.simplearticle.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndDeletedAtIsNull(String email);
    List<User> findByDeletedAtIsNull();
    Optional<User> findByIdAndDeletedAtIsNull(UUID id);
}
