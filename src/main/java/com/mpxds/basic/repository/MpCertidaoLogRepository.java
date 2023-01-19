package com.mpxds.basic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpCertidaoLog;

public interface MpCertidaoLogRepository extends JpaRepository<MpCertidaoLog, Long> {
	//
	Optional<MpCertidaoLog> findByDataMovimento(String dataMovimento);
	
	@Transactional(readOnly=true)
	public List<MpCertidaoLog> findAllByOrderByDataMovimento();
    
}
