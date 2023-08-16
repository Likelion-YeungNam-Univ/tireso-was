package com.nes.tireso.boundedContext.home.controller;

import java.nio.charset.Charset;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.base.rq.Rq;

@RestController
@RequiredArgsConstructor
public class HomeController {
	// private final Rq rq;
	//
	// @GetMapping("/")
	// public String showMain() {
	// 	if (rq.isLogout()) return "redirect:/usr/member/login";
	//
	// 	return "redirect:/usr/member/me";
	// }

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return new ResponseEntity<String>("hello", HttpStatus.OK);
	}
}
