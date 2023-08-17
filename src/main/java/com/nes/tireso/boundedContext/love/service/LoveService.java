package com.nes.tireso.boundedContext.love.service;

import org.springframework.stereotype.Service;

import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;
import com.nes.tireso.boundedContext.love.entity.Love;
import com.nes.tireso.boundedContext.love.repository.LoveRepository;
import com.nes.tireso.boundedContext.tire.entity.Tire;
import com.nes.tireso.boundedContext.tire.repository.TireRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoveService {
	private final LoveRepository loveRepository;
	private final MemberRepository memberRepository;
	private final TireRepository tireRepository;

	// public boolean isLoved(Long memberId, Long tireId) {
	// 	return loveRepository.existsByMemberAndTire(memberId, tireId);
	// }

	public boolean save(Long memberId, Long tireId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		Tire tire = tireRepository.findById(tireId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타이어입니다."));

		Love love = loveRepository.findByMemberAndTire(member, tire);

		if (love == null) {
			loveRepository.save(Love.builder()
					.member(member)
					.tire(tire)
					.build());
			return true;
		} else {
			loveRepository.deleteById(love.getId());
			return false;
		}
	}
}