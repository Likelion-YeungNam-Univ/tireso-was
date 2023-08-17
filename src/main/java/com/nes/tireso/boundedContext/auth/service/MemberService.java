package com.nes.tireso.boundedContext.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member create(OAuthInfoDto oAuthInfoDto) {
		if (isDuplicateUsername(oAuthInfoDto.getUsername())) {
			return memberRepository.findByUsername(oAuthInfoDto.getUsername());
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

	public boolean isDuplicateUsername(String username) {
		if (memberRepository.findByUsername(username) == null) {
			return false;
		}
		return true;
	}
}