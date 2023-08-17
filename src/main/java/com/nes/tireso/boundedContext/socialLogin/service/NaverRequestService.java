package com.nes.tireso.boundedContext.socialLogin.service;

import com.nes.tireso.base.security.SecurityUtil;
import com.nes.tireso.boundedContext.auth.dto.SignInResponse;
import com.nes.tireso.boundedContext.auth.dto.TokenRequest;
import com.nes.tireso.boundedContext.auth.dto.TokenResponse;
import com.nes.tireso.boundedContext.auth.enumType.AuthProvider;
import com.nes.tireso.boundedContext.auth.service.RequestService;
import com.nes.tireso.boundedContext.member.repository.MemberRepository;
import com.nes.tireso.boundedContext.socialLogin.dto.NaverUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

;
;

@Service
@RequiredArgsConstructor
public class NaverRequestService implements RequestService {
    private final MemberRepository memberRepository;
    private final SecurityUtil securityUtil;
    private final WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private String GRANT_TYPE;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.provider.naver.token_uri}")
    private String TOKEN_URI;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String USER_INFO_URI;

    @Override
    public SignInResponse redirect(TokenRequest tokenRequest) {
        TokenResponse tokenResponse = getToken(tokenRequest);
        NaverUserInfo naverUserInfo = getUserInfo(tokenResponse.getAccessToken());

        if (memberRepository.existsById(naverUserInfo.getResponse().getId())) {
            String accessToken = securityUtil.createAccessToken(
                    naverUserInfo.getResponse().getId(), AuthProvider.NAVER, tokenResponse.getAccessToken());
            String refreshToken = securityUtil.createRefreshToken(
                    naverUserInfo.getResponse().getId(), AuthProvider.NAVER, tokenResponse.getRefreshToken());
            return SignInResponse.builder()
                    .authProvider(AuthProvider.NAVER)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return SignInResponse.builder()
                    .authProvider(AuthProvider.NAVER)
                    .naverUserInfo(naverUserInfo)
                    .build();
        }
    }

    @Override
    public TokenResponse getToken(TokenRequest tokenRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", GRANT_TYPE);
        formData.add("client_id", CLIENT_ID);
        formData.add("client_secret", CLIENT_SECRET);
        formData.add("code", tokenRequest.getCode());
        formData.add("state", tokenRequest.getState());

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    @Override
    public NaverUserInfo getUserInfo(String accessToken) {
        return webClient.mutate()
                .baseUrl(USER_INFO_URI)
                .build()
                .get()
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(NaverUserInfo.class)
                .block();
    }

    @Override
    public TokenResponse getRefreshToken(String provider, String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", CLIENT_ID);
        formData.add("refresh_token", refreshToken);

        return webClient.mutate()
                .baseUrl(TOKEN_URI)
                .build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.just(new BadRequestException()))
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
