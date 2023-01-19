package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="CancelamentoPrenotacao")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03",
		 			  "mpAtosVinculadosXml", "mpEmolumentosXml"})
public class MpCancelamentoPrenotacaoXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private MpAtosVinculadosXml mpAtosVinculadosXml;
	private MpEmolumentosXml mpEmolumentosXml;

	// ---

	public MpCancelamentoPrenotacaoXml() {};

	public MpCancelamentoPrenotacaoXml( String campo01, String campo02, String campo03, 
					   MpAtosVinculadosXml mpAtosVinculadosXml, MpEmolumentosXml mpEmolumentosXml) {
		//
		super();
		
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
		this.mpAtosVinculadosXml = mpAtosVinculadosXml;
		this.mpEmolumentosXml = mpEmolumentosXml;
	}

	@XmlAttribute(name="DataPratica")
	public String getCampo01() {
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01; 
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlAttribute(name="CCT")
	public String getCampo02() {
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02; 
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }
	
	@XmlElement(name="RequerimentoCancelamento")
	public String getCampo03() { 
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }

	@XmlElement(name="AtosVinculados")
	public MpAtosVinculadosXml getMpAtosVinculadosXml() { return mpAtosVinculadosXml; }
	public void setMpAtosVinculadosXml(MpAtosVinculadosXml mpAtosVinculadosXml) { this.mpAtosVinculadosXml = mpAtosVinculadosXml; }

	@XmlElement(name="Emolumentos")
	public MpEmolumentosXml getMpEmolumentosXml() { return mpEmolumentosXml; }
	public void setMpEmolumentosXml(MpEmolumentosXml mpEmolumentosXml) { this.mpEmolumentosXml = mpEmolumentosXml; }
	
}