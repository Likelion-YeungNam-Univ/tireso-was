package com.nes.tireso.boundedContext.tire.entity;

import com.nes.tireso.base.baseEntity.BaseEntity;
import com.nes.tireso.boundedContext.brand.entity.Brand;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Tire extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;
	private String name1;
	private String name2;
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

	@Builder
	public Tire(Brand brand, String name1, String name2, String description, String imageUrl1, String imageUrl2,
			String imageUrl3, String carType, String season, String type, String width, String ratio, String diameter,
			Float rate, int price, String tag, int review_cnt) {
		this.brand = brand;
		this.name1 = name1;
		this.name2 = name2;
		this.description = description;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
		this.carType = carType;
		this.season = season;
		this.type = type;
		this.width = width;
		this.ratio = ratio;
		this.diameter = diameter;
		this.rate = rate;
		this.price = price;
		this.tag = tag;
		this.review_cnt = review_cnt;
	}
}
