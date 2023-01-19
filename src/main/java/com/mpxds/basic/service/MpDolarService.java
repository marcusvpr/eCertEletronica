package com.mpxds.basic.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpDolar;
import com.mpxds.basic.repository.MpDolarRepository;

@Service
public class MpDolarService {
	//
    @Autowired
    private MpDolarRepository mpDolarRepository;

    // ---

    public MpDolar guardar(MpDolar mpDolar) throws MpNegocioException {
    	//
		try {
			MpDolar mpObj = this.findByDataMovimento(mpDolar.getDataMovimento());
			
			if (mpObj != null && !mpObj.equals(mpDolar)) {
				throw new MpNegocioException("Já existe um DÓLAR com a Data informada.");
			}
			
	    	return this.mpDolarRepository.saveAndFlush(mpDolar);
	    	//
		} catch (OptimisticLockException e) {
			//
			throw new MpNegocioException("Erro de concorrência. Esse DÓLAR... já foi alterado anteriormente!");
		}
    }

    public void remover(MpDolar mpDolar)  {
    	//
    	this.mpDolarRepository.delete(mpDolar);
    }

    public List<MpDolar> findAllByDataMovimento() {
    	//
    	List<MpDolar> mpObjs = this.mpDolarRepository.findAllByOrderByDataMovimento();

    	return mpObjs;
    }
    
    public MpDolar findById(Long id) {
    	//
    	Optional<MpDolar> mpObj = this.mpDolarRepository.findById(id);

    	return mpObj.orElse(null);
    }

    public MpDolar findByDataMovimento(Date dataMovimento) {
    	//
    	Optional<MpDolar> mpObj = this.mpDolarRepository.findByDataMovimento(dataMovimento);

    	return mpObj.orElse(null);
    }

    public MpDolar findTopByDataMovimento() {
    	//
    	Optional<MpDolar> mpObj = this.mpDolarRepository.findTopByDataMovimento(new Date());

    	return mpObj.orElse(null);
    }

}
