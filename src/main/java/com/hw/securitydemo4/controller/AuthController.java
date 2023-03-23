package com.hw.securitydemo4.controller;

import com.hw.securitydemo4.model.AuthRequest;
import com.hw.securitydemo4.model.AuthResponse;
import com.hw.securitydemo4.model.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(AuthResponse.builder().token("token").build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        System.out.println(request);
        return ResponseEntity.ok(AuthResponse.builder().token("token 2").build());
    }


}

