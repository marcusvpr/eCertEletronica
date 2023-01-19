package com.mpxds.basic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpTabelaInterna;
import com.mpxds.basic.model.dto.MpTabelaInternaDTO;
import com.mpxds.basic.repository.MpTabelaInternaRepository;
import com.mpxds.basic.util.jsf.MpFacesUtil;
import com.mpxds.basic.util.report.MpExecutorRelatorio;
import com.mpxds.basic.util.report.MpExecutorRelatorioDTO;

@Named
public class MpRelatorioTabelaInternaController implements Serializable {
	//
	private static final long serialVersionUID = 1L;

    @Autowired
	private FacesContext facesContext;

    @Autowired
	private HttpServletResponse response;

    @Autowired
	private EntityManager manager;

    @Autowired
	private MpTabelaInternaRepository mpTabelaInternaRepository;

	// ---
	
    @Transactional
    public void emitir() {
		//
		Map<String, Object> parametros = new HashMap<>();
		
		MpExecutorRelatorio executor = new MpExecutorRelatorio("/relatorios/mpRelatorio_tabelas_internas.jasper", 
												this.response, parametros, "MpRelatorio_tabelas_internas.pdf");
		//
		Session session = manager.unwrap(Session.class);
		
		session.doWork(executor);
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			MpFacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

    @Transactional
    public void emitirDTO() {
		//
		Map<String, Object> parametros = new HashMap<>();
		
		MpExecutorRelatorioDTO executor = new MpExecutorRelatorioDTO("/relatorios/mpRelatorio_tabela_interna_dto.jasper", 
												this.response, parametros, "MpRelatorio_tabelas_internas.pdf");
		//
		Collection<Object> data = new ArrayList<Object>();
		//
		List<MpTabelaInterna> mpTabelaInternaList = mpTabelaInternaRepository.findAll();
		
		for (MpTabelaInterna mpTabelaInterna : mpTabelaInternaList) {
			//
			MpTabelaInternaDTO mpTabelaInternaDTO = new MpTabelaInternaDTO(
												mpTabelaInterna.getMpTipoTabelaInterna().getDescricao(),
												mpTabelaInterna.getCodigo(),
												mpTabelaInterna.getDescricao());
			data.add(mpTabelaInternaDTO);
		}
		//
		try {
			executor.execute(data);
			//
		} catch (Exception e) {
			MpFacesUtil.addErrorMessage("A execução do relatório apresentou erro. Contactar o Suporte");
		}
		//
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			MpFacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	public void mpFechar(CloseEvent event) {
		//		
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/Dashboard");
			
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}

}