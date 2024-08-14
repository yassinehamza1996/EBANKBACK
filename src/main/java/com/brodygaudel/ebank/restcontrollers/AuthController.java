package com.brodygaudel.ebank.restcontrollers;

import com.brodygaudel.ebank.config.JwtUtil;
import com.brodygaudel.ebank.dtos.LoginRequest;
import com.brodygaudel.ebank.entities.User;
import com.brodygaudel.ebank.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String token = userService.Login(request);
        return token;
    }
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
