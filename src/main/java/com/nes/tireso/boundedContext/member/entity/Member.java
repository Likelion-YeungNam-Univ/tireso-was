package com.nes.tireso.boundedContext.member.entity;


import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.auth.enumType.AuthProvider;
import com.nes.tireso.boundedContext.auth.enumType.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    private String carType;
    private String type;
    private String width;
    private String ratio;

    @Builder
    public Member(
            String nickname
            , String email
            , String profileImageUrl
            , Role role
            , AuthProvider authProvider
            , String carType
            , String type
            , String width
            , String ratio
    ) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.authProvider = authProvider;
        this.carType = carType;
        this.type = type;
        this.width = width;
        this.ratio = ratio;

        if (profileImageUrl == null || profileImageUrl.isEmpty()) {
            this.profileImageUrl = "https://kr.object.ncloudstorage.com/tireso/member/default_profile.png";
        } else {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public Member update(String name, String picture) {
        this.nickname = name;
        this.profileImageUrl = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
