package com.nes.tireso.boundedContext.tire.entity;

import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.brand.entity.Brand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Tire extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;
	private String name;
	private String description;
	private String imageUrl1;
	private String imageUrl2;
	private String imageUrl3;
	private String carType;
	private String season;
	private String type;
	private String width;
	private String ratio;
	private String diameter;
	private Float rate;
	private int price;
	private String tag;
	private int review_cnt;
}
