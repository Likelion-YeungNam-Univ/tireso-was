package com.nes.tireso.boundedContext.auth.service;


import com.nes.tireso.boundedContext.auth.dto.SignInResponse;
import com.nes.tireso.boundedContext.auth.dto.TokenRequest;
import com.nes.tireso.boundedContext.auth.dto.TokenResponse;

public interface RequestService<T> {
    SignInResponse redirect(TokenRequest tokenRequest);

    TokenResponse getToken(TokenRequest tokenRequest);

    T getUserInfo(String accessToken);

    TokenResponse getRefreshToken(String provider, String refreshToken);
}
