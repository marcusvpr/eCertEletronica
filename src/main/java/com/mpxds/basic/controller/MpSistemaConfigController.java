package com.mpxds.basic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpSistemaConfig;
import com.mpxds.basic.model.enums.MpTipoCampo;
import com.mpxds.basic.repository.MpSistemaConfigRepository;
import com.mpxds.basic.service.MpSistemaConfigService;
import com.mpxds.basic.util.jsf.MpFacesUtil;

@Named
public class MpSistemaConfigController implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
    @Autowired
	private MpSistemaConfigRepository mpSistemaConfigRepository;

    @Autowired
	private MpSistemaConfigService mpSistemaConfigService;
		
	// ---
	
	private String txtTitulo = "Sistema Configuração";

	private MpSistemaConfig mpSistemaConfig;
	private MpSistemaConfig mpSistemaConfigAnt;
	private String numeroSistemaConfig;

	private Boolean indEditavel = true;
	private String txtModoTela = "";
	
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private List<MpSistemaConfig> mpSistemaConfigList;
	private List<MpSistemaConfig> filtroMpSistemaConfigList;
	
	private MpTipoCampo mpTipoCampo;
	private List<MpTipoCampo> mpTipoCampoList;
	
	// ------------------
	
	public void inicializar() {
		//		
		this.mpSistemaConfig = new MpSistemaConfig();	
		this.mpSistemaConfigAnt = new MpSistemaConfig();
		//
		this.indEditavel = true;		
		this.txtModoTela = "";
		
		this.mpSistemaConfigList = this.mpSistemaConfigService.findAllByParametro();
		
		this.mpTipoCampoList = Arrays.asList(MpTipoCampo.values());
		
		this.mpTop();
	}
			
	public void salvar() {
		//
		this.mpSistemaConfig = this.mpSistemaConfigService.guardar(this.mpSistemaConfig);

		MpFacesUtil.addInfoMessage(this.txtTitulo + "... salvo com sucesso! ( " + this.mpSistemaConfig.getParametro());
	}
	
	// ------------- Trata Botões -------------
	  
	public void mpListener(ActionEvent event){
		//
		this.mpSistemaConfig = (MpSistemaConfig)event.getComponent().getAttributes().get("SistemaConfigSelecionado");		
	}	

	public void mpFechar(CloseEvent event) {
		//
//		System.out.println("MpCadastroSistemaConfigBean.FECHAR()");
		
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/dashboard.xhtml");
			
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}	
		
	public void mpNew() {
		//
//		System.out.println("MpCadastroSistemaConfigBean.NOVO()");

		this.setMpSistemaConfigAnt(this.mpSistemaConfig);
		
		this.mpSistemaConfig = new MpSistemaConfig();	
		
		this.setIndEditavel(false);
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpSistemaConfig.getId()) return;
		//
		this.setMpSistemaConfigAnt(this.mpSistemaConfig);
		
		this.indEditavel = false;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpView() {
		//
		if (null == this.mpSistemaConfig.getId()) return;
		//
		this.setMpSistemaConfigAnt(this.mpSistemaConfig);
		
		this.indEditavel = true;
		//
		this.txtModoTela = "( Visualização )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpSistemaConfig.getId()) return;
		//
		try {
			this.mpSistemaConfigRepository.delete(this.mpSistemaConfig);
			
			this.mpSistemaConfigList.remove(this.mpSistemaConfig);
			//
			MpFacesUtil.addInfoMessage(this.txtTitulo + "... " + this.mpSistemaConfig.getParametro() +
																	 			" ... excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
//		System.out.println("MpCadastroSistemaConfigBean.GRAVA() (" + this.getIndEditavel() + "/" + this.txtModoTela);
		
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
		this.mpSistemaConfigList = this.mpSistemaConfigService.findAllByParametro();

		this.setMpSistemaConfigAnt(this.mpSistemaConfig);
		//
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
//		System.out.println("MpCadastroSistemaConfigBean.DESFAZ()");

		this.mpSistemaConfig = this.mpSistemaConfigAnt;

		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	// -------- Trata Navegação ...

	public void mpTop() {
		//
		this.mpSistemaConfig = this.mpSistemaConfigRepository.findFirstByParametroIsNotNullOrderByParametroAsc(); 
		if (null == this.mpSistemaConfig)
			this.limpar();
//		else
//			this.mpTrataMpSistemaConfig();
		//
		this.txtModoTela = "( Início )";
	}
	public void mpPrev() {
		if (null == this.mpSistemaConfig.getParametro()) return;
		//
		this.setMpSistemaConfigAnt(this.mpSistemaConfig);
		//
		List<MpSistemaConfig> mpSistemaConfigList = this.mpSistemaConfigRepository.
										findTop1ByParametroLessThanOrderByParametroDesc(mpSistemaConfig.getParametro());
		if (mpSistemaConfigList.isEmpty()) {
			this.mpSistemaConfig = this.mpSistemaConfigAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else {
//			System.out.println("MpSistemaConfigController.mpPrev() ( " + mpSistemaConfigList.get(0).getParametro());
			this.mpSistemaConfig = mpSistemaConfigList.get(0);
					
			this.txtModoTela = "( Anterior )";
		}
		//
//		this.mpTrataMpSistemaConfig();
	}
	
	public void mpNext() {
		//
		if (null == this.mpSistemaConfig.getParametro()) return;
		//
		this.setMpSistemaConfigAnt(this.mpSistemaConfig);
		//
		List<MpSistemaConfig> mpSistemaConfigList = this.mpSistemaConfigRepository.
										findTop1ByParametroGreaterThanOrderByParametro(mpSistemaConfig.getParametro());
		if (mpSistemaConfigList.isEmpty()) {
			this.mpSistemaConfig = this.mpSistemaConfigAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else {
//			System.out.println("MpSistemaConfigController.mpNext() ( " + mpSistemaConfigList.get(0).getParametro());
			this.mpSistemaConfig = mpSistemaConfigList.get(0);
					
			this.txtModoTela = "( Próximo )";
		}
		//
//		this.mpTrataMpSistemaConfig();
	}
	
	public void mpBotton() {
		//
		this.mpSistemaConfig = this.mpSistemaConfigRepository.findFirstByParametroIsNotNullOrderByParametroDesc();  
		if (null == this.mpSistemaConfig)
			this.limpar();
//		else
//			this.mpTrataMpSistemaConfig();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpSistemaConfig.getId()) return;

//		try {
			this.setMpSistemaConfigAnt(this.mpSistemaConfig);

//			this.mpSistemaConfig = (MpSistemaConfig) this.mpSistemaConfig.clone();
			
			this.mpSistemaConfig = SerializationUtils.clone(this.mpSistemaConfig);
			//
			this.mpSistemaConfig.setId(null);
			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//
		this.indEditavel = false;
		this.indEditavelNav = false;
		this.indNaoEditavel = true;
		//
		this.txtModoTela = "( Clone )";
	}
	
	private void limpar() {
		//
		this.mpSistemaConfig = new MpSistemaConfig();
		//
		this.mpSistemaConfig.setParametro("");		
		this.mpSistemaConfig.setDescricao("");		
		this.mpSistemaConfig.setObjeto("");		
	}
	
	@SuppressWarnings("deprecation")
	public void posProcessarXls(Object documento) {
		//
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fonteCabecalho.setFontHeightInPoints((short) 16);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//
		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}
	
	// ---

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByValor(Object value, Object filter, Locale locale) {
    	//
//		System.out.println("MpCadastroSistemaConfigBean.FILTER()");

    	String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null||filterText.equals("")) return true;
         
        if (value == null) return false;
        //
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }
	      
    // =============================
    
	public MpSistemaConfig getMpSistemaConfig() { return mpSistemaConfig; }
	public void setMpSistemaConfig(MpSistemaConfig mpSistemaConfig) { this.mpSistemaConfig = mpSistemaConfig; }

	public MpSistemaConfig getMpSistemaConfigAnt() { return mpSistemaConfigAnt; }
	public void setMpSistemaConfigAnt(MpSistemaConfig mpSistemaConfigAnt) {
		try {
			if (null==this.mpSistemaConfig) return;
			//
			this.mpSistemaConfigAnt = (MpSistemaConfig) this.mpSistemaConfig.clone();
			this.mpSistemaConfigAnt.setId(this.mpSistemaConfig.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
    
	public String getNumeroSistemaConfig() { return numeroSistemaConfig; }
	public void setNumeroSistemaConfig(String numeroSistemaConfig) { this.numeroSistemaConfig = numeroSistemaConfig; }

	// ---
	
	public boolean isEditando() { return this.mpSistemaConfig.getId() != null; }
	
	public boolean isIndEditavel() { return indEditavel; }
	public boolean getIndEditavel() { return indEditavel; }
	public void setIndEditavel(Boolean indEditavel) { this.indEditavel = indEditavel; }
	
	public boolean isIndEditavelNav() { return indEditavelNav; }
	public boolean getIndEditavelNav() { return indEditavelNav; }
	public void setIndEditavelNav(Boolean indEditavelNav) { this.indEditavelNav = indEditavelNav; }
	
	public boolean isIndNaoEditavel() { return indNaoEditavel; }
	public boolean getIndNaoEditavel() { return indNaoEditavel; }
	public void setIndNaoEditavel(Boolean indNaoEditavel) {	this.indNaoEditavel = indNaoEditavel; }
	
	public String getTxtTitulo() { return txtTitulo; }
	public void setTxtTitulo(String txtTitulo) { this.txtTitulo = txtTitulo; }
		
	public String getTxtModoTela() { return txtModoTela; }
	public void setTxtModoTela(String txtModoTela) { this.txtModoTela = txtModoTela; }
		
	//
	
	public List<MpSistemaConfig> getMpSistemaConfigList() { return mpSistemaConfigList; }
	
	public List<MpSistemaConfig> getFiltroMpSistemaConfigList() { return filtroMpSistemaConfigList; }
	public void setFiltroMpSistemaConfigList(List<MpSistemaConfig> filtroMpSistemaConfigList) { 
															this.filtroMpSistemaConfigList = filtroMpSistemaConfigList; }
		
	public MpTipoCampo getMpTipoCampo() {	return mpTipoCampo; }
	public void setMpTipoCampo(MpTipoCampo mpTipoCampo) { this.mpTipoCampo = mpTipoCampo; }
	public List<MpTipoCampo> getMpTipoCampoList() { return mpTipoCampoList; }

	   
}