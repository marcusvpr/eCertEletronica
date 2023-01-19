package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="DistribuicaoExtrajudicial")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campoA1", "campoA2",  "campo01", "campo02", "campo03", "campo04", "campo05", "campo06", "campo07",
		 			  "campo08", "campo08a", "campo09", "campo10", "campo11",  "mpParticipantesXml", "mpEmolumentosXml"})
public class MpDistribuicaoExtrajudicialXml {
	//
	private String campoA1;	
	private String campoA2;	
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private String campo04;	
	private String campo05;	
	private String campo06;	
	private String campo07;	
	private String campo08;	
	private String campo08a;	
	private String campo09;	
	private String campo10;	
	private String campo11;	
	private MpParticipantesXml mpParticipantesXml;
	private MpEmolumentosXml mpEmolumentosXml;

	// ---

	public MpDistribuicaoExtrajudicialXml() {};

	public MpDistribuicaoExtrajudicialXml(String campoA1, String campoA2,
										  String campo01, String campo02, String campo03, String campo04,  String campo05,
										  String campo06, String campo07, String campo08, String campo08a, String campo09, 
										  String campo10, String campo11,
										  MpParticipantesXml mpParticipantesXml, MpEmolumentosXml mpEmolumentosXml) {
		//
		super();
		
		this.campoA1 = campoA1;
		this.campoA2 = campoA2;
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
		this.campo04 = campo04;
		this.campo05 = campo05;
		this.campo06 = campo06;
		this.campo07 = campo07;
		this.campo08 = campo08;
		this.campo08a = campo08a;
		this.campo09 = campo09;
		this.campo10 = campo10;
		this.campo11 = campo11;
		this.mpParticipantesXml = mpParticipantesXml;
		this.mpEmolumentosXml = mpEmolumentosXml;
	}

	@XmlAttribute(name="DataPratica")
	public String getCampoA1() {
		if (null == this.campoA1 || this.campoA1.isEmpty()) return null;
		return this.campoA1; 
	}
	public void setCampoA1(String newCampoA1) { this.campoA1 = newCampoA1; }

	@XmlAttribute(name="Selo")
	public String getCampoA2() {
		if (null == this.campoA2 || this.campoA2.isEmpty()) return null;
		return this.campoA2; 
	}
	public void setCampoA2(String newCampoA2) { this.campoA2 = newCampoA2; }

	@XmlAttribute(name="Aleatorio")
	public String getCampo01() { 
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01;
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlAttribute(name="CodServicoAtoOriginal")
	public String getCampo02() {
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02;
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }
	
	@XmlAttribute(name="SeloAtoOriginal")
	public String getCampo03() {
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }

	@XmlAttribute(name="AleatorioAtoOriginal")
	public String getCampo04() { 
		if (null == this.campo04 || this.campo04.isEmpty()) return null;
		return this.campo04; 
	}
	public void setCampo04(String newCampo04) { this.campo04 = newCampo04; }

	@XmlAttribute(name="TipoAto")
	public String getCampo05() { 
		if (null == this.campo05 || this.campo05.isEmpty()) return null;
		return this.campo05; 
	}
	public void setCampo05(String newCampo05) { this.campo05 = newCampo05; }

	@XmlAttribute(name="TipoDistribuicao")
	public String getCampo06() { 
		if (null == this.campo06 || this.campo06.isEmpty()) return null;
		return this.campo06; 
	}
	public void setCampo06(String newCampo06) { this.campo06 = newCampo06; }

	@XmlAttribute(name="Assunto")
	public String getCampo07() { 
		if (null == this.campo07 || this.campo07.isEmpty()) return null;
		return this.campo07; 
	}
	public void setCampo07(String newCampo07) { this.campo07 = newCampo07; }

	@XmlAttribute(name="Livro")
	public String getCampo08a() { 
		if (null == this.campo08a || this.campo08a.isEmpty()) return null;
		return this.campo08a; 
	}
	public void setCampo08a(String newCampo08a) { this.campo08a = newCampo08a; }

	@XmlAttribute(name="DescricaoLivro")
	public String getCampo08() { 
		if (null == this.campo08 || this.campo08.isEmpty()) return null;
		return this.campo08;
	}
	public void setCampo08(String newCampo08) { this.campo08 = newCampo08; }

	@XmlAttribute(name="NumeroRegistro")
	public String getCampo09() {
		if (null == this.campo09 || this.campo09.isEmpty()) return null;
		return this.campo09; 
	}
	public void setCampo09(String newCampo09) { this.campo09 = newCampo09; }
	
	@XmlAttribute(name="DescricaoAto")
	public String getCampo10() { 
		if (null == this.campo10 || this.campo10.isEmpty()) return null;
		return this.campo10; 
	}
	public void setCampo10(String newCampo10) { this.campo10 = newCampo10; }

	@XmlAttribute(name="RequerimentoCancelamento")
	public String getCampo11() {
		if (null == this.campo11 || this.campo11.isEmpty()) return null;
		return this.campo11; 
	}
	public void setCampo11(String newCampo11) { this.campo09 = newCampo11; }
	
	@XmlElement(name="Participantes")
	public MpParticipantesXml getMpParticipantesXml() { return mpParticipantesXml; }
	public void setMpParticipantesXml(MpParticipantesXml mpParticipantesXml) { this.mpParticipantesXml = mpParticipantesXml; }

	@XmlElement(name="Emolumentos")
	public MpEmolumentosXml getMpEmolumentosXml() { return mpEmolumentosXml; }
	public void setMpEmolumentosXml(MpEmolumentosXml mpEmolumentosXml) { this.mpEmolumentosXml = mpEmolumentosXml; }
	
}