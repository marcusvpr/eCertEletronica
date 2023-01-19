package com.mpxds.basic.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpTabelaInterna;
import com.mpxds.basic.model.enums.MpTipoTabelaInterna;
import com.mpxds.basic.repository.MpTabelaInternaRepository;

@Service
public class MpTabelaInternaService {
	//
    @Autowired
    private MpTabelaInternaRepository mpTabelaInternaRepository;

    // ---
    
    public MpTabelaInterna guardar(MpTabelaInterna mpTabelaInterna) throws MpNegocioException {
    	//
		try {
			MpTabelaInterna mpObj = this.findByMpTipoTabelaInternaAndCodigo(mpTabelaInterna.getMpTipoTabelaInterna(),
																						mpTabelaInterna.getCodigo());
			
			if (mpObj != null && !mpObj.equals(mpTabelaInterna)) {
				throw new MpNegocioException("Já existe uma Tabela Interna com Tipo e Código.");
			}
			
	    	return this.mpTabelaInternaRepository.saveAndFlush(mpTabelaInterna);
	    	//
		} catch (OptimisticLockException e) {
			//
			throw new MpNegocioException(
									"Erro de concorrência. Esse TABELA INTERNA... já foi alterada anteriormente!");
		}
    }

    public void remover(MpTabelaInterna mpTabelaInterna)  {
    	//
    	this.mpTabelaInternaRepository.delete(mpTabelaInterna);
    }

    public List<MpTabelaInterna> findAllByMpTipoTabelaInterna() {
    	//
    	List<MpTabelaInterna> mpObjs = mpTabelaInternaRepository.findAllMpTipoTabelaInterna();

    	return mpObjs;
    }
 
    public MpTabelaInterna findById(Long id) {
    	//
    	Optional<MpTabelaInterna> mpObj = mpTabelaInternaRepository.findById(id);

    	return mpObj.orElse(null);
    }

    public MpTabelaInterna findByMpTipoTabelaInternaAndCodigo(MpTipoTabelaInterna mpTipoTabelaInterna,
    																			String codigo) {
    	//
    	Optional<MpTabelaInterna> mpObj = this.mpTabelaInternaRepository.findTabelaInternasAndCodigo(
    																			mpTipoTabelaInterna, codigo);
    	return mpObj.orElse(null);
    }

    public MpTabelaInterna findByMpTipoTabelaInternaAndDescricao(MpTipoTabelaInterna mpTipoTabelaInterna,
    																			String descricao) {
    	//
    	Optional<MpTabelaInterna> mpObj = this.mpTabelaInternaRepository.findByMpTipoTabelaInternaAndDescricao(
    																			mpTipoTabelaInterna, descricao);
    	return mpObj.orElse(null);
    }

}
