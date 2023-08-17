package com.nes.tireso.boundedContext.love.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.boundedContext.love.service.LoveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoveController {
	private LoveService loveService;

	@GetMapping("/isLoved")
	public ResponseEntity<Boolean> isLoved(@RequestParam Long userId, @RequestParam Long tireId) {
		boolean isLoved = loveService.isLoved(userId, tireId);
		return ResponseEntity.ok(isLoved);
	}
}
