package com.nes.tireso.boundedContext.auth.controller;

import java.io.IOException;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.base.rq.Rq;
import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "OAuth를 통한 사용자 인증 관련 API")
public class AuthController {
	private final AuthService authService;
	@GetMapping("/login/naver")
	@Operation(summary = "네이버 로그인 API", description = "네이버 로그인 페이지로 redirect 됩니다.")
	public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(authService.getNaverAuthUri());
	}

	@GetMapping("/login/naver/callback")
	public ResponseEntity<TokenDto> naverLoginCallback(@RequestParam String code) throws IOException {
		TokenDto tokenDto = authService.getNaverToken("token", code);
		OAuthInfoDto oAuthInfoDto = authService.getNaverUserInfo(tokenDto);
		authService.signIn(oAuthInfoDto);
		return new ResponseEntity<>(tokenDto, HttpStatus.OK);
	}
}
