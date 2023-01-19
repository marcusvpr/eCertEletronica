package com.mpxds.basic.model.dto;

public class MpTabelaInternaDTO {
	//
	private String tipo;	
	private String codigo;
	private String descricao;

	// ---

	public MpTabelaInternaDTO(String tipo, String codigo, String descricao) {
		//
		this.tipo = tipo;
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getTipo() { return this.tipo; }
	public void setTipo(String newTipo) { this.tipo = newTipo; }

	public String getCodigo() { return this.codigo; }
	public void setCodigo(String newCodigo) { this.codigo = newCodigo; }

	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

}