package com.nes.tireso.boundedContext.auth.entity;

import com.nes.tireso.base.baseEntity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column
	private String profileImageUrl;

	private String username;
	private String password;

	private String carType;
	private String oauthType;
	private String width;
	private String ratio;

	@Builder
	public Member(String name, String email, String profileImageUrl, String username, String password, String carType, String oauthType, String width,
			String ratio) {
		this.name = name;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.username = username;
		this.password = password;
		this.carType = carType;
		this.oauthType = oauthType;
		this.width = width;
		this.ratio = ratio;

		if (profileImageUrl == null || profileImageUrl.isEmpty()) {
			this.profileImageUrl = "https://kr.object.ncloudstorage.com/tireso/member/default_profile.png";
		} else {
			this.profileImageUrl = profileImageUrl;
		}
	}
}