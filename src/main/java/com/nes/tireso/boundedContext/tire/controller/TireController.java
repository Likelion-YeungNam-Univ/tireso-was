package com.nes.tireso.boundedContext.tire.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.boundedContext.tire.entity.Tire;
import com.nes.tireso.boundedContext.tire.service.TireService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tire")
@RequiredArgsConstructor
public class TireController {
	private final TireService tireService;

	@GetMapping
	@Operation(summary = "타이어 목록 전체 조회 API", description = "타이어 전체 목록을 배열 형태로 반환합니다.")
	public ResponseEntity<List<Tire>> list() {
		return new ResponseEntity<>(tireService.list(), HttpStatus.OK);
	}
}
