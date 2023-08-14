package com.nes.tireso.boundedContext.auth.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.base.rq.Rq;

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
	@GetMapping("/login/naver")
	@Operation(summary = "네이버 로그인 API", description = "네이버 로그인 페이지로 redirect 됩니다.")
	public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {}

	@GetMapping("/authorization/kakao")
	@Operation(summary = "카카오 로그인 API", description = "카카오 로그인 페이지로 redirect 됩니다.")
	public void kakaoLogin(HttpServletRequest request, HttpServletResponse response) {}

	@GetMapping("/authorization/google")
	@Operation(summary = "구글 로그인 API", description = "구글 로그인 페이지로 redirect 됩니다.")
	public void googleLogin(HttpServletRequest request, HttpServletResponse response) {}
}
