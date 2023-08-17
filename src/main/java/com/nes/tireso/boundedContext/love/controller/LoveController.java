package com.nes.tireso.boundedContext.love.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.boundedContext.auth.entity.UserInfo;
import com.nes.tireso.boundedContext.love.service.LoveService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/love")
@RequiredArgsConstructor
@Tag(name = "Love", description = "좋아요 관련 API")
public class LoveController {
	private final LoveService loveService;

	@Resource
	private UserInfo userInfo;

	@PostMapping
	public ResponseEntity<Boolean> setLove(HttpServletRequest request, @RequestBody Long tireId) {
		System.out.println("userId: " + userInfo.getUserId());
		System.out.println("tireId: " + tireId);
		return ResponseEntity.ok(loveService.save(userInfo.getUserId(), tireId));
	}

	// @GetMapping
	// public ResponseEntity<Boolean> setLove(HttpServletRequest request, @RequestParam("tire_id") Long tireId) {
	// 	System.out.println("userId: " + userInfo.getUserId());
	// 	System.out.println("tireId: " + tireId);
	// 	return ResponseEntity.ok(loveService.save(userInfo.getUserId(), tireId));
	// }
}
