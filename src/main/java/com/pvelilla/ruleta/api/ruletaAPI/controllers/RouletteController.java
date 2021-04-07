package com.pvelilla.ruleta.api.ruletaAPI.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pvelilla.ruleta.api.ruletaAPI.domain.ResponseMapDTO;
import com.pvelilla.ruleta.api.ruletaAPI.domain.RouletteDTO;
import com.pvelilla.ruleta.api.ruletaAPI.service.RouletteService;

@RestController
@RequestMapping("/api/v1/roulettes")
public class RouletteController {
	
private RouletteService rouletteService;
	
	@Autowired
	public RouletteController(final RouletteService rouletteService) {
		this.rouletteService = rouletteService;
	}
	
	@CrossOrigin
	@PostMapping
	public ResponseMapDTO save(@RequestBody @Valid RouletteDTO rouletteDTO) {
		return new ResponseMapDTO("recordId", rouletteService.save(rouletteDTO));
	}
	
	@CrossOrigin
	@DeleteMapping(value = "/{rouletteId}")
	public RouletteDTO deleteById(@PathVariable Long rouletteId) {
		return rouletteService.deleteById(rouletteId);
	}

	@CrossOrigin
	@PutMapping(value = "/{rouletteId}")
	public RouletteDTO updateById(@PathVariable Long rouletteId, @RequestBody @Valid RouletteDTO rouletteDTO) {
		return rouletteService.update(rouletteId, rouletteDTO);
	}

	@CrossOrigin
	@GetMapping(value = "/{rouletteId}")
	public RouletteDTO getRouletteById(@PathVariable Long rouletteId) {
		return rouletteService.findById(rouletteId);
	}
	
	@CrossOrigin
	@GetMapping
	public List<RouletteDTO> findALlRecords(@RequestParam(name = "stateParam") Optional<Long> stateParam) {
		return rouletteService.findAll(stateParam);
	}
	
	@CrossOrigin
	@GetMapping(value = "/opening/{rouletteId}")
	public String getOpeningState(@PathVariable Long rouletteId) {
		return rouletteService.getOpeningState(rouletteId);
	}
	
	
	public void bet(String color, int number, double moneyBet, int userId) {
		if(number>0 && number<=36) {
			
		}else if(color!=null) {
			
		}
		
		rouletteService.bet(color, number, moneyBet, userId);
	}
	

}
