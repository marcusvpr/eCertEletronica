package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Participante")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03", "campo04", "campo05"})
public class MpParticipanteXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private String campo04;	
	private String campo05;	

	// ---

	public MpParticipanteXml() {};

	public MpParticipanteXml(String campo01, String campo02, String campo03, String campo04, String campo05) {
		//
		super();
		
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
		this.campo04 = campo04;
		this.campo05 = campo05;
	}

	@XmlAttribute(name="Tipo")
	public String getCampo01() { 
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01;
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlAttribute(name="TipoPessoa")
	public String getCampo02() { 
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02;
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }
	
	@XmlAttribute(name="Nome")
	public String getCampo03() { 
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }

	@XmlAttribute(name="CPFCNPJ")
	public String getCampo04() { 
		if (null == this.campo04 || this.campo04.isEmpty()) return null;
		return this.campo04;
	}
	public void setCampo04(String newCampo04) { this.campo04 = newCampo04; }

	@XmlAttribute(name="JustificativaAusenciaCPFCNPJ")
	public String getCampo05() { 
		if (null == this.campo05 || this.campo05.isEmpty()) return null;
		return this.campo05;
	}
	public void setCampo05(String newCampo05) { this.campo05 = newCampo05; }
	
}