package com.mpxds.basic.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpSistemaConfig;
import com.mpxds.basic.repository.MpSistemaConfigRepository;

@Service
public class MpSistemaConfigService {
	//
    @Autowired
    private MpSistemaConfigRepository mpSistemaConfigRepository;

    // ---

    public MpSistemaConfig guardar(MpSistemaConfig mpSistemaConfig) throws MpNegocioException {
    	//
		try {
			MpSistemaConfig mpObj = this.findByParametro(mpSistemaConfig.getParametro());
			
			if (mpObj != null && !mpObj.equals(mpSistemaConfig)) {
				throw new MpNegocioException("Já existe um SISTEMA CONFIGURAÇÃO com o Parâmetro informado.");
			}
			
	    	return this.mpSistemaConfigRepository.saveAndFlush(mpSistemaConfig);
	    	//
		} catch (OptimisticLockException e) {
			//
			throw new MpNegocioException(
								"Erro de concorrência. Esse SISTEMA CONFIGURAÇÃO... já foi alterado anteriormente!");
		}
    }

    public void remover(MpSistemaConfig mpSistemaConfig)  {
    	//
    	this.mpSistemaConfigRepository.delete(mpSistemaConfig);
    }

    public List<MpSistemaConfig> findAllByParametro() {
    	//
    	List<MpSistemaConfig> mpObjs = this.mpSistemaConfigRepository.findAllByOrderByParametro();

    	return mpObjs;
    }
    
    public MpSistemaConfig findById(Long id) {
    	//
    	Optional<MpSistemaConfig> mpObj = this.mpSistemaConfigRepository.findById(id);

    	return mpObj.orElse(null);
    }

    public MpSistemaConfig findByParametro(String parametro) {
    	//
    	Optional<MpSistemaConfig> mpObj = this.mpSistemaConfigRepository.findByParametro(parametro);

    	return mpObj.orElse(null);
    }

}
