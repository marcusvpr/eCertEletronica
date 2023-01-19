package com.mpxds.basic.model.enums;

public enum MpTipoTabelaInterna {
	//
	TB_INDICACAO("Tabela Indicação", "c", 50, false, null),
	TB_ORGAO_EXPEDIDOR("Tabela Orgão Expedidor", "c", 50, false, null),
	TB_CIDADE("Tabela Cidade", "c", 50, false, null), // Uf -> EnumSeparado !
	TB_TIPO_TELEFONE("Tabela Tipo Telefone", "c", 50, false, null),
	TB_BANCO("Tabela Banco", "c", 50, false, null),
	TB_TIPO_RG("Tabela Tipo RG", "c", 50, false, null),
	TB_PAGAMENTO_TIPO("Tabela Pagamento Tipo", "c", 50, false, null),
	TB_STATUS("Tabela Status", "c", 50, false, null);
	
	private final String descricao;
	private final String formato; // c=character n=numeric
	private final Integer tamanho;
	private final Boolean indPai;
	private final String filha;
	
	// ---

	MpTipoTabelaInterna(String descricao, String formato, Integer tamanho, Boolean indPai, String filha) {
		//
		this.descricao = descricao;
		this.formato = formato;
		this.tamanho = tamanho;
		this.indPai = indPai;
		this.filha = filha;
	}
	
//    public static MpTipoTabelaInternaSJ capturaTipoTabela(String tabelaX) {
//    	//
//    	MpTipoTabelaInternaSJ mpTipoTabelaInternaSJX = null; // Default
//    	
//        for (MpTipoTabelaInternaSJ itemX : MpTipoTabelaInternaSJ.values()) {
//            if (itemX.getTabela().equals(tabelaX)) {
//            	mpTipoTabelaInternaSJX = itemX;
//                break;
//            }
//        }
//        //
//        return mpTipoTabelaInternaSJX;
//    }	
	
	// ---
	
	public String getDescricao() { return descricao; }
	
	public String getFormato() { return formato; }
	
	public Integer getTamanho() { return tamanho; }
	
	public Boolean getIndPai() { return indPai; }
	
	public String getFilha() { return filha; }
	
}