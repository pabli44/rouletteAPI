package com.pvelilla.ruleta.api.ruletaAPI.domain;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouletteDTO {
	
	private Long rouletteId;

	@NotBlank
	private String state;
	
	@NotNull
	private double moneyBet;

}
