package com.nes.tireso.base.rq;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.nes.tireso.boundedContext.member.entity.Member;
import com.nes.tireso.boundedContext.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequestScope
public class Rq {
	private final MemberService memberService;
	private final HttpServletRequest req;
	private final HttpServletResponse resp;
	private final User user;
	private Member member = null;

	public Rq(MemberService memberService,
		HttpServletRequest req, HttpServletResponse resp) {
		this.memberService = memberService;
		this.req = req;
		this.resp = resp;

		// 현재 로그인한 회원의 인증정보를 가져옴
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getPrincipal() instanceof User) {
			this.user = (User)authentication.getPrincipal();
		} else {
			this.user = null;
		}
	}

	// 로그인 되어 있는지 체크
	public boolean isLogin() {
		return user != null;
	}

	// 로그아웃 되어 있는지 체크
	public boolean isLogout() {
		return !isLogin();
	}

	// 로그인 된 회원의 객체
	public Member getMember() {
		if (isLogout())
			return null;

		// 데이터가 없는지 체크
		if (member == null) {
			member = memberService.findByUsername(user.getUsername()).orElse(null);
		}

		return member;
	}

	// 뒤로가기 + 메세지
	public String historyBack(String msg) {
		String referer = req.getHeader("referer");
		String key = "historyBackErrorMsg___" + referer;
		req.setAttribute("localStorageKeyAboutHistoryBackErrorMsg", key);
		req.setAttribute("historyBackErrorMsg", msg);
		// 200 이 아니라 400 으로 응답코드가 지정되도록
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return "common/js";
	}
}