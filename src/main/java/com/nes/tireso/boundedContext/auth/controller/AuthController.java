package com.nes.tireso.boundedContext.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.dto.UserInfoDto;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.entity.UserInfo;
import com.nes.tireso.boundedContext.auth.service.GoogleService;
import com.nes.tireso.boundedContext.auth.service.KakaoService;
import com.nes.tireso.boundedContext.auth.service.MemberService;
import com.nes.tireso.boundedContext.auth.service.NaverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "사용자 인증 관련 API")
public class AuthController {
	private final MemberService memberService;
	private final NaverService naverService;
	private final KakaoService kakaoService;
	private final GoogleService googleService;

	private final String mainPageUrl = "http://localhost:3000/main";

	@Resource
	private UserInfo userInfo;

	@GetMapping("/sign-in/naver")
	@Operation(summary = "네이버 로그인 메서드", description = "네이버 로그인을 하기 위한 메서드입니다.")
	public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(naverService.getAuthorizationUrl());
	}

	@GetMapping("/sign-in/naver/callback")
	@Operation(summary = "네이버 로그인 콜백 메서드", description = "네이버 로그인을 성공하면 실행되는 메서드입니다.")
	public void naverCallback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TokenDto token = naverService.getNaverToken("token", code);
		OAuthInfoDto oauthInfoDto = naverService.getUserInfo(token);
		Member member = memberService.create(oauthInfoDto);

		userInfo.setUserId(member.getId());
		userInfo.setUserNm(member.getName());

		response.sendRedirect(mainPageUrl);
	}

	@GetMapping("/sign-in/kakao")
	@Operation(summary = "카카오 로그인 메서드", description = "카카오 로그인을 하기 위한 메서드입니다.")
	public void kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(kakaoService.getAuthorizationUrl());
	}

	@GetMapping("/sign-in/kakao/callback")
	public void kakaoCallback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TokenDto token = kakaoService.getToken(code);
		OAuthInfoDto oauthInfoDto = kakaoService.getUserInfo(token);
		Member member = memberService.create(oauthInfoDto);

		userInfo.setUserId(member.getId());
		userInfo.setUserNm(member.getName());

		response.sendRedirect(mainPageUrl);
	}

	@GetMapping("/sign-in/google")
	@Operation(summary = "카카오 로그인 메서드", description = "카카오 로그인을 하기 위한 메서드입니다.")
	public void googleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(googleService.getAuthorizationUrl());
	}

	@GetMapping("/sign-in/google/callback")
	@Operation(summary = "구글 로그인 콜백 메서드", description = "구글 로그인을 성공하면 실행되는 메서드입니다.")
	public void googleCallback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TokenDto token = googleService.getToken(code);
		OAuthInfoDto oauthInfoDto = googleService.getUserInfo(token);
		Member member = memberService.create(oauthInfoDto);

		userInfo.setUserId(member.getId());
		userInfo.setUserNm(member.getName());

		response.sendRedirect(mainPageUrl);
	}

	@PostMapping("/sign-out")
	@Operation(summary = "로그아웃 메서드", description = "사용자가 로그아웃을 하기 위한 메서드입니다.")
	public ResponseEntity<String> signOut(HttpServletRequest request) {
		userInfo.setUserNm(null);
		userInfo.setUserId(null);
		return ResponseEntity.ok("로그아웃 되었습니다.");
	}

	@GetMapping
	@Operation(summary = "로그인 여부 확인 메서드", description = "사용자가 로그인 되어있는지 확인하는 메서드입니다.")
	public ResponseEntity<Long> isSignIn() {
		Long result = -1L;

		if (userInfo.getUserId() != null) {
			result = userInfo.getUserId();
		}
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/user-info/{userId}")
	@Operation(summary = "사용자 정보 저장 메서드", description = "사용자 정보를 저장하는 메서드입니다.")
	public ResponseEntity<UserInfoDto> update(@RequestBody UserInfoDto userInfoDto, @PathVariable Long userId) {
		return ResponseEntity.ok(memberService.update(userId, userInfoDto));
	}

	@GetMapping("/user-info/{userId}")
	@Operation(summary = "사용자 정보 조회 메서드", description = "사용자 정보를 조회하는 메서드입니다.")
	public ResponseEntity<Member> read(@PathVariable Long userId) {
		return ResponseEntity.ok(memberService.read(userId));
	}
}
