package com.zeenatInterior.interiordesign.controller;



import com.zeenatInterior.interiordesign.dto.LoginRequest;
import com.zeenatInterior.interiordesign.dto.SignUpRequest;
import com.zeenatInterior.interiordesign.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Map<String, Object> res = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(res);
    }
}
