package com.mpxds.basic.model.xml.converte;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Imoveis")
public class MpImoveisXml {
	//
	private List<MpImovelUrbanoXml> mpImovelUrbanoXmls = new ArrayList<MpImovelUrbanoXml>();

	// ---

	public MpImoveisXml() {};

	public MpImoveisXml(List<MpImovelUrbanoXml> mpImovelUrbanoXmls) {
		//
		super();
		
		this.mpImovelUrbanoXmls = mpImovelUrbanoXmls;
	}

	@XmlElement(name="ImovelUrbano")
	public List<MpImovelUrbanoXml> getMpImovelUrbanoXmls() { return this.mpImovelUrbanoXmls; }
	public void setMpImovelUrbanoXmls(List<MpImovelUrbanoXml> newMpImovelUrbanoXmls) {
																			this.mpImovelUrbanoXmls = newMpImovelUrbanoXmls; }

}