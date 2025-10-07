package com.zeenatInterior.interiordesign.service;



import com.zeenatInterior.interiordesign.dto.SignUpRequest;
import com.zeenatInterior.interiordesign.entity.Role;
import com.zeenatInterior.interiordesign.entity.User;
import com.zeenatInterior.interiordesign.repository.UserRepository;
import com.zeenatInterior.interiordesign.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, Object> register(SignUpRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);

        String token = jwtTokenProvider.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build()
        );

        return Map.of(
                "token", token,
                "user", Map.of("id", user.getId(), "name", user.getName(), "email", user.getEmail(), "role", user.getRole())
        );
    }

    public Map<String, Object> login(String email, String password) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        }
        User u = userRepo.findByEmail(email).orElseThrow();
        String token = jwtTokenProvider.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(u.getEmail())
                        .password(u.getPassword())
                        .roles(u.getRole().name())
                        .build()
        );

        return Map.of(
                "token", token,
                "user", Map.of("id", u.getId(), "name", u.getName(), "email", u.getEmail(), "role", u.getRole())
        );
    }
}