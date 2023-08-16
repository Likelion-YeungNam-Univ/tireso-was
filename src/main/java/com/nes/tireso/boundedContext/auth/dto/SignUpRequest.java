package com.nes.tireso.boundedContext.auth.dto;

import com.nes.tireso.boundedContext.auth.enumType.AuthProvider;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String id;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private AuthProvider authProvider;
}
