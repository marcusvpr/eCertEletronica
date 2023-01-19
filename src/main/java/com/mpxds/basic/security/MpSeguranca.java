package com.mpxds.basic.security;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mpxds.basic.model.MpRole;
import com.mpxds.basic.model.MpUser;
import com.mpxds.basic.service.MpUserServiceImpl;
import com.mpxds.basic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpSeguranca implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Autowired
	private ExternalContext externalContext;

	@Autowired
	private MpUserServiceImpl mpUserServiceImpl;

	@Autowired
    private PasswordEncoder passwordEncoder;
	// ---
	
	private String freshdeskURL = "mpxdshelp.freshdesk.com"; // "mpcom.freshdesk.com"; // "mpxdsrj.freshdesk.com";
	
	private String trocaSenhaAtual;
	private String trocaSenhaNova;
	private String trocaSenhaNovaConfirma;
	
	// SaveInCloud (SIC) S3.Minio... Panel: http://200.150.196.170/ -> PRBWG6g5T2 / G0Rk7Ls5c6 !
	private String sicMinioS3URL = "http://200.150.196.170/";
	private String sicMinioS3User = "PRBWG6g5T2";
	private String sicMinioS3Passw = "G0Rk7Ls5c6";	
	
	// ----	

	public String getNumeroIP() {
		//
		String ipAddress = null;

		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().
																				getExternalContext().getRequest();
		ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (null == ipAddress)
			ipAddress = request.getRemoteAddr();
		//
		return ipAddress;
	}
				
	// ==============
	
	public boolean isAdministradores() {
		//
		Boolean isAdmin = this.externalContext.isUserInRole("ADMIN");
		
		System.out.println("MpSeguranca.isAdministradores() - ( ADMIN = " + isAdmin);
		
		return isAdmin;
	}
	
	public boolean isAdmins() {
		//
		Boolean isAdmin = false;

		try {
			MpUser mpUser = mpUserServiceImpl.findByUsername(this.getUsuarioLogado());
			if (null == mpUser) {
				//
				System.out.println("MpSeguranca.isAdminis() - ( User NULL = " + this.getUsuarioLogado());
			} else {
				List<MpRole> mpRoles = mpUser.getMpRoles();
				
				for (MpRole mpRole : mpRoles) {
					//
					if (mpRole.getName().toUpperCase().equals("ADMIN"))
						isAdmin = true;
				}
			}
			//
		} catch (Exception e) {
			System.out.println("MpSeguranca.isAdminis() - ( e = " + e);
		}
		//
//		System.out.println("MpSeguranca.isAdmins() - ( ADMIN = " + isAdmin);
		
		return isAdmin;
	}
	public boolean isUserCancs() {
		//
		Boolean isUserCanc = false;

		try {
			MpUser mpUser = mpUserServiceImpl.findByUsername(this.getUsuarioLogado());
			if (null == mpUser) {
				//
				System.out.println("MpSeguranca.isUserCancs() - ( User NULL = " + this.getUsuarioLogado());
			} else {
				List<MpRole> mpRoles = mpUser.getMpRoles();
				
				for (MpRole mpRole : mpRoles) {
					//
					if (mpRole.getName().toUpperCase().equals("USER_CANC"))
						isUserCanc = true;
				}
			}
			//
		} catch (Exception e) {
			System.out.println("MpSeguranca.isUserCancs() - ( e = " + e);
		}
		//
//		System.out.println("MpSeguranca.isAdmins() - ( ADMIN = " + isAdmin);
		
		return isUserCanc;
	}
	public boolean isUserCerts() {
		//
		Boolean isUserCert = false;

		try {
			MpUser mpUser = mpUserServiceImpl.findByUsername(this.getUsuarioLogado());
			if (null == mpUser) {
				//
				System.out.println("MpSeguranca.isUserCerts() - ( User NULL = " + this.getUsuarioLogado());
			} else {
				List<MpRole> mpRoles = mpUser.getMpRoles();
				
				for (MpRole mpRole : mpRoles) {
					//
					if (mpRole.getName().toUpperCase().equals("USER_CERT"))
						isUserCert = true;
				}
			}
			//
		} catch (Exception e) {
			System.out.println("MpSeguranca.isUserCerts() - ( e = " + e);
		}
		//
//		System.out.println("MpSeguranca.isAdmins() - ( ADMIN = " + isAdmin);
		
		return isUserCert;
	}
	
	public String getUsuarioLogado() {
		//
		return this.externalContext.getUserPrincipal().getName();
	}
	
	public void enviaSenhaTroca() {
		//
//		System.out.println("MpSeguranca.enviaMpNotificacao() - Entrou!");
		//
		String mensagem = "";
		if (this.trocaSenhaAtual.isEmpty()) mensagem = mensagem + "(Senha Atual) ";
		if (this.trocaSenhaNova.isEmpty()) mensagem = mensagem + "(Senha Nova) ";
		if (this.trocaSenhaNovaConfirma.isEmpty()) mensagem = mensagem + 
															"(Senha Nova Confirma)";
		if (!mensagem.isEmpty()) {
			//		
			MpFacesUtil.addInfoMessage("Informar... " + mensagem);
			return;
		}
		//
		if (!this.trocaSenhaNova.equals(this.trocaSenhaNovaConfirma)) {
			MpFacesUtil.addInfoMessage("Nova Senha x Confirma ... são diferentes !");
			return;
		}
		if (this.trocaSenhaNova.length() < 6) {
			MpFacesUtil.addInfoMessage("Tamanho(Min.=6)... Nova Senha Inválido !");
			return;
		}
		//
//		MpFacesUtil.addInfoMessage("Em implementação! Contactar Suporte");
//		return;
		//
		MpUser mpUser = this.mpUserServiceImpl.findByUsername(this.getUsuarioLogado());
		if (null == mpUser) {
			//
			MpFacesUtil.addInfoMessage("Usuário não encontrado ! Contactar Suporte.");
			return;
		}
		//
//		System.out.println("MpSeguranca.enviaSenhaTroca() ---------------> " + this.trocaSenhaAtual + " / " + 
//							this.passwordEncoder.encode(this.trocaSenhaAtual.trim()) + " / " + mpUser.getPassword());
//		
//		BCryptPasswordEncoder passwordEncoderX = new BCryptPasswordEncoder();		
//		if (passwordEncoderX.matches(this.trocaSenhaAtual.trim(), mpUser.getPassword())) {
//			
//		}
		
		if (passwordEncoder.matches(this.trocaSenhaAtual.trim(), mpUser.getPassword())) {
			//
			mpUser.setPassword(this.passwordEncoder.encode(this.trocaSenhaNova.trim()));
			mpUser.setPasswordConfirm(this.trocaSenhaNova.trim());
			//

			mpUser = this.mpUserServiceImpl.guardar(mpUser);
			//
			MpFacesUtil.addInfoMessage("Troca de senha... efetuada!");
		} else
			MpFacesUtil.addInfoMessage("Senha Atual... Inválida!");			
	}
	
	// ---
	
	public String getFreshdeskURL() { return freshdeskURL; }
	public void setFreshdeskURL(String freshdeskURL) { this.freshdeskURL = freshdeskURL; }
	
	public String getTrocaSenhaAtual() { return trocaSenhaAtual; }
	public void setTrocaSenhaAtual(String trocaSenhaAtual) { this.trocaSenhaAtual = trocaSenhaAtual; }

	public String getTrocaSenhaNova() { return trocaSenhaNova; }
	public void setTrocaSenhaNova(String trocaSenhaNova) { this.trocaSenhaNova = trocaSenhaNova; }
	public String getTrocaSenhaNovaConfirma() { return trocaSenhaNovaConfirma; }
	public void setTrocaSenhaNovaConfirma(String trocaSenhaNovaConfirma) { 
															this.trocaSenhaNovaConfirma = trocaSenhaNovaConfirma; }
	
	// SaveInCloud S3.Minio ..	
	public String getSicMinioS3URL() { return sicMinioS3URL; }
	public void setSicMinioS3URL(String sicMinioS3URL) { this.sicMinioS3URL = sicMinioS3URL; }

	public String getSicMinioS3User() { return sicMinioS3User; }
	public void setSicMinioS3User(String sicMinioS3User) { this.sicMinioS3User = sicMinioS3User; }

	public String getSicMinioS3Passw() { return sicMinioS3Passw; }
	public void setSicMinioS3Passw(String sicMinioS3Passw) { this.sicMinioS3Passw = sicMinioS3Passw; }

}
