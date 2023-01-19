package com.mpxds.basic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpDolar;
import com.mpxds.basic.repository.MpDolarRepository;
import com.mpxds.basic.service.MpDolarService;
import com.mpxds.basic.util.jsf.MpFacesUtil;

@Named
public class MpDolarController implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
    @Autowired
	private MpDolarRepository mpDolarRepository;

    @Autowired
	private MpDolarService mpDolarService;
	
	// ---
	
	private String txtTitulo = "Dólar";

	private MpDolar mpDolar;
	private MpDolar mpDolarAnt;

	private Boolean indEditavel = true;
	private String txtModoTela = "";
	
	private List<MpDolar> mpDolarList;
	private List<MpDolar> filtroMpDolarList;

	// ------------------
	
	public void inicializar() {
		//		
		this.mpDolar = new MpDolar(null, null, BigDecimal.ZERO, "");	
		this.mpDolarAnt = new MpDolar(null, new Date(), BigDecimal.ZERO, "");
		//
		this.indEditavel = true;		
		this.txtModoTela = "";
		
		this.mpDolarList = this.mpDolarService.findAllByDataMovimento();
	}
		
	public void salvar() {
		//
		this.mpDolar = this.mpDolarService.guardar(this.mpDolar);

		MpFacesUtil.addInfoMessage(this.txtTitulo + "... salvo com sucesso! ( " + 
																		this.mpDolar.getDataMovimentoSDF());
	}
	
	// ------------- Trata Botões -------------
	  
	public void mpListener(ActionEvent event){
		//
		this.mpDolar = (MpDolar)event.getComponent().getAttributes().get("DolarSelecionado");
	}	

	public void mpFechar(CloseEvent event) {
		//
//		System.out.println("MpCadastroDolarBean.FECHAR()");
		
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/Dashboard");
			
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}	
		
	public void mpNew() {
		//
		this.setMpDolarAnt(this.mpDolar);
		
		this.mpDolar = new MpDolar(null, new Date(), BigDecimal.ZERO, "");	
		//
		this.setIndEditavel(false);
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpDolar.getId()) return;
		//
		this.setMpDolarAnt(this.mpDolar);
		
		this.indEditavel = false;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpView() {
		//
		if (null == this.mpDolar.getId()) return;
		//
		this.setMpDolarAnt(this.mpDolar);
		
		this.indEditavel = true;
		//
		this.txtModoTela = "( Visualização )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpDolar.getId()) return;
		//
		try {
			this.mpDolarRepository.delete(this.mpDolar);
			
			this.mpDolarList.remove(this.mpDolar);
			//
			MpFacesUtil.addInfoMessage(this.txtTitulo + "... " + this.mpDolar.getDataMovimentoSDF() +
																	 			" ... excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
//		System.out.println("MpCadastroDolarBean.GRAVA() (" + this.getIndEditavel() + "/" + this.txtModoTela);
		
		try {
			this.salvar();
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Erro na Gravação! ( " + e.toString());			
			//
			return;
		}
		//
		this.mpDolarList = this.mpDolarService.findAllByDataMovimento();

		this.setMpDolarAnt(this.mpDolar);
		//
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
//		System.out.println("MpCadastroDolarBean.DESFAZ()");

		this.mpDolar = this.mpDolarAnt;
		
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	// ---

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByValor(Object value, Object filter, Locale locale) {
    	//
//		System.out.println("MpCadastroDolarBean.FILTER()");

    	String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null||filterText.equals("")) return true;
         
        if (value == null) return false;
        //
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }
	
	// ---

	public MpDolar getMpDolar() { return mpDolar; }
	public void setMpDolar(MpDolar mpDolar) { this.mpDolar = mpDolar; }

	public MpDolar getMpDolarAnt() { return mpDolarAnt; }
	public void setMpDolarAnt(MpDolar mpDolarAnt) {
		try {
			if (null==this.mpDolar) return;
			//
			this.mpDolarAnt = (MpDolar) this.mpDolar.clone();
			this.mpDolarAnt.setId(this.mpDolar.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isEditando() { return this.mpDolar.getId() != null; }
	
	public boolean isIndEditavel() { return indEditavel; }
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public String getTxtTitulo() { return txtTitulo; }
	public void setTxtTitulo(String txtTitulo) { this.txtTitulo = txtTitulo; }
		
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	//
	
	public List<MpDolar> getMpDolarList() { return mpDolarList; }
	
	public List<MpDolar> getFiltroMpDolarList() { return filtroMpDolarList; }
	public void setFiltroMpDolarList(List<MpDolar> filtroMpDolarList) { this.filtroMpDolarList = filtroMpDolarList; }
	
}