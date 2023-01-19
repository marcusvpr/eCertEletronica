package com.mpxds.basic.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpRole;
import com.mpxds.basic.model.MpUser;
import com.mpxds.basic.repository.MpRoleRepository;
import com.mpxds.basic.repository.MpUserRepository;
import com.mpxds.basic.service.MpUserService;
import com.mpxds.basic.util.jsf.MpFacesUtil;

@Named
public class MpUsuarioController implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
    @Autowired
	private MpUserRepository mpUserRepository;

    @Autowired
	private MpUserService mpUserService;

    @Autowired
	private MpRoleRepository mpRoleRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	// ---
	
	private String txtTitulo = "Usuário";

	private MpUser mpUser;
	private MpUser mpUserAnt;

	private String senha = "";

	private Boolean indEditavel = true;
	private String txtModoTela = "";
	
	private Boolean indEditavelNav = true;
	private Boolean indNaoEditavel = false;
	
	private List<MpUser> mpUserList;
	private List<MpUser> filtroMpUserList;
	
	private MpRole mpRole;
	private List<MpRole> mpRoleList = new ArrayList<MpRole>();

	// ------------------
	
	public void inicializar() {
		//		
		this.mpUser = new MpUser();	
		this.mpUserAnt = new MpUser();
		//
		this.indEditavel = true;		
		this.txtModoTela = "";
		
		this.mpUserList = this.mpUserService.findAllByUsername();
		
		this.mpRoleList = this.mpRoleRepository.findAllByOrderByName();
		
		this.mpTop();
	}
		
	public void salvar() {
		//
		if (!this.senha.isEmpty()) {
			//
			mpUser.setPassword(this.passwordEncoder.encode(this.senha.trim()));
			mpUser.setPasswordConfirm(this.senha.trim());

			MpFacesUtil.addInfoMessage(this.txtTitulo + "... Senha Alterada !");
		}
		//
		this.mpUser = this.mpUserService.guardar(this.mpUser);

		MpFacesUtil.addInfoMessage(this.txtTitulo + "... salvo com sucesso! ( " + this.mpUser.getUsername());
	}
	
	// ------------- Trata Botões -------------
	  
	public void mpListener(ActionEvent event){
		//
		this.mpUser = (MpUser)event.getComponent().getAttributes().get("UsuarioSelecionado");
	}	

	public void mpFechar(CloseEvent event) {
		//
//		System.out.println("MpCadastroUsuarioBean.FECHAR()");
		
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/Dashboard");
			
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}	
		
	public void mpNew() {
		//
		this.setMpUserAnt(this.mpUser);
		
		this.mpUser = new MpUser();	
		//
		this.setIndEditavel(false);
		//
		this.txtModoTela = "( Novo )";
	}
	
	public void mpEdit() {
		//
		if (null == this.mpUser.getId()) return;
		//
		this.setMpUserAnt(this.mpUser);
		
		this.indEditavel = false;
		//
		this.txtModoTela = "( Edição )";
	}
	
	public void mpView() {
		//
		if (null == this.mpUser.getId()) return;
		//
		this.setMpUserAnt(this.mpUser);
		
		this.indEditavel = true;
		//
		this.txtModoTela = "( Visualização )";
	}
	
	public void mpDelete() {
		//
		if (null == this.mpUser.getId()) return;
		//
		try {
			this.mpUserRepository.delete(this.mpUser);
			
			this.mpUserList.remove(this.mpUser);
			//
			MpFacesUtil.addInfoMessage(this.txtTitulo + "... " + this.mpUser.getUsername() +
																	 			" ... excluído com sucesso.");
		} catch (MpNegocioException ne) {
			MpFacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void mpGrava() {
		//
//		System.out.println("MpCadastroUsuarioBean.GRAVA() (" + this.getIndEditavel() + "/" + this.txtModoTela);

//		if (!this.senha.equals("????")) {
//			//
//			this.mpUser.setPassword(BCryptPasswordEncoder.this.senha);
//		}
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
		this.mpUserList = this.mpUserService.findAllByUsername();

		this.setMpUserAnt(this.mpUser);
		//
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	public void mpDesfaz() {
		//
//		System.out.println("MpCadastroUsuarioBean.DESFAZ()");

		this.mpUser = this.mpUserAnt;
		
		this.indEditavel = true;
		//
		this.txtModoTela = "";
	}
	
	// -------- Trata Navegação ...

	public void mpTop() {
		//
		this.mpUser = this.mpUserRepository.findFirstByUsernameIsNotNullOrderByUsernameAsc(); 
		if (null == this.mpUser)
			this.limpar();
//		else
//			this.mpTrataMpUser();
		//
		this.txtModoTela = "( Início )";
	}
	public void mpPrev() {
		if (null == this.mpUser.getUsername()) return;
		//
		this.setMpUserAnt(this.mpUser);
		//
		List<MpUser> mpUserList = this.mpUserRepository.
										findTop1ByUsernameLessThanOrderByUsernameDesc(mpUser.getUsername());
		if (mpUserList.isEmpty()) {
			this.mpUser = this.mpUserAnt;
			//
			this.txtModoTela = "( Anterior - Inicio )";
		} else {
//			System.out.println("MpUserController.mpPrev() ( " + mpUserList.get(0).getUsername());
			this.mpUser = mpUserList.get(0);
					
			this.txtModoTela = "( Anterior )";
		}
		//
//		this.mpTrataMpUser();
	}
	
	public void mpNext() {
		//
		if (null == this.mpUser.getUsername()) return;
		//
		this.setMpUserAnt(this.mpUser);
		//
		List<MpUser> mpUserList = this.mpUserRepository.
										findTop1ByUsernameGreaterThanOrderByUsername(mpUser.getUsername());
		if (mpUserList.isEmpty()) {
			this.mpUser = this.mpUserAnt;
			//
			this.txtModoTela = "( Próximo - Fim )";
		} else {
//			System.out.println("MpUserController.mpNext() ( " + mpUserList.get(0).getUsername());
			this.mpUser = mpUserList.get(0);
					
			this.txtModoTela = "( Próximo )";
		}
		//
//		this.mpTrataMpUser();
	}
	
	public void mpBotton() {
		//
		this.mpUser = this.mpUserRepository.findFirstByUsernameIsNotNullOrderByUsernameDesc();  
		if (null == this.mpUser)
			this.limpar();
//		else
//			this.mpTrataMpUser();
		//
		this.txtModoTela = "( Fim )";
	}
	
	public void mpClone() {
		//
		if (null == this.mpUser.getId()) return;

//		try {
			this.setMpUserAnt(this.mpUser);

//			this.mpUser = (MpUser) this.mpUser.clone();
			
			this.mpUser = SerializationUtils.clone(this.mpUser);
			//
			this.mpUser.setId(null);
			
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
		this.mpUser = new MpUser();
		//
		this.mpUser.setUsername("");		
		this.mpUser.setPassword("");		
		this.mpUser.setPasswordConfirm("");;		
		this.mpUser.setEmail("");
		this.mpUser.setEmailConfirm("");
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
//		System.out.println("MpCadastroUsuarioBean.FILTER()");

    	String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null||filterText.equals("")) return true;
         
        if (value == null) return false;
        //
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }
	
	// ---

	public MpUser getMpUser() { return mpUser; }
	public void setMpUser(MpUser mpUser) { this.mpUser = mpUser; }

	public MpUser getMpUserAnt() { return mpUserAnt; }
	public void setMpUserAnt(MpUser mpUserAnt) {
		try {
			if (null==this.mpUser) return;
			//
			this.mpUserAnt = (MpUser) this.mpUser.clone();
			this.mpUserAnt.setId(this.mpUser.getId());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
	
	public boolean isEditando() { return this.mpUser.getId() != null; }
	
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
	
	public List<MpUser> getMpUserList() { return mpUserList; }
	
	public List<MpUser> getFiltroMpUserList() { return filtroMpUserList; }
	public void setFiltroMpUserList(List<MpUser> filtroMpUserList) { 
																this.filtroMpUserList = filtroMpUserList; }
	
	public MpRole getMpRole() { return mpRole; }
	public void setMpRole(MpRole mpRole) { this.mpRole = mpRole; }

	public List<MpRole> getMpRoleList() { return mpRoleList; }
	public void setMpRoleList(List<MpRole> mpRoleList) { this.mpRoleList = mpRoleList; }

}