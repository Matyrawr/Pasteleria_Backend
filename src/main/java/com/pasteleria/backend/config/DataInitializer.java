package com.pasteleria.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pasteleria.backend.model.Role;
import com.pasteleria.backend.model.User;
import com.pasteleria.backend.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner ensureAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByEmail("admin@example.com").orElseGet(() -> {
                User admin = new User(
                    "Admin",
                    30,
                    java.time.LocalDate.of(1990,1,1),
                    "admin@example.com",
                    passwordEncoder.encode("admin"),
                    Role.ADMIN
                );
                return userRepository.save(admin);
            });
        };
    }
}
