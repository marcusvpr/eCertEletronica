package com.mpxds.basic.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mp_dolar", indexes = {
		@Index(name = "index_mp_dolar_data_mov", columnList = "data_movimento", unique = true)})
public class MpDolar extends MpEntity {
	//
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
    @Column(name = "data_movimento", nullable = false, length = 10)
	private Date dataMovimento;  

	@NotNull(message = "Valor é obrigatório")
	@Column(nullable = false, precision = 10, scale = 3)
	private BigDecimal valor;
	
	@Column(nullable = true, length = 255)
	private String observacao;

	// ---
	
	public MpDolar() {
		//
	}

  	public MpDolar(Long id, Date dataMovimento, BigDecimal valor, String observacao) {
  		//
  		super();
  		
  		this.id = id;
  		this.dataMovimento = dataMovimento;
  		this.valor = valor;
  		this.observacao = observacao;
  	}
 
  	public Date getDataMovimento() { return this.dataMovimento; }
  	public void setDataMovimento(Date newDataMovimento) { this.dataMovimento = newDataMovimento; }
  	
	public BigDecimal getValor() { return this.valor; }
	public void setValor(BigDecimal newValor) { this.valor = newValor; }
  	
	public String getObservacao() { return this.observacao; }
	public void setObservacao(String newObservacao) { this.observacao = newObservacao; }

	//
	
	@Transient
  	public String getDataMovimentoSDF() {
  		//
		if (null==this.dataMovimento) return "__/__/____";
		
  		SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
  		
  		return sdfDMY.format(this.dataMovimento); 
  	}
	
}
