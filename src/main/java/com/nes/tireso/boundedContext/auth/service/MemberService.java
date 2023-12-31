package com.nes.tireso.boundedContext.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.UserInfoDto;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member create(OAuthInfoDto oAuthInfoDto) {
		if (isDuplicateEmail(oAuthInfoDto.getEmail())) {
			return memberRepository.findByEmail(oAuthInfoDto.getEmail());
		}

		Member member = Member.builder()
				.username(oAuthInfoDto.getUsername())
				.name(oAuthInfoDto.getName())
				.email(oAuthInfoDto.getEmail())
				.profileImageUrl(oAuthInfoDto.getProfileImageUrl())
				.oauthType(oAuthInfoDto.getOauthType())
				.build();

		memberRepository.save(member);

		return member;
	}

	public boolean isDuplicateEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	public UserInfoDto update(Long memberId, UserInfoDto userInfoDto) {
		Member member = memberRepository.findById(memberId).get();

		Member.builder()
				.carName(userInfoDto.getCarName())
				.carNumber(userInfoDto.getCarNumber())
				.carBrand(userInfoDto.getCarBrand())
				.carModel(userInfoDto.getCarModel())
				.oilType(userInfoDto.getOilType())
				.width(userInfoDto.getWidth())
				.ratio(userInfoDto.getRatio())
				.inch(userInfoDto.getInch())
				.build();

		memberRepository.save(member);
		return userInfoDto;
	}

	public Member read(Long memberId) {
		return memberRepository.findById(memberId).get();
	}
}