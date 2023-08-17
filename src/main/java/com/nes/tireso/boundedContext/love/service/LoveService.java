package com.nes.tireso.boundedContext.love.service;

import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.love.repository.LoveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoveService {
	private LoveRepository loveRepository;

	public boolean isLoved(Long userId, Long tireId) {
		// return loveRepository.existsByUserAndTire(userId, tireId);
		return true;
	}
}