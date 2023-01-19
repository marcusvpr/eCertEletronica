package com.mpxds.basic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name="mp_certidao_log", indexes = {
		@Index(name = "index_mp_certidao_log_data_mov", columnList = "data_movimento")})
public class MpCertidaoLog extends MpEntity {
	//
	private static final long serialVersionUID = 1L;

    @Column(name = "data_movimento", nullable = false, length = 10)
	private String dataMovimento;  

	private Integer contBalcao;
	private Integer contPedido;
	private Integer contEtiqueta;
	
	// ---
	
	public MpCertidaoLog() {
		//
	}

  	public MpCertidaoLog(Long id, String dataMovimento, Integer contBalcao, Integer contPedido,
  							Integer contEtiqueta) {
  		//
  		super();
  		
  		this.id = id;
  		this.dataMovimento = dataMovimento;
  		this.contBalcao = contBalcao;
  		this.contPedido = contPedido;
  		this.contEtiqueta = contEtiqueta;
  	}
 
  	public String getDataMovimento() { return this.dataMovimento; }
  	public void setDataMovimento(String newDataMovimento) { this.dataMovimento = newDataMovimento; }
  	
	public Integer getContBalcao() { return this.contBalcao; }
	public void setContBalcao(Integer newcontBalcao) { this.contBalcao = newcontBalcao; }
  	
	public Integer getContPedido() { return this.contPedido; }
	public void setContPedido(Integer newcontPedido) { this.contPedido = newcontPedido; }
  	
	public Integer getContEtiqueta() { return this.contEtiqueta; }
	public void setContEtiqueta(Integer newcontEtiqueta) { this.contPedido = newcontEtiqueta; }

}
