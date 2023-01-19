package com.mpxds.basic.model.xml.converte;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Participantes")
public class MpParticipantesXml {
	//
	private List<MpParticipanteXml> mpParticipanteXmls = new ArrayList<MpParticipanteXml>();
	private List<MpPessoaFisicaXml> mpPessoaFisicaXmls = new ArrayList<MpPessoaFisicaXml>();

	// ---

	public MpParticipantesXml() {};

	public MpParticipantesXml(List<MpParticipanteXml> mpParticipanteXmls, List<MpPessoaFisicaXml> mpPessoaFisicaXmls) {
		//
		super();
		
		this.mpParticipanteXmls = mpParticipanteXmls;
	}

	@XmlElement(name="Participante")
	public List<MpParticipanteXml> getMpParticipanteXmls() { return this.mpParticipanteXmls; }
	public void setMpParticipanteXmls(List<MpParticipanteXml> newMpParticipanteXmls) {
																			this.mpParticipanteXmls = newMpParticipanteXmls; }

	@XmlElement(name="PessoaFisica")
	public List<MpPessoaFisicaXml> getMpPessoaFisicaXmls() { return this.mpPessoaFisicaXmls; }
	public void setMpPessoaFisicaXmls(List<MpPessoaFisicaXml> newMpPessoaFisicaXmls) {
																			this.mpPessoaFisicaXmls = newMpPessoaFisicaXmls; }

}