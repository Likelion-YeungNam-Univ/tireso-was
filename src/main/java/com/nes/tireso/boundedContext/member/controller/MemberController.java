package com.nes.tireso.boundedContext.member.controller;

import com.nes.tireso.boundedContext.auth.dto.SignUpRequest;
import com.nes.tireso.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(memberService.createMember(signUpRequest));
    }
}