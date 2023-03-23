package com.hw.securitydemo4.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
