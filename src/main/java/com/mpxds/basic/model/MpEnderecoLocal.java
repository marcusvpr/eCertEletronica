package com.mpxds.basic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.mpxds.basic.model.enums.MpEstadoUF;

@Embeddable
public class MpEnderecoLocal implements Serializable {
	//
	private static final long serialVersionUID = 1L;

	@Column(name = "local_logradouro", nullable = false, length = 150)
	private String logradouro;

	@Column(name = "local_numero", nullable = false, length = 20)
	private String numero;

	@Size(max = 150)
	@Column(name = "local_complemento", length = 150)
	private String complemento;
	
	@ManyToOne
	@JoinColumn(name = "mpCidade_id")
	private MpTabelaInterna mpCidade; // TAB_0004
	
	@Column(name = "local_bairro", nullable = false, length = 100)
	private String bairro;
	
	@Column(name = "local_cep", nullable = false, length = 9)
	private String cep;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 2)
	private MpEstadoUF mpEstadoUF;

	// ---
	
	public String getLogradouro() {	return logradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

	public String getNumero() { return numero; }
	public void setNumero(String numero) { this.numero = numero; }

	public String getComplemento() { return complemento; }
	public void setComplemento(String complemento) { this.complemento = complemento; }

	public MpTabelaInterna getMpCidade() { return mpCidade; }
	public void setMpCidade(MpTabelaInterna mpCidade) { this.mpCidade = mpCidade; }

	public String getBairro() { return bairro; }
	public void setBairro(String bairro) { this.bairro = bairro; }

	public String getCep() { return cep; }
	public void setCep(String cep) { this.cep = cep; }

	public MpEstadoUF getMpEstadoUF() { return mpEstadoUF; }
	public void setMpEstadoUF(MpEstadoUF mpEstadoUF) { this.mpEstadoUF = mpEstadoUF; }

}
