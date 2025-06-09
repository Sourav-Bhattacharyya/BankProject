package com.sourav.banking.service;

import com.sourav.banking.DTO.AuthRequest;
import com.sourav.banking.DTO.RegisterRequest;
import com.sourav.banking.entity.User;
import com.sourav.banking.repositoires.UserRepository;
import com.sourav.banking.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registerUser(RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole("USER");

        return userRepository.save(user);
    }

    public String authenticateAndGetToken(AuthRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        return jwtUtil.genrateUserToken(request.getUsername());
    }

    public void deleteUser(String userName){
        Optional<User> user=userRepository.findByUsername(userName);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        userRepository.delete(user.get());
    }
}
