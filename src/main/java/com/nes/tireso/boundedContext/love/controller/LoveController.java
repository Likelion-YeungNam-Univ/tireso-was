package com.nes.tireso.boundedContext.love.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.boundedContext.love.service.LoveService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/love")
@RequiredArgsConstructor
@Tag(name = "Love", description = "좋아요 관련 API")
public class LoveController {
	private LoveService loveService;

	// @GetMapping
	// public ResponseEntity<Boolean> isLoved(HttpServletRequest request, @RequestParam Long tireId) {
	// 	Long memberId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
	// 	boolean isLoved = loveService.isLoved(memberId, tireId);
	// 	return ResponseEntity.ok(isLoved);
	// }

	// @PostMapping
	// public ResponseEntity<Boolean> isLoved(HttpServletRequest request, @RequestParam Long tireId) {
	// 	Long memberId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
	// 	boolean isLoved = loveService.isLoved(memberId, tireId);
	//
	// }
}
