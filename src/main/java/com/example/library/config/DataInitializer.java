package com.example.library.config;

import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepo,
                                   UserRepository userRepo,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepo.findByRole("ADMIN")
                    .orElseGet(() -> roleRepo.save(new Role("ADMIN")));

            Role librarianRole = roleRepo.findByRole("LIBRARIAN")
                    .orElseGet(() -> roleRepo.save(new Role("LIBRARIAN")));

            Role staffRole = roleRepo.findByRole("STAFF")
                    .orElseGet(() -> roleRepo.save(new Role("STAFF")));

            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setEnabled(true);
                admin.setRoles(Collections.singleton(adminRole));

                userRepo.save(admin);
            }
        };
    }
}
