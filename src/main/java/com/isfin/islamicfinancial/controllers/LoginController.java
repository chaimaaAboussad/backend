package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.User;
import com.isfin.islamicfinancial.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty() ||
                !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        User user = optionalUser.get();

        // Hardcoded admin check
        String role;
        if (user.getUsername().equalsIgnoreCase("admin") &&
                password.equals("admin123")) { // raw password check
            role = "ADMIN";
        } else {
            role = "CLIENT";
        }

        return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "role", role
        ));
    }



    // ---------------- SIGNUP (for CLIENTS only) ----------------
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String email = body.get("email");

        // Check if username exists
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setRole("CLIENT"); // force CLIENT role

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "User registered successfully",
                "username", newUser.getUsername(),
                "role", newUser.getRole()
        ));
    }
}
