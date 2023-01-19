package com.mpxds.basic.model.xml.converte;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AtosVinculados")
public class MpAtosVinculadosXml {
	//
	private List<MpAtoVinculadoXml> mpAtoVinculadoXmls = new ArrayList<MpAtoVinculadoXml>();

	// ---

	public MpAtosVinculadosXml() {};

	public MpAtosVinculadosXml(List<MpAtoVinculadoXml> mpAtoVinculadoXmls) {
		//
		super();
		
		this.mpAtoVinculadoXmls = mpAtoVinculadoXmls;
	}

	@XmlElement(name="AtoVinculado")
	public List<MpAtoVinculadoXml> getMpAtoVinculadoXmls() { return this.mpAtoVinculadoXmls; }
	public void setMpAtoVinculadoXmls(List<MpAtoVinculadoXml> newMpAtoVinculadoXmls) {
																			this.mpAtoVinculadoXmls = newMpAtoVinculadoXmls; }

}