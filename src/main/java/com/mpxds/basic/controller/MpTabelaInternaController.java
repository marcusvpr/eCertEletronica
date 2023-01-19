package com.mpxds.basic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;

//import com.mpxds.basic.service.sisJuri.MpTabelaInternaServiceX;
import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpTabelaInterna;
import com.mpxds.basic.model.enums.MpTipoTabelaInterna;
import com.mpxds.basic.service.MpTabelaInternaService;
import com.mpxds.basic.util.jsf.MpFacesUtil;

@Named
public class MpTabelaInternaController implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Autowired
	private MpTabelaInternaService mpTabelaInternaService;
	
	// ---

	private String txtTitulo = "Tabela Interna";

	private MpTabelaInterna mpTabelaInterna;
	private MpTabelaInterna mpTabelaInternaAnt;

	private List<MpTabelaInterna> mpTabelaInternaList;
	private List<MpTabelaInterna> filtroMpTabelaInternaList;

	private MpTipoTabelaInterna mpTipoTabelaInterna;
	private List<MpTipoTabelaInterna> mpTipoTabelaInternaList;
	
	private Boolean indEditavel = true;	
	private Boolean indEditavelSubcat = false;
	
	private String txtModoTela = "";
	private String txtModoSubtabelaInternaDialog = "";

	private MpTabelaInterna mpTabelaInternaPai = new MpTabelaInterna();
	private MpTabelaInterna mpSubtabelaInterna = new MpTabelaInterna();
	
	private List<MpTabelaInterna> mpTabelaInternasRaizes = new ArrayList<MpTabelaInterna>();
	
	private List<MpTabelaInterna> mpSubtabelaInternas;
	private List<MpTabelaInterna> mpSubtabelaInternaExcluidaList = new ArrayList<MpTabelaInterna>();	
		
	// -----------------------
	
	public void inicializar() {
		//
		mpTabelaInterna = new MpTabelaInterna(null, null, "", "");
		mpTabelaInternaAnt = new MpTabelaInterna(null, null, "", "");
		
		this.mpTabelaInternaList = this.mpTabelaInternaService.findAllByMpTipoTabelaInterna();
		
		this.mpTipoTabelaInternaList = Arrays.asList(MpTipoTabelaInterna.values());
		
		this.carregarMpTabelaInternas();
	}

	public void carregarMpTabelaInternas() {
		//
		if (this.mpTabelaInterna.getMpTipoTabelaInterna() != null) {
			//
			if (this.mpTabelaInternaPai != null) {
				this.carregarMpSubtabelaInternas();
			}
		}
		//
		this.limpar();
	}
	
	public void carregarMpSubtabelaInternas() {
		//
//		this.mpSubtabelaInternas = this.mpTabelaInternas.mpFilhaList(this.mpTabelaInternaPai);
	}
	
	public void salvar() {
		//
		this.mpTabelaInterna = this.mpTabelaInternaService.guardar(this.mpTabelaInterna);
		//
		if (this.mpSubtabelaInternaExcluidaList.size() > 0) {
			for (MpTabelaInterna mpSubtabelaInterna : this.mpSubtabelaInternaExcluidaList) {
				//
				if (null == mpSubtabelaInterna.getId()) continue;

				this.mpTabelaInternaService.remover(mpSubtabelaInterna);
			}
		}
		//
		MpFacesUtil.addInfoMessage("Tabela Interna... salva com sucesso!");
	}

	// ---
	
	public void alterarMpSubtabelaInterna() {
		//
		this.txtModoSubtabelaInternaDialog = "Edição";
		
		this.indEditavelSubcat = true;
	}			
	
	public void adicionarMpSubtabelaInternaX() {
		//
		this.txtModoSubtabelaInternaDialog = "Novo";
		
		if (this.mpSubtabelaInterna != null) {
			this.mpSubtabelaInterna.setMpTipoTabelaInterna(this.mpTabelaInterna.getMpTipoTabelaInterna());
			this.mpSubtabelaInterna.setMpPai(this.mpTabelaInterna);

			this.mpSubtabelaInterna.setCodigo("");
			this.mpSubtabelaInterna.setDescricao("");
			
//			this.mpTabelaInterna.getMpFilhas().add(this.mpSubtabelaInterna);
		}
		//
		this.indEditavelSubcat = true;
	}

	public void removerMpSubtabelaInternaX() {
		//
		try {
//			this.mpTabelaInterna.getMpFilhas().remove(this.mpSubtabelaInterna);
			
			this.mpSubtabelaInternaExcluidaList.add(this.mpSubtabelaInterna);
			
			MpFacesUtil.addInfoMessage("SubtabelaInterna... " + this.mpSubtabelaInterna.getDescricao()
																	+ " excluída com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}			

	public void salvarMpSubtabelaInterna() {
		//
		this.indEditavelSubcat = false;
		
		this.mpSubtabelaInterna = new MpTabelaInterna();
	}			

	public void fecharMpSubtabelaInterna() {
		//
//		if (this.txtModoSubtabelaInternaDialog.equals("Novo"))
//			this.mpTabelaInterna.getMpFilhas().remove(this.mpSubtabelaInterna);
	}			
		
	// ------------- Trata Botões -------------
	  
	public void mpListener(ActionEvent event){
		//
		this.mpTabelaInterna = (MpTabelaInterna)event.getComponent().getAttributes().
																			get("TabelaInternaSelecionado");
	}	

	public void mpFechar(CloseEvent event) {
		//
//		System.out.println("MpCadastroTabelaInternaBean.FECHAR()");
		
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/Dashboard");
			
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}	

	public void mpNew() {
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		
		this.mpTabelaInterna = new MpTabelaInterna(null, null, "", "");
		//
		this.indEditavel = false;
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpTabelaInterna.getId()) return;
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		
		this.indEditavel = false;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpView() {
		//
		if (null == this.mpTabelaInterna.getId()) return;
		//
		this.setMpTabelaInternaAnt(this.mpTabelaInterna);
		
		this.indEditavel = true;
		//
		this.txtModoTela = "( Visualização )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpTabelaInterna.getId()) return;
		//
		try {
			this.mpTabelaInternaService.remover(mpTabelaInterna);
			
			this.mpTabelaInternaList.remove(this.mpTabelaInterna);
			//
			MpFacesUtil.addInfoMessage("Tabela Interna... " + 
											this.mpTabelaInterna.getDescricao()	+ " excluído com sucesso.");
			//
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
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
		this.mpTabelaInternaList = this.mpTabelaInternaService.findAllByMpTipoTabelaInterna();

		this.mpTabelaInternaAnt = this.mpTabelaInterna;
		//
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
		this.mpTabelaInterna = this.mpTabelaInternaAnt;
		
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	public void mpComplementaNavegacao() {
		//
//		if (this.mpTabelaInterna.getMpFilhas().isEmpty())
//			this.mpTabelaInterna.setMpFilhas(this.mpTabelaInternas.mpFilhaList(this.mpTabelaInterna));
	}

	private void limpar() {
		//
		this.mpTabelaInterna = new MpTabelaInterna(null, null, "", "");

		//		this.mpTabelaInterna.setMpFilhas(new ArrayList<MpTabelaInterna>());
		//
		this.mpTabelaInternaPai = null;
		//
		this.mpSubtabelaInterna = new MpTabelaInterna();
		this.mpSubtabelaInternas = new ArrayList<>();
	}
	
	// ---
	
	
	public boolean isEditando() { return this.mpTabelaInterna.getId() != null; }
	
	public boolean isIndEditavel() { return indEditavel; }
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }

	public boolean getIndEditavelSubcat() { return indEditavelSubcat; }
	public void setIndEditavelSubcat(Boolean indEditavelSubcat) { this.indEditavelSubcat = indEditavelSubcat; }

	public String getTxtTitulo() { return txtTitulo; }
	public void setTxtTitulo(String txtTitulo) { this.txtTitulo = txtTitulo; }

	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
	
	public String getTxtModoSubtabelaInternaDialog() { return txtModoSubtabelaInternaDialog; }
	public void setTxtModoSubtabelaInternaDialog(String txtModoSubtabelaInternaDialog) {
										this.txtModoSubtabelaInternaDialog = txtModoSubtabelaInternaDialog; }
	
	public MpTabelaInterna getMpTabelaInterna() { return mpTabelaInterna; }
	public void setMpTabelaInterna(MpTabelaInterna mpTabelaInterna) {
		this.mpTabelaInterna = mpTabelaInterna;		
		
		if (this.mpTabelaInterna != null) {
			this.mpTabelaInternaPai = this.mpTabelaInterna.getMpPai();
		}
	}

	public MpTabelaInterna getMpTabelaInternaAnt() { return mpTabelaInternaAnt; }
	public void setMpTabelaInternaAnt(MpTabelaInterna mpTabelaInternaAnt) {
		try {
			this.mpTabelaInternaAnt = (MpTabelaInterna) this.mpTabelaInterna.clone();
			this.mpTabelaInternaAnt.setId(this.mpTabelaInterna.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public List<MpTabelaInterna> getMpTabelaInternasRaizes() { return mpTabelaInternasRaizes; }

	@NotNull
	public MpTabelaInterna getMpTabelaInternaPai() { return mpTabelaInternaPai; }
	public void setMpTabelaInternaPai(MpTabelaInterna mpTabelaInternaPai) {	
																this.mpTabelaInternaPai = mpTabelaInternaPai; }
	public List<MpTabelaInterna> getMpSubtabelaInternas() {	return mpSubtabelaInternas; }
	
	public MpTabelaInterna getMpSubtabelaInterna() { return mpSubtabelaInterna; }
	public void setMpSubtabelaInterna(MpTabelaInterna mpSubtabelaInterna) { 
																this.mpSubtabelaInterna = mpSubtabelaInterna; }

	public List<MpTabelaInterna> getMpTabelaInternaList() { return mpTabelaInternaList; }
	
	public List<MpTabelaInterna> getFiltroMpTabelaInternaList() { return filtroMpTabelaInternaList; }
	public void setFiltroMpTabelaInternaList(List<MpTabelaInterna> filtroMpTabelaInternaList) { 
													this.filtroMpTabelaInternaList = filtroMpTabelaInternaList; }
	
	public MpTipoTabelaInterna getMpTipoTabelaInterna() { return mpTipoTabelaInterna; }
	public void setMpTipoTabelaInterna(MpTipoTabelaInterna mpTipoTabelaInterna) {
													this.mpTipoTabelaInterna = mpTipoTabelaInterna; }
	public List<MpTipoTabelaInterna> getMpTipoTabelaInternaList() { return mpTipoTabelaInternaList; }

}