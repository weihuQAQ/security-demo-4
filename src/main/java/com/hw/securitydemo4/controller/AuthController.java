package com.hw.securitydemo4.controller;

import com.hw.securitydemo4.model.AuthRequest;
import com.hw.securitydemo4.model.AuthResponse;
import com.hw.securitydemo4.model.RegisterRequest;
import com.hw.securitydemo4.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(AuthResponse.builder().token("token 2").build());
    }
}

