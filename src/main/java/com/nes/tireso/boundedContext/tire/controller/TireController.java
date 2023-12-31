package com.nes.tireso.boundedContext.tire.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nes.tireso.boundedContext.tire.entity.Tire;
import com.nes.tireso.boundedContext.tire.service.TireService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tire")
@RequiredArgsConstructor
@Tag(name = "Tire", description = "Tire 조회 및 검색 관련 API")
public class TireController {
	private final TireService tireService;

	@GetMapping
	@Operation(summary = "타이어 목록 전체 조회 API", description = "타이어 전체 목록을 배열 형태로 반환합니다.")
	public ResponseEntity<List<Tire>> list(
			@RequestParam(defaultValue = "0") int sort,
			@RequestParam(name = "brand", defaultValue = "전체") String brand,
			@RequestParam(name = "car_type", defaultValue = "전체") String carType,
			@RequestParam(defaultValue = "전체") String season,
			@RequestParam(defaultValue = "전체") String type
	) {

		// 정렬: 등록순, 인기순, 낮은가격순, 높은가격순
		return new ResponseEntity<>(tireService.list(sort, brand, carType, season, type), HttpStatus.OK);
	}

	@GetMapping("/{tireId}")
	@Operation(summary = "타이어 상세 조회 API", description = "타이어 상세 정보를 반환합니다.")
	public ResponseEntity<Tire> read(@PathVariable String tireId) {
		return new ResponseEntity<>(tireService.read(tireId), HttpStatus.OK);
	}
}
