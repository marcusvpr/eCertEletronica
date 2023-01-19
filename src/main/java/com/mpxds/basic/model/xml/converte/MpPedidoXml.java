package com.mpxds.basic.model.xml.converte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="pedido")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"campo01", "campo02", "campo03", "campo04", "campo05", "campo06", "campo07",
		 			  "campo08", "campo09", "campo10", "campo11", "campo12", "campo13", "campo14",
		 			  "campo15", "campo16", "campo17", "campo18", "campo19", "campo20", "campo21",
		 			  "campo22"})
public class MpPedidoXml {
	//
	private String campo01;	
	private String campo02;	
	private String campo03;	
	private String campo04;	
	private String campo05;	
	private String campo06;	
	private String campo07;	
	private String campo08;	
	private String campo09;	
	private String campo10;	
	private String campo11;	
	private String campo12;	
	private String campo13;
	private String campo14;	
	private String campo15;	
	private String campo16;	
	private String campo17;	
	private String campo18;	
	private String campo19;	
	private String campo20;	
	private String campo21;	
	private String campo22;	

	// ---

	public MpPedidoXml() {};

	public MpPedidoXml(String campo01, String campo02, String campo03, String campo04, String campo05,
						  String campo06, String campo07, String campo08, String campo09, String campo10,
						  String campo11, String campo12, String campo13, String campo14, String campo15,
						  String campo16, String campo17, String campo18, String campo19, String campo20,
						  String campo21, String campo22) {
		//
		super();
		
		this.campo01 = campo01;
		this.campo02 = campo02;
		this.campo03 = campo03;
		this.campo04 = campo04;
		this.campo05 = campo05;
		this.campo06 = campo06;
		this.campo07 = campo07;
		this.campo08 = campo08;
		this.campo09 = campo09;
		this.campo10 = campo10;
		this.campo11 = campo11;
		this.campo12 = campo12;
		this.campo13 = campo13;
		this.campo14 = campo14;
		this.campo15 = campo15;
		this.campo16 = campo16;
		this.campo17 = campo17;
		this.campo18 = campo18;
		this.campo19 = campo19;
		this.campo20 = campo20;
		this.campo21 = campo21;
		this.campo22 = campo22;
	}

	@XmlElement(name="operacao")
	public String getCampo01() { 
		if (null == this.campo01 || this.campo01.isEmpty()) return null;
		return this.campo01;	
	}
	public void setCampo01(String newCampo01) { this.campo01 = newCampo01; }

	@XmlElement(name="certidao_id")
	public String getCampo02() { 
		if (null == this.campo02 || this.campo02.isEmpty()) return null;
		return this.campo02;
	}
	public void setCampo02(String newCampo02) { this.campo02 = newCampo02; }
	
	@XmlElement(name="aleatorio")
	public String getCampo03() { 
		if (null == this.campo03 || this.campo03.isEmpty()) return null;
		return this.campo03;
	}
	public void setCampo03(String newCampo03) { this.campo03 = newCampo03; }

	@XmlElement(name="codigo_caixa")
	public String getCampo04() { 
		if (null == this.campo04 || this.campo04.isEmpty()) return null;
		return this.campo04; 
	}
	public void setCampo04(String newCampo04) { this.campo04 = newCampo04; }

	@XmlElement(name="pedido_central")
	public String getCampo05() { 
		if (null == this.campo05 || this.campo05.isEmpty()) return null;
		return this.campo05;
	}
	public void setCampo05(String newCampo05) { this.campo05 = newCampo05; }

	@XmlElement(name="data_pedido")
	public String getCampo06() { 
		if (null == this.campo06 || this.campo06.isEmpty()) return null;
		return this.campo06;
	}
	public void setCampo06(String newCampo06) { this.campo06 = newCampo06; }

	@XmlElement(name="codigo_certidao")
	public String getCampo07() { 
		if (null == this.campo07 || this.campo07.isEmpty()) return null;
		return this.campo07;
	}
	public void setCampo07(String newCampo07) { this.campo07 = newCampo07; }

	@XmlElement(name="nome_certidao")
	public String getCampo08() { 
		if (null == this.campo08 || this.campo08.isEmpty()) return null;
		return this.campo08;
	}
	public void setCampo08(String newCampo08) { this.campo08 = newCampo08; }

	@XmlElement(name="periodo")
	public String getCampo09() { 
		if (null == this.campo09 || this.campo09.isEmpty()) return null;
		return this.campo09;
	}
	public void setCampo09(String newCampo09) { this.campo09 = newCampo09; }
	
	@XmlElement(name="finalidade")
	public String getCampo10() { 
		if (null == this.campo10 || this.campo10.isEmpty()) return null;
		return this.campo10; 
	}
	public void setCampo10(String newCampo10) { this.campo10 = newCampo10; }
	
	@XmlElement(name="nome_pesquisado")
	public String getCampo11() { 
		if (null == this.campo11 || this.campo11.isEmpty()) return null;
		return this.campo11;
	}
	public void setCampo11(String newCampo11) { this.campo11 = newCampo11; }
	
	@XmlElement(name="cpf_cnpj")
	public String getCampo12() { 
		if (null == this.campo12 || this.campo12.isEmpty()) return null;
		return this.campo12;
	}
	public void setCampo12(String newCampo12) { this.campo12 = newCampo12; }
	
	@XmlElement(name="valor_emolumentos")
	public String getCampo13() { 
		if (null == this.campo13 || this.campo13.isEmpty()) return null;
		return this.campo13;
	}
	public void setCampo13(String newCampo13) { this.campo13 = newCampo13; }
	
	@XmlElement(name="valor_fetj")
	public String getCampo14() { 
		if (null == this.campo14 || this.campo14.isEmpty()) return null;
		return this.campo14;
	}
	public void setCampo14(String newCampo14) { this.campo14 = newCampo14; }
	
	@XmlElement(name="valor_funperj")
	public String getCampo15() { 
		if (null == this.campo15 || this.campo15.isEmpty()) return null;
		return this.campo15;
	}
	public void setCampo15(String newCampo15) { this.campo15 = newCampo15; }
	
	@XmlElement(name="valor_fundperj")
	public String getCampo16() { 
		if (null == this.campo16 || this.campo16.isEmpty()) return null;
		return this.campo16;
	}
	public void setCampo16(String newCampo16) { this.campo16 = newCampo16; }
	
	@XmlElement(name="valor_funarpen")
	public String getCampo17() { 
		if (null == this.campo17 || this.campo17.isEmpty()) return null;
		return this.campo17;
	}
	public void setCampo17(String newCampo17) { this.campo17 = newCampo17; }
	
	@XmlElement(name="valor_ressag")
	public String getCampo18() { 
		if (null == this.campo18 || this.campo18.isEmpty()) return null;
		return this.campo18;
	}
	public void setCampo18(String newCampo18) { this.campo18 = newCampo18; }
	
	@XmlElement(name="valor_iss")
	public String getCampo19() { 
		if (null == this.campo19 || this.campo19.isEmpty()) return null;
		return this.campo19;
	}
	public void setCampo19(String newCampo19) { this.campo19 = newCampo19; }
	
	@XmlElement(name="valor_central")
	public String getCampo20() { 
		if (null == this.campo20 || this.campo20.isEmpty()) return null;
		return this.campo20;
	}
	public void setCampo20(String newCampo20) { this.campo20 = newCampo20; }
	
	@XmlElement(name="valor_reserva")
	public String getCampo21() { 
		if (null == this.campo21 || this.campo21.isEmpty()) return null;
		return this.campo21; 
	}
	public void setCampo21(String newCampo21) { this.campo21 = newCampo21; }
	
	@XmlElement(name="data_entrega")
	public String getCampo22() { 
		if (null == this.campo22 || this.campo22.isEmpty()) return null;
		return this.campo22; 
	}
	public void setCampo22(String newCampo22) { this.campo22 = newCampo22; }
	
}