package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Certidao")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03", "campo04", "campo05",
						"campo06", "campo07",  
						"campo01x", "campo02x", "campo03x", "campo04x", "campo05x",
						"mpParticipantesXml", "mpEmolumentosXml"})
public class MpCertidaoXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private String campo04;	
	private String campo05;	
	private String campo06;	
	private String campo07;	

	private String campo01x;	
	private String campo02x;	
	private String campo03x;	
	private String campo04x;	
	private String campo05x;		
	
	private MpParticipantesXml mpParticipantesXml;
	private MpEmolumentosXml mpEmolumentosXml;

	// ---

	public MpCertidaoXml() {};

	public MpCertidaoXml( String campo01, String campo02, String campo03, String campo04,  String campo05,
						  String campo06, String campo07,
						  String campo01x, String campo02x, String campo03x, String campo04x,  String campo05x,
						  MpParticipantesXml mpParticipantesXml, MpEmolumentosXml mpEmolumentosXml) {
		//
		super();
		
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
		this.campo04 = campo04;
		this.campo05 = campo05;
		this.campo06 = campo06;
		this.campo07 = campo07;

		this.campo01x = campo01x;
		this.campo02x = campo02x;
		this.campo03x = campo03x;
		this.campo04x = campo04x;
		this.campo05x = campo05x;
		
		this.mpParticipantesXml = mpParticipantesXml;
		this.mpEmolumentosXml = mpEmolumentosXml;
	}

	@XmlAttribute(name="DataPratica")
	public String getCampo01() {
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01; 
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlAttribute(name="Selo")
	public String getCampo02() {
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02; 
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }

	@XmlAttribute(name="Aleatorio")
	public String getCampo03() { 
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }

	@XmlAttribute(name="TipoCertidao")
	public String getCampo04() {
		if (null == this.campo04 || this.campo04.isEmpty()) return null;
		return this.campo04;
	}
	public void setCampo04(String newCampo04) { this.campo04 = newCampo04; }
	
	@XmlAttribute(name="NumeroAto")
	public String getCampo05() {
		if (null == this.campo05 || this.campo05.isEmpty()) return null;
		return this.campo05;
	}
	public void setCampo05(String newCampo05) { this.campo05 = newCampo05; }

	@XmlAttribute(name="QuantidadeDistribuicao")
	public String getCampo06() { 
		if (null == this.campo06 || this.campo06.isEmpty()) return null;
		return this.campo06; 
	}
	public void setCampo06(String newCampo06) { this.campo06 = newCampo06; }

	@XmlAttribute(name="QtdFolhasExcedentes")
	public String getCampo07() { 
		if (null == this.campo07 || this.campo07.isEmpty()) return null;
		return this.campo07; 
	}
	public void setCampo07(String newCampo07) { this.campo07 = newCampo07; }

	@XmlAttribute(name="IndAtoEletronico")
	public String getCampo01x() {
		if (null == this.campo01x || this.campo01x.isEmpty()) return null;
		return this.campo01x; 
	}
	public void setCampo01x(String newCampo01x) { this.campo01x = newCampo01x; }

	@XmlAttribute(name="UrlPDFAto")
	public String getCampo02x() {
		if (null == this.campo02x || this.campo02x.isEmpty()) return null;
		return this.campo02x; 
	}
	public void setCampo02x(String newCampo02x) { this.campo02x = newCampo02x; }

	@XmlAttribute(name="HashPDFAto")
	public String getCampo03x() { 
		if (null == this.campo03x || this.campo03x.isEmpty()) return null;
		return this.campo03x;
	}
	public void setCampo03x(String newCampo03x) { this.campo03x = newCampo03x; }

	@XmlAttribute(name="UrlDossie")
	public String getCampo04x() {
		if (null == this.campo04x || this.campo04x.isEmpty()) return null;
		return this.campo04x;
	}
	public void setCampo04x(String newCampo04x) { this.campo04x = newCampo04x; }
	
	@XmlAttribute(name="HashDossie")
	public String getCampo05x() {
		if (null == this.campo05x || this.campo05x.isEmpty()) return null;
		return this.campo05x;
	}
	public void setCampo05x(String newCampo05x) { this.campo05x = newCampo05x; }
		
	@XmlElement(name="Participantes")
	public MpParticipantesXml getMpParticipantesXml() { return mpParticipantesXml; }
	public void setMpParticipantesXml(MpParticipantesXml mpParticipantesXml) { this.mpParticipantesXml = mpParticipantesXml; }

	@XmlElement(name="Emolumentos")
	public MpEmolumentosXml getMpEmolumentosXml() { return mpEmolumentosXml; }
	public void setMpEmolumentosXml(MpEmolumentosXml mpEmolumentosXml) { this.mpEmolumentosXml = mpEmolumentosXml; }
	
}