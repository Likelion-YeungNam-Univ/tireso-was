package com.nes.tireso.boundedContext.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nes.tireso.base.jwt.JwtProvider;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.entity.RefreshToken;
import com.nes.tireso.boundedContext.auth.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
	private final JwtProvider jwtProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public void storeRefreshToken(TokenDto tokenDto, String username) {
		RefreshToken refreshToken = RefreshToken.builder()
				.token(tokenDto.getRefreshToken())
				.username(username)
				.createdAt(jwtProvider.getIssuedAt(tokenDto.getRefreshToken()))
				.build();

		if (refreshTokenRepository.findByUsername(username).isPresent()) {
			refreshTokenRepository.deleteByUsername(username);
		}
		refreshTokenRepository.save(refreshToken);
	}

	public String createNewAccessTokenByValidateRefreshToken(String refreshToken) throws Exception {
		if (jwtProvider.validateToken(refreshToken)) {
			refreshToken = refreshToken.substring(7);
			return jwtProvider.reissueAccessToken(refreshToken);
		}
		return null;
	}

	public String createNewRefreshTokenByValidateRefreshToken(String refreshToken) {
		if (jwtProvider.validateToken(refreshToken)) {
			refreshToken = refreshToken.substring(7);
			return jwtProvider.generateRefreshToken(jwtProvider.getUsername(refreshToken), jwtProvider.getUserId(refreshToken));
		}
		return null;
	}

	public void deleteRefreshToken(String username) throws Exception {
		Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(username);
		if (refreshToken.isPresent()) {
			refreshTokenRepository.delete(refreshToken.get());
		} else {
			throw new Exception("존재하지 않는 사용자 입니다.");
		}
	}
}
