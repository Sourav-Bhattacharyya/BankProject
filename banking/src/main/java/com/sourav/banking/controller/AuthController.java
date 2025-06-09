package com.sourav.banking.controller;

import com.sourav.banking.DTO.AuthRequest;
import com.sourav.banking.DTO.AuthResponse;
import com.sourav.banking.DTO.RegisterRequest;
import com.sourav.banking.entity.User;
import com.sourav.banking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        User registeredUser = authService.registerUser(request);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) throws Exception {
        String token = authService.authenticateAndGetToken(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(Authentication authentication){
        String username = authentication.getName();
        authService.deleteUser(username);
        return ResponseEntity.ok("User account deleted successfully");
    }
}