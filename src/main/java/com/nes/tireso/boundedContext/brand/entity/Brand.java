package com.nes.tireso.boundedContext.brand.entity;

import com.nes.tireso.base.baseEntity.BaseEntity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Brand extends BaseEntity {
	private String name;
}
