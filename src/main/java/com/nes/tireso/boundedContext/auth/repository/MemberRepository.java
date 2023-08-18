package com.nes.tireso.boundedContext.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nes.tireso.boundedContext.auth.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUsername(String username);

	Member findByEmail(String email);
}
