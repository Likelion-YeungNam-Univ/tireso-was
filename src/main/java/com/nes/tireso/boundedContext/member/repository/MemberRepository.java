package com.nes.tireso.boundedContext.member.repository;


import com.nes.tireso.boundedContext.auth.enumType.AuthProvider;
import com.nes.tireso.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);

    boolean existsByIdAndAuthProvider(String id, AuthProvider authProvider);
}
