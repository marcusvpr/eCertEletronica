package com.mpxds.basic.model.xml.converte;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="RegistroImoveis")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"versaoLayout", "mpRemessaXml", "mpDistribuicaoExtrajudicialXmls"})
public class MpRegistroImoveisXml {
	//
	private String versaoLayout;	
	private MpRemessaXml mpRemessaXml;
	private List<MpDistribuicaoExtrajudicialXml> mpDistribuicaoExtrajudicialXmls = 
																			new ArrayList<MpDistribuicaoExtrajudicialXml>();

	// ---

	public MpRegistroImoveisXml() {};

	public MpRegistroImoveisXml(String versaoLayout, MpRemessaXml mpRemessaXml) {
		//
		super();
		
		this.versaoLayout = versaoLayout;
		this.mpRemessaXml = mpRemessaXml;
	}

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
	
}