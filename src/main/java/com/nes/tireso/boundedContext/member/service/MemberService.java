package com.nes.tireso.boundedContext.member.service;

import com.nes.tireso.base.rsData.RsData;
import com.nes.tireso.boundedContext.member.entity.Member;
import com.nes.tireso.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    private RsData<Member> join(String providerTypeCode, String username, String password, String nickname, String profileImage, String email) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("F-1", "해당 아이디(%s)는 이미 사용중입니다.".formatted(username));
        }

        if (StringUtils.hasText(password)) {
            password = passwordEncoder.encode(password);
        }

        Member member = Member.builder()
                .providerTypeCode(providerTypeCode)
                .username(username)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .build();

        memberRepository.save(member);

        return RsData.of("S-1", "회원가입이 완료되었습니다.", member);
    }

    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username, String nickname, String profileImage, String email) {
        Optional<Member> opMember = findByUsername(username);

        return opMember.map(member -> {
            member.setNickname(nickname);
            member.setProfileImage(profileImage);
            member.setEmail(email);
            return RsData.of("S-2", "로그인 되었습니다.", member);
        }).orElseGet(() -> join(providerTypeCode, username, "", nickname, profileImage, email));
    }
}
