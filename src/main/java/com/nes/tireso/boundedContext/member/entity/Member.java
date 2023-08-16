package com.nes.tireso.boundedContext.member.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.member.dto.CarInfoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
	private String providerTypeCode;
	@Column(unique = true)
	private String username;
	private String nickname;
	private String password;
	private String email;
	private String profileImage;
	private String carName;
	private String carNumber;
	private String carBrand;
	private String carModelName;
	private String oilType;

	@Builder
	public Member(String providerTypeCode, String username, String nickname, String password, String email, String profileImage, String carName,
			String carNumber, String carBrand, String carModelName, String oilType) {
		this.providerTypeCode = providerTypeCode;
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.profileImage = profileImage;
		this.carName = carName;
		this.carNumber = carNumber;
		this.carBrand = carBrand;
		this.carModelName = carModelName;
		this.oilType = oilType;
	}

	public List<? extends GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		grantedAuthorities.add(new SimpleGrantedAuthority("member"));

		if (isAdmin()) {
			grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
		}

		return grantedAuthorities;
	}

	public boolean isAdmin() {
		return "admin".equals(username);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCarInfo(CarInfoDto carInfo) {
		this.carName = carInfo.getCarName();
		this.carNumber = carInfo.getCarNumber();
		this.carBrand = carInfo.getCarBrand();
		this.carModelName = carInfo.getCarModelName();
		this.oilType = carInfo.getOilType();
	}
}
