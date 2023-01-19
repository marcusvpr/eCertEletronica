package com.mpxds.basic.model.enums;

public enum MpTipoCampo {

	BOOLEAN("Boolean"), 
	DECIMAL("Decimal"), 
	TEXTO("Texto"); 
	
	private String descricao;
	
	// ---
	
	MpTipoCampo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() { return descricao; }
	
}
