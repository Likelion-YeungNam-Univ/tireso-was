package com.nes.tireso.boundedContext.auth.controller;

import com.nes.tireso.boundedContext.auth.dto.SignInResponse;
import com.nes.tireso.boundedContext.auth.dto.TokenRequest;
import com.nes.tireso.boundedContext.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/oauth2/code/{registrationId}")
    public ResponseEntity<SignInResponse> redirect(
            @PathVariable("registrationId") String registrationId
            , @RequestParam("code") String code
            , @RequestParam("state") String state) {
        return ResponseEntity.ok(
                authService.redirect(
                        TokenRequest.builder()
                                .registrationId(registrationId)
                                .code(code)
                                .state(state)
                                .build()));
    }

    @PostMapping("/auth/token")
    public ResponseEntity<SignInResponse> refreshToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRequest));
    }
}