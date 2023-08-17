package com.nes.tireso.boundedContext.love.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.love.entity.Love;
import com.nes.tireso.boundedContext.tire.entity.Tire;

public interface LoveRepository extends JpaRepository<Love, Long> {
	Love findByMemberAndTire(Member member, Tire tire);
}
