package com.mpxds.basic.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpDolar;

public interface MpDolarRepository extends JpaRepository<MpDolar, Long> {
	//
	Optional<MpDolar> findByDataMovimento(Date dataMovimento);
	
	Optional<MpDolar> findTopByDataMovimento(Date dataMovimento);
	
	@Transactional(readOnly=true)
	public List<MpDolar> findAllByOrderByDataMovimento();
    
}
