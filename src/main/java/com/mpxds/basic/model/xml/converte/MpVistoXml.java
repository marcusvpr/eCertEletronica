package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Visto")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03", "campo04", "campo05", "campo06", "campo07",  
		  			  "campo08", "campo09", "campo10", "campo11", "campo12", "campo13", "campo14",
		 			  "mpParticipantesXml", "mpEmolumentosXml"})
public class MpVistoXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private String campo04;	
	private String campo05;	
	private String campo06;	
	private String campo07;	
	private String campo08;	
	private String campo09;	
	private String campo10;	
	private String campo11;	
	private String campo12;	
	private String campo13;
	private String campo14;	
	private MpParticipantesXml mpParticipantesXml;
	private MpEmolumentosXml mpEmolumentosXml;

	// ---

	public MpVistoXml() {};

	public MpVistoXml( String campo01, String campo02, String campo03, String campo04, String campo05,
					   String campo06, String campo07, String campo08, String campo09, String campo10,
					   String campo11, String campo12, String campo13, String campo14,
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
		this.campo08 = campo08;
		this.campo09 = campo09;
		this.campo10 = campo10;
		this.campo11 = campo11;
		this.campo12 = campo12;
		this.campo13 = campo13;
		this.campo14 = campo14;
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

	@XmlAttribute(name="CodServicoAtoOriginal")
	public String getCampo04() {
		if (null == this.campo04 || this.campo04.isEmpty()) return null;
		return this.campo04;
	}
	public void setCampo04(String newCampo04) { this.campo04 = newCampo04; }
	
	@XmlAttribute(name="SeloAtoOriginal")
	public String getCampo05() {
		if (null == this.campo05 || this.campo05.isEmpty()) return null;
		return this.campo05;
	}
	public void setCampo05(String newCampo05) { this.campo05 = newCampo05; }

	@XmlAttribute(name="AleatorioAtoOriginal")
	public String getCampo06() { 
		if (null == this.campo06 || this.campo06.isEmpty()) return null;
		return this.campo06; 
	}
	public void setCampo06(String newCampo06) { this.campo06 = newCampo06; }

	@XmlAttribute(name="CodServicoAtoReferencia")
	public String getCampo07() { 
		if (null == this.campo07 || this.campo07.isEmpty()) return null;
		return this.campo07; 
	}
	public void setCampo07(String newCampo07) { this.campo07 = newCampo07; }

	@XmlElement(name="SeloAtoReferencia")
	public String getCampo08() { 
		if (null == this.campo08 || this.campo08.isEmpty()) return null;
		return this.campo08;
	}
	public void setCampo08(String newCampo08) { this.campo08 = newCampo08; }

	@XmlElement(name="AleatorioAtoReferencia")
	public String getCampo09() { 
		if (null == this.campo09 || this.campo09.isEmpty()) return null;
		return this.campo09;
	}
	public void setCampo09(String newCampo09) { this.campo09 = newCampo09; }
	
	@XmlElement(name="TipoCertidao")
	public String getCampo10() { 
		if (null == this.campo10 || this.campo10.isEmpty()) return null;
		return this.campo10; 
	}
	public void setCampo10(String newCampo10) { this.campo10 = newCampo10; }
	
	@XmlElement(name="NumeroAto")
	public String getCampo11() { 
		if (null == this.campo11 || this.campo11.isEmpty()) return null;
		return this.campo11;
	}
	public void setCampo11(String newCampo11) { this.campo11 = newCampo11; }
	
	@XmlElement(name="QuantidadeDistribuicao")
	public String getCampo12() { 
		if (null == this.campo12 || this.campo12.isEmpty()) return null;
		return this.campo12;
	}
	public void setCampo12(String newCampo12) { this.campo12 = newCampo12; }
	
	@XmlElement(name="QtdFolhasExcedentes")
	public String getCampo13() { 
		if (null == this.campo13 || this.campo13.isEmpty()) return null;
		return this.campo13;
	}
	public void setCampo13(String newCampo13) { this.campo13 = newCampo13; }
	
	@XmlElement(name="Revalidada")
	public String getCampo14() { 
		if (null == this.campo14 || this.campo14.isEmpty()) return null;
		return this.campo14;
	}
	public void setCampo14(String newCampo14) { this.campo14 = newCampo14; }
		
	@XmlElement(name="Participantes")
	public MpParticipantesXml getMpParticipantesXml() { return mpParticipantesXml; }
	public void setMpParticipantesXml(MpParticipantesXml mpParticipantesXml) { this.mpParticipantesXml = mpParticipantesXml; }

	@XmlElement(name="Emolumentos")
	public MpEmolumentosXml getMpEmolumentosXml() { return mpEmolumentosXml; }
	public void setMpEmolumentosXml(MpEmolumentosXml mpEmolumentosXml) { this.mpEmolumentosXml = mpEmolumentosXml; }
	
}