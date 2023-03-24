package com.hw.securitydemo4.service;

import com.hw.securitydemo4.entry.Token;
import com.hw.securitydemo4.entry.User;
import com.hw.securitydemo4.enums.Role;
import com.hw.securitydemo4.enums.TokenType;
import com.hw.securitydemo4.model.AuthRequest;
import com.hw.securitydemo4.model.AuthResponse;
import com.hw.securitydemo4.model.RegisterRequest;
import com.hw.securitydemo4.repository.TokenRepository;
import com.hw.securitydemo4.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private TokenRepository tokenRepository;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtService jwtService;
    @Resource
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(user);

        saveUserToken(savedUser, token);

        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (user != null) {
            // enable single login
            revokeAllUserTokens(user);

            var token = jwtService.generateToken(user);
            saveUserToken(user, token);

            return AuthResponse.builder()
                    .token(token)
                    .build();
        }
        return null;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUserIdAndExpiredFalseOrRevokedFalse(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
