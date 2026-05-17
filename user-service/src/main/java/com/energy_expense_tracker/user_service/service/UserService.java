package com.energy_expense_tracker.user_service.service;

import com.energy_expense_tracker.user_service.dto.UserDto;
import com.energy_expense_tracker.user_service.entity.User;
import com.energy_expense_tracker.user_service.exception.UserNotFoundException;
import com.energy_expense_tracker.user_service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto input){
        log.info("Creating user with email: {}", input.getEmail());
        final User createdUser = User.builder()
                .name(input.getName())
                .surname(input.getSurname())
                .email(input.getEmail())
                .address(input.getAddress())
                .alerting(input.isAlerting())
                .energyAlertingThreshold(input.getEnergyAlertingThreshold())
                .build();

        final User saved = userRepository.save(createdUser);
        log.info("User created successfully with id: {}", saved.getId());
        return toDto(saved);
    }

    public UserDto getUserById(Long id){
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Found user: {} {}", user.getName(), user.getSurname());
        return toDto(user);
    }

    public UserDto updateUser(Long id, UserDto input){
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(input.getName());
        user.setSurname(input.getSurname());
        user.setEmail(input.getEmail());
        user.setAddress(input.getAddress());
        user.setAlerting(input.isAlerting());
        user.setEnergyAlertingThreshold(input.getEnergyAlertingThreshold());

        User saved = userRepository.save(user);
        log.info("User updated successfully with id: {}", saved.getId());
        return toDto(saved);
    }

    public void deleteUser(Long id){
        log.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
        log.info("User deleted successfully with id: {}", id);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .address(user.getAddress())
                .alerting(user.isAlerting())
                .energyAlertingThreshold(user.getEnergyAlertingThreshold())
                .build();
    }
}
