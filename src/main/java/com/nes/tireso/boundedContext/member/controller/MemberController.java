package com.nes.tireso.boundedContext.member.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nes.tireso.boundedContext.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usr/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	@Operation(summary = "OAuth 로그인 예시 API", description = "카카오, 구글, 네이버 OAuth 로그인 예시를 위한 API입니다.")
	public String showLogin() {
		return "usr/member/login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/me")
	public String showMe(Model model) {

		return "usr/member/me";
	}
}
