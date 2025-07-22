package com.safemap.userservice.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    // Login & Identity
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;    // unique, not null

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;       // unique, not null

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must be at least 8 characters and contain both letters and numbers"
    )
    private String password;    // bcrypt-hashed

    // Profile
    private String firstName;
    private String lastName;

    // Roles & Status
    @NotNull(message = "Role is required")
    private Role role;          // enum {USER, ADMIN}
    private boolean enabled;    // account active or suspended

    // Audit
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    private Instant lastLogin;  // timestamp of last successful login

    public enum Role {
        USER, ADMIN
    }
}