package com.hw.securitydemo4.service;

import com.hw.securitydemo4.entry.Token;
import com.hw.securitydemo4.repository.TokenRepository;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {
    @Resource
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String bearer = "Bearer ";
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith(bearer)) {
            return;
        }

        String jwt = authorization.substring(bearer.length());
        Token token = tokenRepository.findByToken(jwt).orElse(null);

        if (token != null) {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();
        }
    }
}
