package com.pvelilla.ruleta.api.ruletaAPI.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pvelilla.ruleta.api.ruletaAPI.config.dozer.DozerMappingBuilder;
import com.pvelilla.ruleta.api.ruletaAPI.config.exceptions.RecordNotFoundException;
import com.pvelilla.ruleta.api.ruletaAPI.config.specification.SpecificationBuilder;
import com.pvelilla.ruleta.api.ruletaAPI.domain.RouletteDTO;
import com.pvelilla.ruleta.api.ruletaAPI.entities.Roulette;
import com.pvelilla.ruleta.api.ruletaAPI.repository.RouletteRepository;
import com.pvelilla.ruleta.api.ruletaAPI.service.RouletteService;

@Service
public class RouletteServiceImpl implements RouletteService{
	
	private RouletteRepository rouletteRepository;
	private static final String NAME_DOMAIN = "Roulette";
	
	
	public RouletteServiceImpl(final RouletteRepository rouletteRepository) {
		this.rouletteRepository = rouletteRepository;
	}
	
	
	@Override
	public List<RouletteDTO> findAll(Optional<Long> stateParam) {
		Map<String, Object> paramSpec = new HashMap<>();
		stateParam.ifPresent(mapper -> paramSpec.put("stateParam", stateParam.get()));
		return rouletteRepository
				.findAll(new SpecificationBuilder<Roulette>(paramSpec).conjunctionEquals("[state]", "stateParam").build())
				.stream().map(mapper -> new DozerMappingBuilder().map(mapper, RouletteDTO.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public RouletteDTO findById(Long rouletteId) {
		return rouletteRepository.findById(rouletteId)
				.map(mapper -> new DozerMappingBuilder().map(mapper, RouletteDTO.class))
				.orElseThrow(() -> new RecordNotFoundException(NAME_DOMAIN, rouletteId));
	}

	@Override
	public Long save(RouletteDTO rouletteDTO) {
		Roulette roulette = new DozerMappingBuilder().map(rouletteDTO, Roulette.class);
		rouletteRepository.save(roulette);
		return roulette.getRouletteId();
	}

	@Override
	public RouletteDTO update(Long rouletteId, RouletteDTO rouletteDTO) {
		return rouletteRepository.findById(rouletteId).map(mapper -> {
			Roulette roulette= new DozerMappingBuilder().map(rouletteId, Roulette.class);
			roulette.setRouletteId(rouletteId);
			rouletteRepository.save(roulette);
			return new DozerMappingBuilder().map(roulette, RouletteDTO.class);
		}).orElseThrow(() -> new RecordNotFoundException(NAME_DOMAIN, rouletteId));
	}

	@Override
	public RouletteDTO deleteById(Long rouletteId) {
		return rouletteRepository.findById(rouletteId).map(mapper -> {
			rouletteRepository.delete(mapper);
			return new DozerMappingBuilder().map(mapper, RouletteDTO.class);
		}).orElseThrow(() -> new RecordNotFoundException(NAME_DOMAIN, rouletteId));
	}

	@Override
	public String getOpeningState(Long rouletteId) {
		Roulette roulette = rouletteRepository.getOne(rouletteId);
		roulette.setState("Success");
		
		return roulette.getState();
	}


	@Override
	public Object bet(String color, int number, double moneyBet, int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
