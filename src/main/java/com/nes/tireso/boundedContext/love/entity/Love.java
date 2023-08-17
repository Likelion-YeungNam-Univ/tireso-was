package com.nes.tireso.boundedContext.love.entity;

import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.auth.entity.Member;
import com.nes.tireso.boundedContext.tire.entity.Tire;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Love extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "tire_id")
	private Tire tire;
}
