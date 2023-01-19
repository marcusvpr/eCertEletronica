package com.mpxds.basic.model.xml.converte.se;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mpxds.basic.model.xml.converte.MpCancelamentoPrenotacaoXml;
import com.mpxds.basic.model.xml.converte.MpCertidaoGenericaXml;
import com.mpxds.basic.model.xml.converte.MpCertidaoPrenotacaoXml;
import com.mpxds.basic.model.xml.converte.MpCertidaoXml;
import com.mpxds.basic.model.xml.converte.MpComplementoEmolumentosXml;
import com.mpxds.basic.model.xml.converte.MpDistribuicaoExtrajudicialXml;
import com.mpxds.basic.model.xml.converte.MpFolhaAdicionalComplementoXml;
import com.mpxds.basic.model.xml.converte.MpInformacaoVerbalXml;
import com.mpxds.basic.model.xml.converte.MpRemessaXml;
import com.mpxds.basic.model.xml.converte.MpVistoXml;

@XmlRootElement(name="RegistroDistribuicao")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"versaoLayout", "mpRemessaXml", 
						"mpDistribuicaoExtrajudicialXmls", "mpCertidaoXmls",         "mpVistoXmls", 
						"mpFolhaAdicionalComplementoXmls", "mpInformacaoVerbalXmls", "mpComplementoEmolumentosXmls",
						"mpCertidaoGenericaXmls", " mpCertidaoPrenotacaoXmls", "mpCancelamentoPrenotacaoXmls"
						})
public class MpRegistroDistribuicaoSEXml {
	//
	private String versaoLayout;	
	private MpRemessaXml mpRemessaXml;
	private List<MpDistribuicaoExtrajudicialXml> mpDistribuicaoExtrajudicialXmls = 
												 new ArrayList<MpDistribuicaoExtrajudicialXml>();
	private List<MpCertidaoXml> mpCertidaoXmls = new ArrayList<MpCertidaoXml>();
	private List<MpVistoXml> mpVistoXmls = new ArrayList<MpVistoXml>();
	private List<MpFolhaAdicionalComplementoXml> mpFolhaAdicionalComplementoXmls = new ArrayList<MpFolhaAdicionalComplementoXml>();
	private List<MpInformacaoVerbalXml> mpInformacaoVerbalXmls = new ArrayList<MpInformacaoVerbalXml>();
	private List<MpComplementoEmolumentosXml> mpComplementoEmolumentosXmls = new ArrayList<MpComplementoEmolumentosXml>();
	private List<MpCertidaoGenericaXml> mpCertidaoGenericaXmls = new ArrayList<MpCertidaoGenericaXml>();
	private List<MpCertidaoPrenotacaoXml> mpCertidaoPrenotacaoXmls = new ArrayList<MpCertidaoPrenotacaoXml>();
	private List<MpCancelamentoPrenotacaoXml> mpCancelamentoPrenotacaoXmls = new ArrayList<MpCancelamentoPrenotacaoXml>();

	// ---

	public MpRegistroDistribuicaoSEXml() {};

	public MpRegistroDistribuicaoSEXml(String versaoLayout, MpRemessaXml mpRemessaXml) {
		//
		super();
		
		this.versaoLayout = versaoLayout;
		this.mpRemessaXml = mpRemessaXml;
	}


	// ---
	
	public MpRegistroDistribuicaoSEXml(String versaoLayout, MpRemessaXml mpRemessaXml,
			List<MpDistribuicaoExtrajudicialXml> mpDistribuicaoExtrajudicialXmls, List<MpCertidaoXml> mpCertidaoXmls,
			List<MpVistoXml> mpVistoXmls, List<MpFolhaAdicionalComplementoXml> mpFolhaAdicionalComplementoXmls,
			List<MpInformacaoVerbalXml> mpInformacaoVerbalXmls,
			List<MpComplementoEmolumentosXml> mpComplementoEmolumentosXmls,
			List<MpCertidaoGenericaXml> mpCertidaoGenericaXmls, List<MpCertidaoPrenotacaoXml> mpCertidaoPrenotacaoXmls,
			List<MpCancelamentoPrenotacaoXml> mpCancelamentoPrenotacaoXmls) {
		//
		super();

		this.versaoLayout = versaoLayout;
		this.mpRemessaXml = mpRemessaXml;
		this.mpDistribuicaoExtrajudicialXmls = mpDistribuicaoExtrajudicialXmls;
		this.mpCertidaoXmls = mpCertidaoXmls;
		this.mpVistoXmls = mpVistoXmls;
		this.mpFolhaAdicionalComplementoXmls = mpFolhaAdicionalComplementoXmls;
		this.mpInformacaoVerbalXmls = mpInformacaoVerbalXmls;
		this.mpComplementoEmolumentosXmls = mpComplementoEmolumentosXmls;
		this.mpCertidaoGenericaXmls = mpCertidaoGenericaXmls;
		this.mpCertidaoPrenotacaoXmls = mpCertidaoPrenotacaoXmls;
		this.mpCancelamentoPrenotacaoXmls = mpCancelamentoPrenotacaoXmls;
	}

	// ----
	
	@XmlAttribute(name="VersaoLayout")
	public String getVersaoLayout() { return this.versaoLayout; }
	public void setVersaoLayout(String newVersaoLayout) { this.versaoLayout = newVersaoLayout; }

	@XmlElement(name="Remessa")
	public MpRemessaXml getMpRemessaXml() { return this.mpRemessaXml; }
	public void setMpRemessaXml(MpRemessaXml newMpRemessaXml) { this.mpRemessaXml = newMpRemessaXml; }

	@XmlElement(name="DistribuicaoExtrajudicial")
	public List<MpDistribuicaoExtrajudicialXml> getMpDistribuicaoExtrajudicialXmls() { 
																				return this.mpDistribuicaoExtrajudicialXmls; }
	public void setMpDistribuicaoExtrajudicialXmls(List<MpDistribuicaoExtrajudicialXml> newMpDistribuicaoExtrajudicialXmls) {
													this.mpDistribuicaoExtrajudicialXmls = newMpDistribuicaoExtrajudicialXmls; }

	@XmlElement(name="Certidao")
	public List<MpCertidaoXml> getMpCertidaoXmls() { return this.mpCertidaoXmls; }
	public void setMpCertidaoXmls(List<MpCertidaoXml> newMpCertidaoXmls) { this.mpCertidaoXmls = newMpCertidaoXmls; }

	@XmlElement(name="Visto")
	public List<MpVistoXml> getMpVistoXmls() { return this.mpVistoXmls; }
	public void setMpVistoXmls(List<MpVistoXml> newMpVistoXmls) { this.mpVistoXmls = newMpVistoXmls; }

	@XmlElement(name="FolhaAdicionalComplemento")
	public List<MpFolhaAdicionalComplementoXml> getMpFolhaAdicionalComplementoXmls() { 
																				return this.mpFolhaAdicionalComplementoXmls; }
	public void setMpFolhaAdicionalComplementoXmls(List<MpFolhaAdicionalComplementoXml> newMpFolhaAdicionalComplementoXmls) { 
													this.mpFolhaAdicionalComplementoXmls = newMpFolhaAdicionalComplementoXmls; }

	@XmlElement(name="InformacaoVerbal")
	public List<MpInformacaoVerbalXml> getMpInformacaoVerbalXmls() { return this.mpInformacaoVerbalXmls; }
	public void setMpInformacaoVerbalXmls(List<MpInformacaoVerbalXml> newMpInformacaoVerbalXmls) { 
																		this.mpInformacaoVerbalXmls = newMpInformacaoVerbalXmls; }

	@XmlElement(name="ComplementoEmolumentos")
	public List<MpComplementoEmolumentosXml> getMpComplementoEmolumentosXmls() { return this.mpComplementoEmolumentosXmls; }
	public void setMpComplementoEmolumentosXmls(List<MpComplementoEmolumentosXml> newMpComplementoEmolumentosXmls) { 
															this.mpComplementoEmolumentosXmls = newMpComplementoEmolumentosXmls; }
	
	@XmlElement(name="CertidaoGenerica")
	public List<MpCertidaoGenericaXml> getMpCertidaoGenericaXmls() { return this.mpCertidaoGenericaXmls; }
	public void setMpCertidaoGenericaXmls(List<MpCertidaoGenericaXml> newMpCertidaoGenericaXmls) { 
																		this.mpCertidaoGenericaXmls = newMpCertidaoGenericaXmls; }
	
	@XmlElement(name="CertidaoPrenotacao")
	public List<MpCertidaoPrenotacaoXml> getMpCertidaoPrenotacaoXmls() { return this.mpCertidaoPrenotacaoXmls; }
	public void setMpCertidaoPrenotacaoXmls(List<MpCertidaoPrenotacaoXml> newMpCertidaoPrenotacaoXmls) { 
																	this.mpCertidaoPrenotacaoXmls = newMpCertidaoPrenotacaoXmls; }
		
	@XmlElement(name="CancelamentoPrenotacao")
	public List<MpCancelamentoPrenotacaoXml> getMpCancelamentoPrenotacaoXmls() { return this.mpCancelamentoPrenotacaoXmls; }
	public void setMpCancelamentoPrenotacaoXmls(List<MpCancelamentoPrenotacaoXml> newMpCancelamentoPrenotacaoXmls) { 
															this.mpCancelamentoPrenotacaoXmls = newMpCancelamentoPrenotacaoXmls; }

}