package com.nes.tireso.boundedContext.member.service;

import com.nes.tireso.base.exception.BadRequestException;
import com.nes.tireso.boundedContext.auth.dto.SignUpRequest;
import com.nes.tireso.boundedContext.auth.enumType.Role;
import com.nes.tireso.boundedContext.member.entity.Member;
import com.nes.tireso.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(SignUpRequest signUpRequest) {
        if (memberRepository.existsByIdAndAuthProvider(signUpRequest.getId(), signUpRequest.getAuthProvider())) {
            throw new BadRequestException("aleady exist user");
        }

        return memberRepository.save(
                Member.builder()
                        .nickname(signUpRequest.getNickname())
                        .email(signUpRequest.getEmail())
                        .profileImageUrl(signUpRequest.getProfileImageUrl())
                        .role(Role.USER)
                        .authProvider(signUpRequest.getAuthProvider())
                        .build()).getId();
    }
}
