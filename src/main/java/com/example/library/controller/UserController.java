package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.service.LogService;
import com.example.library.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;
    private final LogService logService;

    public UserController(UserService userService, LogService logService) {
        this.userService = userService;
        this.logService = logService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public User createUser(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role,
                           Principal principal) {
        User user = userService.createUser(username, password, role);
        logService.createLog(principal.getName(), "CREATE_USER", "User", user.getId());
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) String role,
                           @RequestParam(required = false) Boolean enabled,
                           Principal principal) {
        User updated = userService.updateUser(id, password, role, enabled);
        logService.createLog(principal.getName(), "UPDATE_USER", "User", updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, Principal principal) {
        userService.deleteUser(id);
        logService.createLog(principal.getName(), "DELETE_USER", "User", id);
    }
}
