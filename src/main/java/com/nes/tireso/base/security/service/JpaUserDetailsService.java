package com.nes.tireso.base.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.auth.entity.CustomUserDetails;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username);
		if (member != null) {
			return new CustomUserDetails(member);
		} else {
			return (UserDetails)new UsernameNotFoundException("User not found with username: " + username);
		}

	}
}