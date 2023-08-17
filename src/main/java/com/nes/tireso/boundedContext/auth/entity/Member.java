package com.nes.tireso.boundedContext.auth.entity;

import java.util.List;

import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.love.entity.Love;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
	private String carName;
	private String carNumber;
	private String carBrand;
	private String carModel;
	private String oilType;
	private String carType;
	private String oauthType;
	private String width;
	private String ratio;
	private String inch;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "love_id")
	private List<Love> love;

	@Builder
	public Member(String name, String email, String profileImageUrl, String username, String password, String carName,
			String carNumber, String carBrand, String carModel, String oilType, String carType, String oauthType,
			String width, String ratio, String inch) {
		this.name = name;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.username = username;
		this.password = password;
		this.carName = carName;
		this.carNumber = carNumber;
		this.carBrand = carBrand;
		this.carModel = carModel;
		this.oilType = oilType;
		this.carType = carType;
		this.oauthType = oauthType;
		this.width = width;
		this.ratio = ratio;
		this.inch = inch;

		if (profileImageUrl == null || profileImageUrl.isEmpty()) {
			this.profileImageUrl = "https://kr.object.ncloudstorage.com/tireso/member/default_profile.png";
		} else {
			this.profileImageUrl = profileImageUrl;
		}
	}

}