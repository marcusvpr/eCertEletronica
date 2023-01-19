package com.mpxds.basic.model;

//import javax.persistence.Access;
//import javax.persistence.AccessType;

//import java.util.ArrayList;
//import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.mpxds.basic.model.enums.MpTipoTabelaInterna;

@Entity
@Table(name = "mp_tabela_interna")
public class MpTabelaInterna extends MpEntity {
	//
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Por favor, informe o Tipo Tabela")
	@Column(name = "mpTipo_tabela_interna", nullable = false)
	@Enumerated(EnumType.STRING)
	private MpTipoTabelaInterna mpTipoTabelaInterna;
	
	@Column(nullable = false)
	private String codigo;

	private String descricao;

	private Boolean indPai = false;;
	private Boolean indFilha = false;
	
	@OneToOne
	@JoinColumn(name = "mpPai_Id")
	private MpTabelaInterna mpPai;

	// private List<MpTabelaInterna> mpPais = new ArrayList<>();

	// ---

	public MpTabelaInterna() {
		//
	}

	public MpTabelaInterna(Long id, MpTipoTabelaInterna mpTipoTabelaInterna, String codigo, String descricao) {
		//
		super();

		this.id = id;
		this.mpTipoTabelaInterna = mpTipoTabelaInterna;
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public MpTipoTabelaInterna getMpTipoTabelaInterna() { return this.mpTipoTabelaInterna; }
	public void setMpTipoTabelaInterna(MpTipoTabelaInterna newMpTipoTabelaInterna) { 
														this.mpTipoTabelaInterna = newMpTipoTabelaInterna; }

	public String getCodigo() { return this.codigo; }
	public void setCodigo(String newCodigo) { this.codigo = newCodigo; }

	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

	public Boolean getIndPai() { return this.indPai; }
	public void setIndPai(Boolean newIndPai) { this.indPai = newIndPai; }

	public Boolean getIndFilha() { return this.indFilha; }
	public void setIndFilha(Boolean newIndFilha) { this.indFilha = newIndFilha; }

	public MpTabelaInterna getMpPai() { return this.mpPai; }
	public void setMpPai(MpTabelaInterna newMpPai) { this.mpPai = newMpPai; }

	// @OneToMany(mappedBy = "mpTabelaInterna", cascade = CascadeType.ALL)
	// public List<MpTabelaInterna> getMpPais() { return mpPais; }
	// public void setMpPais(List<MpTabelaInterna> mpPais) { this.mpPais =
	// mpPais; }

	// ---

	@Transient
	public String getDescricaoNumero() {
		if (null == this.descricao)
			this.descricao = "";
		//
		return this.descricao.trim() + " ( " + this.mpTipoTabelaInterna.getDescricao() + " )";
	}

}