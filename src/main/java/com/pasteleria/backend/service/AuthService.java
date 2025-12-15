package com.pasteleria.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pasteleria.backend.dto.auth.AuthResponse;
import com.pasteleria.backend.dto.auth.LoginRequest;
import com.pasteleria.backend.dto.auth.RegisterRequest;
import com.pasteleria.backend.model.Role;
import com.pasteleria.backend.model.User;
import com.pasteleria.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(
            request.getNombre(),
            request.getEdad(),
            request.getFechaNacimiento(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword()),
            Role.USER
        );

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        
        return new AuthResponse(token);
    }
}
