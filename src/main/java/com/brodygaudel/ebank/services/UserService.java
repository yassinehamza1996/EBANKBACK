package com.brodygaudel.ebank.services;

import com.brodygaudel.ebank.config.JwtUtil;
import com.brodygaudel.ebank.dtos.LoginRequest;
import com.brodygaudel.ebank.entities.User;
import com.brodygaudel.ebank.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(User user) {
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String Login(LoginRequest request) {

            Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
            if(optionalUser.isEmpty()){
                return "User not found";
            }
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    // Password matches, generate and return a token
                    return JwtUtil.generateToken(user.getUsername());
                } else {
                    // Password does not match
                    throw new RuntimeException("Invalid username or password");
                }
            } else {
                // User not found
                throw new RuntimeException("Invalid username or password");
            }
        }

    }


