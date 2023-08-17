package com.nes.tireso.boundedContext.auth.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.base.jwt.JwtProvider;
import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.service.AuthService;
import com.nes.tireso.boundedContext.auth.service.GoogleService;
import com.nes.tireso.boundedContext.auth.service.KakaoService;
import com.nes.tireso.boundedContext.auth.service.MemberService;
import com.nes.tireso.boundedContext.auth.service.NaverService;
import com.nes.tireso.boundedContext.auth.service.RefreshTokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "사용자 인증 관련 API")
public class AuthController {
	private final MemberService memberService;
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;
	private final NaverService naverService;
	private final KakaoService kakaoService;
	private final GoogleService googleService;

	@PostMapping("/refresh")
	@Operation(summary = "Refresh Token을 이용한 New RefreshToken, New Access Token 발급 메서드", description = "Refresh Token을 이용하여 새로운 Refresh Token, Access Token을 발급 받기 위한 메서드입니다.")
	public ResponseEntity<TokenDto> reissueAccessToken(@RequestHeader("Authorization") String refreshToken) throws Exception {
		String newAccessToken = refreshTokenService.createNewAccessTokenByValidateRefreshToken(refreshToken);
		String newRefreshToken = refreshTokenService.createNewRefreshTokenByValidateRefreshToken(refreshToken);

		TokenDto tokenDto = TokenDto.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();

		refreshToken = refreshToken.substring(7);
		refreshTokenService.storeRefreshToken(tokenDto, jwtProvider.getUsername(refreshToken));

		return ResponseEntity.ok(tokenDto);
	}

	// @GetMapping("/sign-in/naver")
	// @Operation(summary = "네이버 로그인 메서드", description = "네이버 로그인을 하기 위한 메서드입니다.")
	// public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// 	response.sendRedirect(naverService.getAuthorizationUrl());
	// }

	// @GetMapping("/sign-in/naver")
	// @Operation(summary = "네이버 로그인 메서드", description = "네이버 로그인을 하기 위한 메서드입니다.")
	// public ResponseEntity<String> naverLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// 	return ResponseEntity.ok(naverService.getAuthorizationUrl());
	// }

	@GetMapping("/sign-in/naver/callback")
	@Operation(summary = "네이버 로그인 콜백 메서드", description = "네이버 로그인을 성공하면 실행되는 메서드입니다.")
	public ResponseEntity<TokenDto> naverCallback(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws IOException {
		TokenDto token = naverService.getNaverToken("token", code);
		OAuthInfoDto oauthInfoDto = naverService.getUserInfo(token);
		Member member = memberService.create(oauthInfoDto);
		TokenDto tokenDto = authService.issueToken(member);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("http://localhost:3000/main"));
		return new ResponseEntity<>(tokenDto, headers, HttpStatus.MOVED_PERMANENTLY);
	}

	// @GetMapping("/sign-in/kakao")
	// @Operation(summary = "카카오 로그인 메서드", description = "카카오 로그인을 하기 위한 메서드입니다.")
	// public void kakaoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// 	response.sendRedirect(kakaoService.getAuthorizationUrl());
	// }

	// @GetMapping("/sign-in/kakao/callback")
	// public ResponseEntity<TokenDto> kakaoCallback(@RequestParam String code) throws Exception {
	// 	TokenDto token = kakaoService.getToken(code);
	// 	OAuthInfoDto oauthInfoDto = kakaoService.getUserInfo(token);
	// 	Member member = memberService.create(oauthInfoDto);
	// 	TokenDto tokenDto = authService.issueToken(member);
	// 	return ResponseEntity.ok(tokenDto);
	// }

	// @GetMapping("/sign-in/google")
	// @Operation(summary = "구글 로그인 콜백 메서드", description = "구글 로그인 후 리다이렉트 되어 인가 코드를 출력하는 메서드입니다.")
	// public void googleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// 	response.sendRedirect(googleService.getAuthorizationUrl());
	// }

	// @GetMapping("/sign-in/google/callback")
	// public ResponseEntity<TokenDto> signIn(@RequestParam String code) throws Exception {
	// 	TokenDto token = googleService.getToken(code);
	// 	OAuthInfoDto oauthInfoDto = googleService.getUserInfo(token);
	// 	Member member = memberService.create(oauthInfoDto);
	// 	TokenDto tokenDto = authService.issueToken(member);
	// 	return ResponseEntity.ok(tokenDto);
	// }

	// @PostMapping("/sign-out")
	// @Operation(summary = "로그아웃 메서드", description = "사용자가 로그아웃을 하기 위한 메서드입니다.")
	// public ResponseEntity<> signOut(HttpServletRequest request) {
	// 	String username = jwtProvider.getUsername(jwtProvider.resolveToken(request).substring(7));
	// 	authService.signOut(username);
	// 	return new ResponseEntity<Message>(
	// 			Message.builder()
	// 					.status(StatusEnum.OK)
	// 					.message("로그아웃 성공!")
	// 					.data(null)
	// 					.build(),
	// 			new HttpJsonHeaders(),
	// 			HttpStatus.OK
	// 	);
	// }
}
