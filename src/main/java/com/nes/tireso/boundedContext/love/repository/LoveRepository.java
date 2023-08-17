package com.nes.tireso.boundedContext.love.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nes.tireso.boundedContext.love.entity.Love;

public interface LoveRepository extends JpaRepository<Love, Long> {
	// 특정 사용자가 찜한 타이어 목록 조회
	// List<Love> findByUser(Member member);
	//
	// // 특정 타이어를 찜한 사용자 목록 조회
	// List<Love> findByTire(Tire tire);
	//
	// // 특정 사용자가 특정 타이어를 찜했는지 여부 확인
	// boolean existsByUserAndTire(Member member, Tire tire);

}
