package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Remessa")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "codServico", "tipoOperacao", "software", "ambiente"})
public class MpRemessaXml {
	//
	private String id;	
	private String codServico;	
	private String tipoOperacao;	
	private String software;	
	private String ambiente;	

	// ---

	public MpRemessaXml() {};

	public MpRemessaXml(String id, String codServico, String tipoOperacao, String software, String ambiente) {
		//
		super();
		
		this.id = id;
		this.codServico = codServico;
		this.tipoOperacao = tipoOperacao;
		this.software = software;
		this.ambiente = ambiente;
	}

	@XmlAttribute(name="Id")
	public String getId() { 
		if (null == this.id || this.id.isEmpty()) return null;
		return this.id; 
	}
	public void setId(String newId) { this.id = newId; }

	@XmlAttribute(name="CodServico")
	public String getCodServico() {
		if (null == this.codServico || this.codServico.isEmpty()) return null;
		return this.codServico;
	}
	public void setCodServico(String newCodServico) { this.codServico = newCodServico; }

	@XmlAttribute(name="TipoOperacao")
	public String getTipoOperacao() {
		if (null == this.tipoOperacao || this.tipoOperacao.isEmpty()) return null;
		return this.tipoOperacao;
	}
	public void setTipoOperacao(String newTipoOperacao) { this.tipoOperacao = newTipoOperacao; }

	@XmlAttribute(name="Software")
	public String getSoftware() { 
		if (null == this.software || this.software.isEmpty()) return null;
		return this.software;
	}
	public void setSoftware(String newSoftware) { this.software = newSoftware; }

	@XmlAttribute(name="Ambiente")
	public String getAmbiente() { 
		if (null == this.ambiente || this.ambiente.isEmpty()) return null;
		return this.ambiente;
	}
	public void setAmbiente(String newAmbiente) { this.ambiente = newAmbiente; }
	
}