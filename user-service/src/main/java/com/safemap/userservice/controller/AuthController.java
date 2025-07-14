package com.safemap.userservice.controller;

import com.safemap.userservice.security.JwtUtil;
import com.safemap.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        return userService.authenticate(email, password)
                .map(user -> {
                    String token = jwtUtil.generateToken(user);
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "userId", user.getId(),
                            "username", user.getUsername(),
                            "role", user.getRole()
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
    }
}
