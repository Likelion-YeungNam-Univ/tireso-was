package com.nes.tireso.boundedContext.member.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nes.tireso.boundedContext.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usr/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	public String showLogin() {
		return "usr/member/login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/me")
	public String showMe(Model model) {

		return "usr/member/me";
	}
}
