package com.nes.tireso.boundedContext.love.entity;

import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.tire.entity.Tire;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Love extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "tire_id")
	private Tire tire;

	@Builder
	public Love(Member member, Tire tire) {
		this.member = member;
		this.tire = tire;
	}
}
