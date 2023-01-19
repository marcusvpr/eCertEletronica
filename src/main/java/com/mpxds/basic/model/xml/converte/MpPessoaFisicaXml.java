package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PessoaFisica")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03"})
public class MpPessoaFisicaXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	

	// ---

	public MpPessoaFisicaXml() {};

	public MpPessoaFisicaXml(String campo01, String campo02, String campo03) {
		//
		super();
		
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
	}

	@XmlAttribute(name="Tipo")
	public String getCampo01() { 
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01;
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlAttribute(name="Nome")
	public String getCampo02() { 
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02;
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }
	
	@XmlAttribute(name="NumeroIdentidade")
	public String getCampo03() { 
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }
	
}