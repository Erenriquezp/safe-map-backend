package com.safemap.userservice.service;

import com.safemap.userservice.model.User;
import com.safemap.userservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Create / Register
    public User create(User user) {
        // Validar username único
        repo.findByUsername(user.getUsername()).ifPresent(existing -> {
            throw new IllegalArgumentException("Username already exists");
        });

        // Validar email único
        repo.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email already exists");
        });
        // Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Defaults
        user.setEnabled(true);
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        return repo.save(user);
    }

    // Read all
    public List<User> findAll() {
        return repo.findAll();
    }

    // Read by ID
    public Optional<User> findById(String id) {
        return repo.findById(id);
    }

    // Update (all fields except password unless provided)
    public Optional<User> update(String id, User updated) {
        return repo.findById(id).map(existing -> {
            existing.setUsername(updated.getUsername());
            existing.setEmail(updated.getEmail());
            if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
                existing.setPassword(passwordEncoder.encode(updated.getPassword()));
            }
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setRole(updated.getRole());
            existing.setEnabled(updated.isEnabled());
            existing.setUpdatedAt(Instant.now());
            return repo.save(existing);
        });
    }

    // Delete
    public void delete(String id) {
        repo.deleteById(id);
    }

    // Authenticate (used by AuthController later)
    public Optional<User> authenticate(String email, String rawPassword) {
        return repo.findByEmail(email)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()))
                .map(u -> {
                    u.setLastLogin(Instant.now());
                    repo.save(u);
                    return u;
                });
    }
}