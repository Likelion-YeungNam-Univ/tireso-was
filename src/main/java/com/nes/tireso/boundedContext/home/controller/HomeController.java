package com.nes.tireso.boundedContext.home.controller;

import com.nes.tireso.base.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final Rq rq;

	@GetMapping("/")
	public String showMain() {
		if (rq.isLogout()) return "redirect:/usr/member/login";

		return "redirect:/usr/member/me";
	}
}
