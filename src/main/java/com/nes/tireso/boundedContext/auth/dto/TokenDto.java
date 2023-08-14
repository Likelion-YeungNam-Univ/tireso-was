package com.nes.tireso.boundedContext.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TokenDto {
	private String accessToken;
	private String refreshToken;

	@Builder
	public TokenDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
