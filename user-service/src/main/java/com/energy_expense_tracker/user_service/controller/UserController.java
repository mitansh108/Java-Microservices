package com.energy_expense_tracker.user_service.controller;

import com.energy_expense_tracker.user_service.dto.UserDto;
import com.energy_expense_tracker.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        log.info("POST /api/v1/user - Create user request received");
        UserDto created = userService.createUser(userDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable Long id){
        log.info("GET /api/v1/user/{} - Get user request received", id);
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        log.info("PUT /api/v1/user/{} - Update user request received", id);
        UserDto updated = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        log.info("DELETE /api/v1/user/{} - Delete user request received", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
