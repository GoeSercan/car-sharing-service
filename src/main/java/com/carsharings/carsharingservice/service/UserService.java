package com.carsharings.carsharingservice.service;

import com.carsharings.carsharingservice.model.User;
import com.carsharings.carsharingservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(User newUser) {
        Objects.requireNonNull(newUser, "New user cannot be null");

        // Check if username already exists
        if (userRepository.existsByUserName(newUser.getUserName())) {
            throw new IllegalArgumentException("User already exists with username: " + newUser.getUserName());
        }

        User savedUser = userRepository.save(newUser);
        logger.info("User registered with ID: {}", savedUser.getId());
        return savedUser;
    }

    public Optional<String> loginUser(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");

        // Find user by username and validate credentials
        return userRepository.findByUserName(username)
                .filter(user -> user.getUserPassword().equals(password))
                .map(user -> {
                    // Generate token (UUID ) //ToDo USE JWT!
                    String token = UUID.randomUUID().toString();
                    logger.info("User logged in successfully: {}", username);
                    return token;
                });
    }

    public boolean logoutUser(String token) {
        logger.info("User logged out with token: {}", token);
        return true;
    }

    public List<User> getAllUsers(String token) {
        if (!isUserFleetManager(token)) {
            throw new SecurityException("Access denied: only fleet managers can fetch all users.");
        }
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> updateUser(String token, String userId, User updatedUser) {
        boolean isFleetManager = isUserFleetManager(token);
        return userRepository.findById(userId).map(existingUser -> {
            // Check if it is fleet manager or the user
            if (!isFleetManager && !existingUser.getId().equals(userId)) {
                logger.warn("Unauthorized update attempt by user: {}", userId);
                return Optional.<User>empty();
            }
            updatedUser.setId(existingUser.getId()); // Retain original ID
            User savedUser = userRepository.save(updatedUser);
            logger.info("User updated successfully: {}", savedUser.getId());
            return Optional.of(savedUser);
        }).orElse(Optional.empty());
    }

    public boolean isUserFleetManager(String userId) {
        return userRepository.findById(userId)
                .map(user -> "fleetmanager".equals(user.getRole()))
                .orElse(false);
    }
}
