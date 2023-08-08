package com.nes.tireso.boundedContext.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nes.tireso.boundedContext.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
}
