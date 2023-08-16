package com.nes.tireso.boundedContext.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nes.tireso.base.rq.Rq;
import com.nes.tireso.boundedContext.member.dto.CarInfoDto;
import com.nes.tireso.boundedContext.member.entity.Member;
import com.nes.tireso.boundedContext.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usr/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final Rq rq;

	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	public String showLogin() {
		return "usr/member/login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/main")
	public String showMe(Model model) {
		return "usr/member/me";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/mypage")
	public ResponseEntity<String> saveCarInfo(CarInfoDto carInfoDto) {
		memberService.saveCarInfo(rq.getMember().getUsername(), carInfoDto);
		return ResponseEntity.ok("성공적으로 저장되었습니다!");
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mypage")
	public ResponseEntity<Member> readMyInfo() {
		return ResponseEntity.ok(rq.getMember());
	}
}
