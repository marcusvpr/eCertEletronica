package com.mpxds.basic.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpCertidaoLog;
import com.mpxds.basic.repository.MpCertidaoLogRepository;

@Service
public class MpCertidaoLogService {
	//
    @Autowired
    private MpCertidaoLogRepository mpCertidaoLogRepository;

    // ---

    public MpCertidaoLog guardar(MpCertidaoLog mpCertidaoLog) throws MpNegocioException {
    	//
		try {			
	    	return this.mpCertidaoLogRepository.saveAndFlush(mpCertidaoLog);
	    	//
		} catch (OptimisticLockException e) {
			//
			throw new MpNegocioException("Erro de concorrência. Essa CertidãoLog... já foi alterado anteriormente!");
		}
    }

    public void remover(MpCertidaoLog mpCertidaoLog)  {
    	//
    	this.mpCertidaoLogRepository.delete(mpCertidaoLog);
    }

    public List<MpCertidaoLog> findAllByDataMovimento() {
    	//
    	List<MpCertidaoLog> mpObjs = this.mpCertidaoLogRepository.findAllByOrderByDataMovimento();

    	return mpObjs;
    }
    
    public MpCertidaoLog findByDataMovimento(String dataMovimento) {
    	//
    	Optional<MpCertidaoLog> mpObj = this.mpCertidaoLogRepository.findByDataMovimento(dataMovimento);

    	return mpObj.orElse(null);
    }
       
    public MpCertidaoLog findById(Long id) {
    	//
    	Optional<MpCertidaoLog> mpObj = this.mpCertidaoLogRepository.findById(id);

    	return mpObj.orElse(null);
    }

}
