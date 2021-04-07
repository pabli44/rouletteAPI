package com.pvelilla.ruleta.api.ruletaAPI.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ruleta")
@Setter
@Getter
@NoArgsConstructor
public class Roulette {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rouletteId;

	@Column(name = "state")
	private String state;
	
	@Column(name = "money_bet")
	private double moneyBet;
}
