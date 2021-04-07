package com.pvelilla.ruleta.api.ruletaAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.pvelilla.ruleta.api.ruletaAPI.entities.Roulette;

@Repository
public interface RouletteRepository extends JpaRepository<Roulette, Long>, JpaSpecificationExecutor<Roulette>{

}
