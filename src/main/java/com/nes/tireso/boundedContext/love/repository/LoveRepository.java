package com.nes.tireso.boundedContext.love.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nes.tireso.boundedContext.love.entity.Love;

public interface LoveRepository extends JpaRepository<Love, Long> {
	// boolean existsByMemberAndTire(Long memberId, Long tireId);
}
