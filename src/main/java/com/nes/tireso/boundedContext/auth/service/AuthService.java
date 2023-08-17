package com.nes.tireso.boundedContext.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nes.tireso.base.jwt.JwtProvider;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	private final NaverService naverService;

	public TokenDto issueToken(Member member) {
		String accessToken = jwtProvider.generateToken(member.getUsername(), member.getId());
		String refreshToken = jwtProvider.generateRefreshToken(member.getUsername(), member.getId());

		TokenDto tokenDto = TokenDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();

		refreshTokenService.storeRefreshToken(tokenDto, member.getUsername());

		return TokenDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
}
