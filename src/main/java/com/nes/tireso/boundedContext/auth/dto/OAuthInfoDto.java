package com.nes.tireso.boundedContext.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OAuthInfoDto {
	private String providerTypeCode;
	private String username;
	private String name;

	@Builder
	public OAuthInfoDto(String providerTypeCode, String username, String name) {
		this.providerTypeCode = providerTypeCode;
		this.username = username;
		this.name = name;
	}
}
