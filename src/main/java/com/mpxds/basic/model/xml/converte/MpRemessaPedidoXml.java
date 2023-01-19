package com.mpxds.basic.model.xml.converte;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="remessa")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03", "campo04", "campo05", "campo06", "mpPedidoXmls"})
public class MpRemessaPedidoXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private String campo04;	
	private String campo05;	
	private String campo06;	

	private List<MpPedidoXml> mpPedidoXmls = new ArrayList<MpPedidoXml>();	

	// ---

	public MpRemessaPedidoXml() {};

	public MpRemessaPedidoXml(String campo01, String campo02, String campo03, String campo04, String campo05, String campo06,
								List<MpPedidoXml> mpPedidoXmls) {
		//
		super();
		
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
		this.campo04 = campo04;
		this.campo05 = campo05;
		this.campo06 = campo06;
		this.mpPedidoXmls = mpPedidoXmls;
	}

	@XmlAttribute(name="versao")
	public String getCampo01() { 
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01;
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlAttribute(name="codigo_cartorio")
	public String getCampo02() { 
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02; 
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }
	
	@XmlAttribute(name="numero_remessa")
	public String getCampo03() { 
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }

	@XmlAttribute(name="data_remessa")
	public String getCampo04() { 
		if (null == this.campo04 || this.campo04.isEmpty()) return null;
		return this.campo04;
	}
	public void setCampo04(String newCampo04) { this.campo04 = newCampo04; }

	@XmlAttribute(name="hora_remessa")
	public String getCampo05() { 
		if (null == this.campo05 || this.campo05.isEmpty()) return null;
		return this.campo05;
	}
	public void setCampo05(String newCampo05) { this.campo05 = newCampo05; }
	
	@XmlAttribute(name="qtd_pedidos")
	public String getCampo06() { 
		if (null == this.campo06 || this.campo06.isEmpty()) return null;
		return this.campo06;
	}
	public void setCampo06(String newCampo06) { this.campo06 = newCampo06; }
	
	@XmlElement(name="pedido")
	public List<MpPedidoXml> getMpPedidoXmls() { return this.mpPedidoXmls; }
	public void setMpPedidoXmls(List<MpPedidoXml> newMpPedidoXmls) { this.mpPedidoXmls = newMpPedidoXmls; }

}