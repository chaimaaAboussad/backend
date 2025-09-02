package com.isfin.islamicfinancial.config;

import com.isfin.islamicfinancial.entities.User;
import com.isfin.islamicfinancial.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setEmail("admin@example.com");
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("client").isEmpty()) {
                User client = new User();
                client.setUsername("client");
                client.setPassword(encoder.encode("client123"));
                client.setEmail("client@example.com");
                client.setRole("CLIENT");
                userRepository.save(client);
            }
        };
    }
}
