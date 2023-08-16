package com.nes.tireso.boundedContext.tire.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.tire.entity.Tire;
import com.nes.tireso.boundedContext.tire.repository.TireRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TireService {
	private final TireRepository tireRepository;

	public List<Tire> list(int sort) {
		// 정렬: 등록순(0), 인기순(1), 낮은가격순(2), 높은가격순(3)
		if (sort == 0 || sort > 3) {
			return tireRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		} else if (sort == 1) {
			return tireRepository.findAll(Sort.by(Sort.Direction.DESC, "rate"));
		} else if (sort == 2) {
			return tireRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
		} else {
			return tireRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		}
	}
}
