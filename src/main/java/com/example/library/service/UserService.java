package com.example.library.service;

import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public User createUser(String username, String password, String roleName) {
        Role role = roleRepo.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRoles(Set.of(role));

        return userRepo.save(user);
    }
    public User updateUser(Long id, String password, String roleName, Boolean enabled) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        if (roleName != null) {
            Role role = roleRepo.findByRole(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            user.setRoles(Set.of(role));
        }

        if (enabled != null) {
            user.setEnabled(enabled);
        }
        return userRepo.save(user);
    }


    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
