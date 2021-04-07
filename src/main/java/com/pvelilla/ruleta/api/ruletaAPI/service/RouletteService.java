package com.pvelilla.ruleta.api.ruletaAPI.service;

import java.util.List;
import java.util.Optional;

import com.pvelilla.ruleta.api.ruletaAPI.domain.RouletteDTO;

public interface RouletteService {
	
	List<RouletteDTO> findAll(Optional<Long> stateParam);
	
	RouletteDTO findById(Long rouletteId);
	
	Long save(RouletteDTO rouletteDTO);

	RouletteDTO update(Long rouletteId, RouletteDTO rouletteDTO);

	RouletteDTO deleteById(Long rouletteId);

	String getOpeningState(Long rouletteId);

	Object bet(String color, int number, double moneyBet, int userId);

}
