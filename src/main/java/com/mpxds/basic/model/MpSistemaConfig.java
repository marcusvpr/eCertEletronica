package com.mpxds.basic.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.mpxds.basic.model.enums.MpTipoCampo;

@Entity
@Table(name="mp_sistema_config", indexes = {
		@Index(name = "index_mp_sistema_config_param", columnList = "parametro", unique = true)})
public class MpSistemaConfig extends MpEntity {
	private static final long serialVersionUID = 1L;
	
	private String parametro; 
	private String descricao;
	
	private MpTipoCampo mpTipoCampo = MpTipoCampo.TEXTO ;
	
	private String valorT = "";
	private BigDecimal valorD = BigDecimal.ZERO;
	private Boolean indValor = false;

	private String objeto;

	// ---
	
	public MpSistemaConfig() {
	  super();
	}

	public MpSistemaConfig(String parametro
						, String descricao
						, MpTipoCampo mpTipoCampo
						, String valorT
						, BigDecimal valorD
						, Boolean indValor
						, String objeto) {
		this.parametro = parametro;
		this.descricao = descricao;
		this.mpTipoCampo = mpTipoCampo;
		this.valorT = valorT;
		this.valorD = valorD;
		this.indValor = indValor;
		this.objeto = objeto;
	}

	@NotNull(message = "Por favor, informe o PARAMÊTRO")
	@Column(nullable = false, length = 100)
	public String getParametro() { return this.parametro; }
	public void setParametro(String newParametro) { this.parametro = newParametro; }

	@NotNull(message = "Por favor, informe o DESCRIÇÃO")
	public String getDescricao() { return this.descricao; }
	public void setDescricao(String newDescricao) { this.descricao = newDescricao; }

	@NotNull(message = "Por favor, informe o TIPO CAMPO")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public MpTipoCampo getMpTipoCampo() { return this.mpTipoCampo; }
	public void setMpTipoCampo(MpTipoCampo newMpTipoCampo) { this.mpTipoCampo = newMpTipoCampo; }

	@NotNull(message = "Por favor, informe o VALOR Texto")
	@Column(nullable = false, length = 1000)
	public String getValorT() { return this.valorT; }
	public void setValorT(String newValorT) { this.valorT = newValorT; }

	@NotNull(message = "Por favor, informe o VALOR Número")
	@Column(nullable = false)
	public BigDecimal getValorD() { return this.valorD; }
	public void setValorD(BigDecimal newValorD) { this.valorD = newValorD; }

	@NotNull(message = "Por favor, informe o VALOR Indicador")
	@Column(nullable = false)
	public Boolean getIndValor() { return this.indValor; }
	public void setIndValor(Boolean newIndValor) { this.indValor = newIndValor; }

	@Column(nullable = true, length = 30)
	public String getObjeto() { return this.objeto; }
	public void setObjeto(String newObjeto) { this.objeto = newObjeto; }

	@Transient
	public String getValor() {
		if (this.mpTipoCampo.equals(MpTipoCampo.TEXTO))
			return this.valorT.trim();
		if (this.mpTipoCampo.equals(MpTipoCampo.DECIMAL))
			return "" + this.valorD;

		return "" + this.indValor;
	}

	@Transient
	public String getParametroValor() {
		if (this.mpTipoCampo.equals(MpTipoCampo.TEXTO))
			return this.parametro.trim() + " / " + this.valorT.trim();

		if (this.mpTipoCampo.equals(MpTipoCampo.DECIMAL))
			return this.parametro.trim() + " / " + this.valorD;

		return this.parametro.trim() + " / " + this.indValor;
	}

}
