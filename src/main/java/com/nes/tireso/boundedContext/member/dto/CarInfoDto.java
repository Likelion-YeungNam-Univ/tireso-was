package com.nes.tireso.boundedContext.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CarInfoDto {
	private String carName;
	private String carNumber;
	private String carBrand;
	private String carModelName;
	private String oilType;

	@Builder
	public CarInfoDto(String carName, String carNumber, String carBrand, String carModelName, String oilType) {
		this.carName = carName;
		this.carNumber = carNumber;
		this.carBrand = carBrand;
		this.carModelName = carModelName;
		this.oilType = oilType;
	}
}
