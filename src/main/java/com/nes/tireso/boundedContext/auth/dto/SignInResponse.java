package com.nes.tireso.boundedContext.auth.dto;


import com.nes.tireso.boundedContext.auth.enumType.AuthProvider;
import com.nes.tireso.boundedContext.socialLogin.dto.GoogleUserInfo;
import com.nes.tireso.boundedContext.socialLogin.dto.KakaoUserInfo;
import com.nes.tireso.boundedContext.socialLogin.dto.NaverUserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResponse {
    private AuthProvider authProvider;
    private KakaoUserInfo kakaoUserInfo;
    private NaverUserInfo naverUserInfo;
    private GoogleUserInfo googleUserInfo;
    private String accessToken;
    private String refreshToken;

    @Builder
    public SignInResponse(
            AuthProvider authProvider
            , KakaoUserInfo kakaoUserInfo
            , NaverUserInfo naverUserInfo
            , GoogleUserInfo googleUserInfo
            , String accessToken
            , String refreshToken
    ) {
        this.authProvider = authProvider;
        this.kakaoUserInfo = kakaoUserInfo;
        this.naverUserInfo = naverUserInfo;
        this.googleUserInfo = googleUserInfo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
