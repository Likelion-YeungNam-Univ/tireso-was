package com.nes.tireso.boundedContext.tire.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.tire.entity.Tire;
import com.nes.tireso.boundedContext.tire.repository.TireRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TireService {
	private final TireRepository tireRepository;

	public List<Tire> list() {
		return tireRepository.findAll();
	}
}
