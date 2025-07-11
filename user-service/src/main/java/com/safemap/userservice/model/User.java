package com.safemap.userservice.model;

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
    private String username;    // unique, not null
    private String email;       // unique, not null
    private String password;    // bcrypt-hashed

    // Profile
    private String firstName;
    private String lastName;

    // Roles & Status
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