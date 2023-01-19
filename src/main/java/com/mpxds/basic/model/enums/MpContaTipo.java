package com.mpxds.basic.model.enums;

public enum MpContaTipo {
	
	CORRENTE("Corrente"),
	POUPANCA("Poupança");
	
	private String descricao;
	
	// ---
	
	MpContaTipo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return this.descricao; }

}