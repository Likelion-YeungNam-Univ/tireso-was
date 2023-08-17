package com.nes.tireso.boundedContext.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OAuthInfoDto {
	private String username;
	private String email;
	private String name;
	private String profileImageUrl;
	private String oauthType;

	@Builder
	public OAuthInfoDto(String username, String email, String name, String profileImageUrl, String oauthType) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.profileImageUrl = profileImageUrl;
		this.oauthType = oauthType;
	}
}
