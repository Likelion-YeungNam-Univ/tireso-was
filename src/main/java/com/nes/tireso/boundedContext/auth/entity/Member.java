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
	private String nickname;

	@Column(nullable = false)
	private String email;

	@Column
	private String profileImageUrl;

	private String username;
	private String password;

	private String carType;
	private String type;
	private String width;
	private String ratio;

	@Builder
	public Member(String nickname, String email, String profileImageUrl, String username, String password, String carType, String type, String width,
			String ratio) {
		this.nickname = nickname;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.username = username;
		this.password = password;
		this.carType = carType;
		this.type = type;
		this.width = width;
		this.ratio = ratio;

		if (profileImageUrl == null || profileImageUrl.isEmpty()) {
			this.profileImageUrl = "https://kr.object.ncloudstorage.com/tireso/member/default_profile.png";
		} else {
			this.profileImageUrl = profileImageUrl;
		}
	}
}