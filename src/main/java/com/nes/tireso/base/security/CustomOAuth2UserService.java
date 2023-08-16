package com.nes.tireso.base.security;

import com.nes.tireso.boundedContext.member.entity.Member;
import com.nes.tireso.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        String oauthId = switch (providerTypeCode) {
            case "NAVER" -> ((Map<String, String>) oAuth2User.getAttributes().get("response")).get("id");
            default -> oAuth2User.getName();
        };

        String username = providerTypeCode + "__%s".formatted(oauthId);

        String nickname, email, profileImage;

        switch (providerTypeCode) {
            case "NAVER" -> {
                Map<String, String> naverResponse = (Map<String, String>) oAuth2User.getAttributes().get("response");
                nickname = naverResponse.get("name");
                email = naverResponse.get("email");
                profileImage = naverResponse.get("profile_image");
            }
            case "KAKAO" -> {
                Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
                Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
                nickname = (String) properties.get("nickname");
                email = (String) kakaoAccount.get("email");
                profileImage = (String) kakaoAccount.get("profile_image");
            }
            case "GOOGLE" -> {
                nickname = (String) oAuth2User.getAttributes().get("name");
                email = (String) oAuth2User.getAttributes().get("email");
                profileImage = (String) oAuth2User.getAttributes().get("picture");
            }
            default -> {
                Map<String, String> defaultAttributes = (Map<String, String>) oAuth2User.getAttributes().get("profile");
                nickname = defaultAttributes.get("name");
                email = defaultAttributes.get("email");
                profileImage = defaultAttributes.get("profile_image");
            }
        }

        if (profileImage == null) {
            profileImage = "https://kr.object.ncloudstorage.com/tireso/member/default_profile.png";
        }

        Member member = memberService.whenSocialLogin(providerTypeCode, username, nickname, profileImage, email).getData();

        return new CustomOAuth2User(member.getUsername(), member.getPassword(), member.getGrantedAuthorities());
    }
}

class CustomOAuth2User extends User implements OAuth2User {

    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return getUsername();
    }
}