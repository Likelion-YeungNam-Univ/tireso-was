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

	public List<Tire> list(int sort, String brandId, String carType, String season, String type) {
		List<Tire> tireList = null;

		// 정렬: 등록순(0), 인기순(1), 낮은가격순(2), 높은가격순(3)
		if (sort == 0 || sort > 3) {
			tireList = tireRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		} else if (sort == 1) {
			tireList = tireRepository.findAll(Sort.by(Sort.Direction.DESC, "rate"));
		} else if (sort == 2) {
			tireList = tireRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
		} else {
			tireList = tireRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
		}

		if (!brandId.equals("전체")) {
			tireList = tireList.stream().filter(tire -> tire.getBrand().getId().equals(brandId)).toList();
		}

		if (!carType.equals("전체")) {
			tireList = tireList.stream().filter(tire -> tire.getCarType().equals(carType)).toList();
		}

		if (!season.equals("전체")) {
			tireList = tireList.stream().filter(tire -> tire.getSeason().equals(season)).toList();
		}

		if (!type.equals("전체")) {
			tireList = tireList.stream().filter(tire -> tire.getType().equals(type)).toList();
		}

		return tireList;
	}
}
