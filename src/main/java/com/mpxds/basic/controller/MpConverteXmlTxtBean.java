package com.mpxds.basic.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mpxds.basic.model.MpSistemaConfig;
import com.mpxds.basic.model.xml.converte.MpAtoVinculadoXml;
import com.mpxds.basic.model.xml.converte.MpAtosVinculadosXml;
import com.mpxds.basic.model.xml.converte.MpCancelamentoPrenotacaoXml;
import com.mpxds.basic.model.xml.converte.MpCertidaoGenericaXml;
import com.mpxds.basic.model.xml.converte.MpCertidaoPrenotacaoXml;
import com.mpxds.basic.model.xml.converte.MpCertidaoXml;
import com.mpxds.basic.model.xml.converte.MpComplementoEmolumentosXml;
import com.mpxds.basic.model.xml.converte.MpDistribuicaoExtrajudicialXml;
import com.mpxds.basic.model.xml.converte.MpEmolumentosXml;
import com.mpxds.basic.model.xml.converte.MpFolhaAdicionalComplementoXml;
import com.mpxds.basic.model.xml.converte.MpImoveisXml;
import com.mpxds.basic.model.xml.converte.MpImovelUrbanoXml;
import com.mpxds.basic.model.xml.converte.MpInformacaoVerbalXml;
import com.mpxds.basic.model.xml.converte.MpItemEmolumentoXml;
import com.mpxds.basic.model.xml.converte.MpParticipanteXml;
import com.mpxds.basic.model.xml.converte.MpParticipantesXml;
import com.mpxds.basic.model.xml.converte.MpPedidoXml;
import com.mpxds.basic.model.xml.converte.MpPessoaFisicaXml;
import com.mpxds.basic.model.xml.converte.MpRegistroDistribuicaoXml;
import com.mpxds.basic.model.xml.converte.MpRegistroImoveisXml;
import com.mpxds.basic.model.xml.converte.MpRemessaPedidoXml;
import com.mpxds.basic.model.xml.converte.MpRemessaXml;
import com.mpxds.basic.model.xml.converte.MpVistoXml;
import com.mpxds.basic.service.MpSistemaConfigService;
import com.mpxds.basic.util.jsf.MpFacesUtil;

@Named
@ViewScoped
public class MpConverteXmlTxtBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MpSistemaConfigService mpSistemaConfigService;

	private String scPathArquivoXml = ""; 
	private String scPathArquivoTxt = ""; 
	private BigDecimal scNumeroRegistroArquivoTxt = BigDecimal.valueOf(5000) ; 

	// ==============================================

	private String arquivoXmlSelecionado = "";	
	private List<String> arquivoXmlList = new ArrayList<String>();
	private List<String> arquivoXmlSelecionadoList = new ArrayList<String>();
	
	private String arquivoTxtSelecionado = "";	
	private List<String> arquivoTxtList = new ArrayList<String>();
	private List<String> arquivoTxtSelecionadoList = new ArrayList<String>();
	
	private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
//	private Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	//----------------
	
	public void inicializar() {
		//
		// Trata Configuração Sistema ...
		// ==============================
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathConverteArquivoXml");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathConverteArquivoXml' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathArquivoXml = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathConverteArquivoTxt");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathConverteArquivoTxt' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathArquivoTxt = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("numeroRegistoConverteArquivoTxt");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'numeroRegistoConverteArquivoTxt' em 'SistemaConfig' ... não existe ! ( " +
												"Assumido = 5.000 !");
			return;
		} else
			this.scNumeroRegistroArquivoTxt = mpSistemaConfig.getValorD();
		//
	}

	public void sairWS() {
		//
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/dashboard.xhtml");
			
		} catch (IOException e) {
			//
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}	

	public void listaPathArquivoXml() {
		//
    	this.listaPath(new File(this.scPathArquivoXml), "XML");
	}
	
	public void listaPathArquivoTxt() {
		//
    	this.listaPath(new File(this.scPathArquivoTxt), "TXT");
	}
		
	public String listaPath(File filePath, String tipoArquivo) {
		//
		this.arquivoXmlList = new ArrayList<String>();
		this.arquivoTxtList = new ArrayList<String>();
		
//      System.out.println("listaPath  -------------->" + this.scPathEtiqueta);
		
		File[] filesList = filePath.listFiles();
		
	    for (File f : filesList) {
	        if (f.isDirectory() && !f.isHidden()) {
//	            System.out.println("Directoy name is  --------------> " + f.getName());
	            continue; // listaPath(f);
	        }
	        if( f.isFile() ) {
	        	//
	        	if (tipoArquivo.contains("XML")) {
	        		if (f.getName().toLowerCase().contains(".xml"))
	        			this.arquivoXmlList.add(f.getName());
	        	} else
		        	if (tipoArquivo.contains("TXT")) {
		        		if (f.getName().toLowerCase().contains(".1")
		        		||  f.getName().toLowerCase().contains(".2")
		        		||  f.getName().toLowerCase().contains(".3")
		        		||  f.getName().toLowerCase().contains(".4")
		        		||  f.getName().toLowerCase().contains(".5")
		        		||  f.getName().toLowerCase().contains(".6")
		        		||  f.getName().toLowerCase().contains(".7")
		        		||  f.getName().toLowerCase().contains(".8")
		        		||  f.getName().toLowerCase().contains(".9")
			        	||  f.getName().toLowerCase().contains(".0"))
		        			this.arquivoTxtList.add(f.getName());
		        	}
	        }
	    }
	    //
	    return "";
	}
	
	public void listaPathArquivoXmlSel() {
		//
		if (this.arquivoXmlSelecionadoList.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Nenhuma Arquivo XML... selecionado !");
			return;
		}
		//
		String msgArquivoXml = "";
		
		for (String arquivoXmlSel : this.arquivoXmlSelecionadoList) {
			//
			this.arquivoXmlSelecionado = arquivoXmlSel;
			
			this.trataArquivoXml("TXT");
			//
			msgArquivoXml = msgArquivoXml + " " + arquivoXmlSel;
		}
		//
		MpFacesUtil.addInfoMessage("Arquivo(s) XML(s) Selecionado(s)... ! ( " + msgArquivoXml);
	}
	public void listaPathArquivoXmlSelPdf() {
		//
		if (this.arquivoXmlSelecionadoList.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Nenhuma Arquivo XML... selecionado !");
			return;
		}
		//
		String msgArquivoXml = "";
		
		for (String arquivoXmlSel : this.arquivoXmlSelecionadoList) {
			//
			this.arquivoXmlSelecionado = arquivoXmlSel;
			
			this.trataArquivoXml("PDF");
			//
			msgArquivoXml = msgArquivoXml + " " + arquivoXmlSel;
		}
		//
		MpFacesUtil.addInfoMessage("Relatório ... Arquivo(s) XML(s) Selecionado(s)... ! ( " + msgArquivoXml);
	}
	
	public void listaPathArquivoTxtSel() {
		//
		if (this.arquivoTxtSelecionadoList.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Nenhuma Arquivo TXT... selecionado !");
			return;
		}
		//
		String msgArquivoTxt = "";
		
		for (String arquivoTxtSel : this.arquivoTxtSelecionadoList) {
			//
			this.arquivoTxtSelecionado = arquivoTxtSel;
			
			this.trataArquivoTxt();
			//
			msgArquivoTxt = msgArquivoTxt + " " + arquivoTxtSel;
		}
		//
		MpFacesUtil.addInfoMessage("Arquivo(s) TXT(s) Selecionado(s)... ! ( " + msgArquivoTxt);
	}
	
	public void mpListenerArquivoXml(ActionEvent event) {
		//
		this.arquivoXmlSelecionado = (String)event.getComponent().getAttributes().get("arquivoXmlSelecionado");

		System.out.println("MpConverteXmlTxtBean.mpListenerArquivoXml()  --------------> " + this.arquivoXmlSelecionado);
	}		
	public void mpGeraXml() {
		//
		if (null == this.arquivoTxtSelecionado) return;
		//
		this.trataArquivoTxt();
	}
	
	public void mpListenerArquivoTxt(ActionEvent event) {
		//
		this.arquivoTxtSelecionado = (String)event.getComponent().getAttributes().get("arquivoTxtSelecionado");

		System.out.println("MpConverteXmlTxtBean.mpListenerArquivoTxt()  --------------> " + this.arquivoTxtSelecionado);
	}		
	public void mpGeraTxt() {
		//
		if (null == this.arquivoXmlSelecionado) return;
		//
		this.trataArquivoXml("TXT");
	}
	public void mpGeraPdf() {
		//
		if (null == this.arquivoXmlSelecionado) return;
		//
		this.trataArquivoXml("PDF");
	}

	public void trataArquivoXml(String acaoX) {
		//
		if (this.arquivoXmlSelecionado.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Informar arquivo XML !");
			return;
		}
		//
    	File fileX = new File(this.scPathArquivoXml + this.arquivoXmlSelecionado);
    	if (!fileX.exists()) {
    		//
    		MpFacesUtil.addInfoMessage("ARQUIVO XML... Não existe ! ( " + this.scPathArquivoXml + this.arquivoXmlSelecionado);
			return;
    	}
		//
    	if (acaoX.contentEquals("TXT"))
    		MpFacesUtil.addInfoMessage(this.criaArquivoTxt(fileX));
    	else
    		MpFacesUtil.addInfoMessage(this.criaArquivoPdf(fileX));
		//
	}	
	
	public void trataArquivoTxt() {
		//
		if (this.arquivoTxtSelecionado.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Informar arquivo TXT !");
			return;
		}
		//
    	File fileX = new File(this.scPathArquivoTxt + this.arquivoTxtSelecionado);
    	if (!fileX.exists()) {
    		//
    		MpFacesUtil.addInfoMessage("ARQUIVO TXT... Não existe ! ( " + this.scPathArquivoTxt + this.arquivoTxtSelecionado);
			return;
    	}
		//
    	MpFacesUtil.addInfoMessage(this.criaArquivoXml(fileX));
		//
	}	
	
	public String criaArquivoXml(File fileXml) {
		//
		Date dateX = new Date();			
		
		String xmlContent = "";
		
		// Define Arrays(1->Vide.2!) XMLs ...
		List<MpDistribuicaoExtrajudicialXml> mpDistribuicaoExtrajudicialXmls = new ArrayList<MpDistribuicaoExtrajudicialXml>();
		List<MpCertidaoXml> mpCertidaoXmls = new ArrayList<MpCertidaoXml>();
		List<MpVistoXml> mpVistoXmls = new ArrayList<MpVistoXml>(); 
		List<MpFolhaAdicionalComplementoXml> mpFolhaAdicionalComplementoXmls = new ArrayList<MpFolhaAdicionalComplementoXml>();
		List<MpInformacaoVerbalXml> mpInformacaoVerbalXmls = new ArrayList<MpInformacaoVerbalXml>();
		List<MpComplementoEmolumentosXml> mpComplementoEmolumentosXmls = new ArrayList<MpComplementoEmolumentosXml>();
		List<MpCertidaoGenericaXml> mpCertidaoGenericaXmls = new ArrayList<MpCertidaoGenericaXml>();
		List<MpCertidaoPrenotacaoXml> mpCertidaoPrenotacaoXmls = new ArrayList<MpCertidaoPrenotacaoXml>();
		List<MpCancelamentoPrenotacaoXml> mpCancelamentoPrenotacaoXmls = new ArrayList<MpCancelamentoPrenotacaoXml>();

		// Trata leitura arquivo ...
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileXml), "UTF-8"))) {
			//
			Integer contLinha = 0;
			String line;

			String serventiaX = "731"; // 1078??? Trata RegistroImoveis !
			//
			while ((line = br.readLine()) != null) {
				//
				if (null == line || line.isEmpty()) continue;
				//
				System.out.println("criaArquivoXml() --------------------------------> ( " + line);
				
				String[] paramList = line.split("\t", -1); // Separado por tabs
				
				Integer iX = 0;
				for (String paramX : paramList) {
					//
					System.out.println("criaArquivoTxt() --------------------------------> ( iX = " + iX + " / " + paramX);
					iX++;
				}
				//
				if (serventiaX.equals("1078")) { // RegistroImoveis
					//
					//
				} else {
					// ------------------------------- //
					// ---   (01) DISTRIBUICAO     --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("DIST")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						
						MpParticipanteXml mpParticipanteXml1 = new MpParticipanteXml(paramList[13],  paramList[14],
																					 paramList[15].replace("'", ""), // Nome?
																					 paramList[16], null);
						mpParticipantesXml.getMpParticipanteXmls().add(mpParticipanteXml1);
						
						MpParticipanteXml mpParticipanteXml2 = new MpParticipanteXml(paramList[17], paramList[18], paramList[19],
								 													 paramList[20], null);
						mpParticipantesXml.getMpParticipanteXmls().add(mpParticipanteXml2);
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[28], paramList[29],
																						   paramList[30], paramList[31],
																						   paramList[32], "", "");
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);
	
						MpItemEmolumentoXml mpItemEmolumentoXml2 = new MpItemEmolumentoXml(paramList[33], paramList[34],
																						   paramList[35], paramList[36],
																						   paramList[37], null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml2);
						
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[21], paramList[22],  paramList[23],
																				 paramList[24], paramList[25],  paramList[26],
																				 paramList[27], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpDistribuicaoExtrajudicialXml mpDistribuicaoExtrajudicialXml = new MpDistribuicaoExtrajudicialXml(
							paramList[1],  paramList[2],  paramList[3],  
							paramList[4],  paramList[5],  paramList[6],
							paramList[7],  paramList[8],  null, 
							paramList[9],  paramList[10], paramList[11], 
							paramList[12], null,
							mpParticipantesXml, mpEmolumentosXml);
						//
						mpDistribuicaoExtrajudicialXmls.add(mpDistribuicaoExtrajudicialXml);
				        //
					}
					else
					// ------------------------------- //
					// ---    (02) CANCELAMENTO    --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("CANC")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						
						MpParticipanteXml mpParticipanteXml1 = new MpParticipanteXml(paramList[10],  paramList[11],
																					 paramList[12].replace("'", ""),
																					 paramList[13], null);
						mpParticipantesXml.getMpParticipanteXmls().add(mpParticipanteXml1);
						
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[21], paramList[22],
																						   paramList[23], paramList[24],
																						   paramList[25], "", "");
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);
	
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[14], paramList[15], paramList[16],
																				 paramList[17], paramList[18], paramList[19],
																				 paramList[20], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpDistribuicaoExtrajudicialXml mpDistribuicaoExtrajudicialXml = new MpDistribuicaoExtrajudicialXml(
							paramList[1], paramList[2], paramList[3], 
							null, 		  null,         null,
							paramList[4], paramList[5], null, 
							null,         paramList[6], paramList[7],
							paramList[8], paramList[9],
							mpParticipantesXml, mpEmolumentosXml);
						//
						mpDistribuicaoExtrajudicialXmls.add(mpDistribuicaoExtrajudicialXml);
						//
					}
					else
					// ------------------------------- //
					// ---      (03) CERTIDAO      --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("CERT")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						
						MpParticipanteXml mpParticipanteXml1 = new MpParticipanteXml();
						
						// ??? 9 ou 29 ???
						String tipoPessoaX = paramList[9];
						if (paramList.length > 29)
							tipoPessoaX = paramList[29];
						
						if (paramList[11].trim().length() == 1)
							mpParticipanteXml1 = new MpParticipanteXml(paramList[8], tipoPessoaX, // [9 ou 29] ??
																		paramList[10].replace("'", ""),
																		null, null); // paramList[11]); ???
						else
							mpParticipanteXml1 = new MpParticipanteXml(paramList[8], tipoPessoaX, // [9 ou 29] ??
									   									paramList[10].replace("'", ""),
									   									paramList[11], null);

						mpParticipantesXml.getMpParticipanteXmls().add(mpParticipanteXml1);
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[19], paramList[20],
																						   paramList[21], paramList[22],
																						   paramList[23], "", "");
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);
						
						if (paramList.length > 25)
							if (!paramList[25].isEmpty() && !paramList[26].isEmpty()) {
								//
								MpItemEmolumentoXml mpItemEmolumentoXml2 = new MpItemEmolumentoXml(paramList[24], paramList[25],
																								   paramList[26], paramList[27],
																								   paramList[28], "", "");
								mpItemEmolumentoXmls.add(mpItemEmolumentoXml2);
							}
						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[12], paramList[13], paramList[14],
																				 paramList[15], paramList[16], paramList[17],
																				 paramList[18], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpCertidaoXml mpCertidaoXml = new MpCertidaoXml(
							paramList[1], paramList[2], paramList[3], paramList[4], paramList[5], paramList[6],
							paramList[7],
							"?", "?", "?", "?", "?", 
							mpParticipantesXml, mpEmolumentosXml);
						//
						mpCertidaoXmls.add(mpCertidaoXml);
						//
					}
					else
					// ------------------------------- //
					// ---       (04) VISTO        --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("VISTO")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						
						MpParticipanteXml mpParticipanteXml1 = new MpParticipanteXml(paramList[15], paramList[16],
									   									paramList[17].replace("'", ""),
									   									paramList[18], null);

						mpParticipantesXml.getMpParticipanteXmls().add(mpParticipanteXml1);
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[26], paramList[27],
																						   paramList[28], paramList[29],
																						   paramList[30], null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[19], paramList[20], paramList[21],
																				 paramList[22], paramList[23], paramList[24],
																				 paramList[25], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpVistoXml mpVistoXml = new MpVistoXml(
							paramList[1],  paramList[2],  paramList[3], paramList[4],  paramList[5],  paramList[6],
							paramList[7],  paramList[8],  paramList[9], paramList[10], paramList[11], paramList[12],
							paramList[13], paramList[14],
							mpParticipantesXml, mpEmolumentosXml);
						//
						mpVistoXmls.add(mpVistoXml);
						//
					}
					else
					// ------------------------------- //
					// ---       (05) RASA         --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("RASA")) {
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[13], paramList[14],
																						   paramList[15], paramList[16],
																						   paramList[17], null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[6], paramList[7], paramList[8],
																				 paramList[9], paramList[10], paramList[11],
																				 paramList[12], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpFolhaAdicionalComplementoXml mpFolhaAdicionalComplementoXml = new MpFolhaAdicionalComplementoXml(
							paramList[1],  paramList[2],  paramList[3], paramList[4],  paramList[5],
							mpEmolumentosXml);
						//
						mpFolhaAdicionalComplementoXmls.add(mpFolhaAdicionalComplementoXml);
						//
					}
					else
					// ------------------------------- //
					// ---  (06) INFORMAÇÃO VERBAL --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("INFV")) {
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
						
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[12], paramList[13],
																						   paramList[14], paramList[15],
																						   paramList[16], null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[5], paramList[6], paramList[7],
																				 paramList[8], paramList[9], paramList[10],
																				 paramList[11], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpInformacaoVerbalXml mpInformacaoVerbalXml = new MpInformacaoVerbalXml(
							paramList[1],  paramList[2],  paramList[3], paramList[4],
							mpEmolumentosXml);
						//
						mpInformacaoVerbalXmls.add(mpInformacaoVerbalXml);
						//
					}
					else
					// ------------------------------- //
					// ---  (07) CONVENIO ou CDA   --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("CONV")	|| line.substring(0, 4).equals("CDA")) {
						//
						MpAtosVinculadosXml mpAtosVinculadosXml = new MpAtosVinculadosXml();
						
						MpAtoVinculadoXml mpAtoVinculadoXml1 = new MpAtoVinculadoXml(paramList[3], null,
																					paramList[4],  paramList[5],
																					paramList[6],  paramList[7], null);

						mpAtosVinculadosXml.getMpAtoVinculadoXmls().add(mpAtoVinculadoXml1);
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[15], paramList[15],
																						   paramList[17], paramList[18],
																						   paramList[19], null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						

						MpItemEmolumentoXml mpItemEmolumentoXml2 = new MpItemEmolumentoXml(paramList[20], paramList[21],
																						   paramList[22], paramList[23],
																						   paramList[24], null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml2);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[8],  paramList[9],  paramList[10],
																				 paramList[11], paramList[12], paramList[13],
																				 paramList[14], null, null, null,
																				 mpItemEmolumentoXmls);
						//
						MpComplementoEmolumentosXml mpComplementoEmolumentosXml = new MpComplementoEmolumentosXml(
							paramList[1],  paramList[2],
							mpAtosVinculadosXml, mpEmolumentosXml);
						//
						mpComplementoEmolumentosXmls.add(mpComplementoEmolumentosXml);
						//
					}
					else
					// ------------------------------- //
					// ---      (08) CERTIDÃO      --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("RGICER")) {
						//
						MpImoveisXml mpImoveisXml = new MpImoveisXml();

						MpImovelUrbanoXml mpImovelUrbanoXml = new MpImovelUrbanoXml(paramList[12], paramList[13],
									paramList[14], paramList[15], paramList[16], paramList[17], paramList[18]);

						mpImoveisXml.getMpImovelUrbanoXmls().add(mpImovelUrbanoXml);
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						
						MpPessoaFisicaXml mpPessoaFisicaXml = new MpPessoaFisicaXml(paramList[19], paramList[20],
									   												paramList[21]);

						mpParticipantesXml.getMpPessoaFisicaXmls().add(mpPessoaFisicaXml);
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(null, paramList[32], paramList[33],
																						   paramList[34], paramList[35],
																						   null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[22], paramList[23], paramList[24],
																				 paramList[25], paramList[26], paramList[27],
																				 paramList[28], paramList[29], paramList[30],
																				 paramList[31], 
																				 mpItemEmolumentoXmls);
						//
						MpCertidaoGenericaXml mpCertidaoGenericaXml = new MpCertidaoGenericaXml(
							paramList[1],  paramList[2],  paramList[3], paramList[4],  paramList[5],  paramList[6],
							paramList[7],  paramList[8],  paramList[9], paramList[10], paramList[11],
							mpImoveisXml, mpParticipantesXml, mpEmolumentosXml);
						//
						mpCertidaoGenericaXmls.add(mpCertidaoGenericaXml);
						//
					}
					else
					// ------------------------------- //
					// ---     (09) PRENOTAÇÃO     --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("RGIPRE")) {
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
						
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[22], paramList[23],
																						   paramList[24], paramList[25],
																						   null, null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[12], paramList[13], paramList[14],
																				 paramList[15], paramList[16], paramList[17],
																				 paramList[18], paramList[19], paramList[20],
																				 paramList[21],
																				 mpItemEmolumentoXmls);
						//
						MpCertidaoPrenotacaoXml mpCertidaoPrenotacaoXml = new MpCertidaoPrenotacaoXml(
							paramList[1],  paramList[2],  paramList[3], paramList[4],  paramList[5],
							paramList[6],  paramList[7],  paramList[8], paramList[9],  paramList[10], paramList[11],
							mpEmolumentosXml);
						//
						mpCertidaoPrenotacaoXmls.add(mpCertidaoPrenotacaoXml);
						//
					}
					else
					// ------------------------------------ //
					// --- (10) CANCELAMENTO PRENOTAÇÃO --- //
					// ------------------------------------ //
					if (line.substring(0, 4).equals("RGICANCPRE")) {
						//
						MpAtosVinculadosXml mpAtosVinculadosXml = new MpAtosVinculadosXml();
						
						MpAtoVinculadoXml mpAtoVinculadoXml1 = new MpAtoVinculadoXml(paramList[4],  paramList[5],
									   												 paramList[6],  paramList[7],
									   												 paramList[8],  paramList[9],
									   												 paramList[10]);

						mpAtosVinculadosXml.getMpAtoVinculadoXmls().add(mpAtoVinculadoXml1);
						//
						List<MpItemEmolumentoXml> mpItemEmolumentoXmls = new ArrayList<MpItemEmolumentoXml>();
	
						MpItemEmolumentoXml mpItemEmolumentoXml1 = new MpItemEmolumentoXml(paramList[21], paramList[22],
																						   paramList[23], paramList[24],
																						   null, null, null);
						mpItemEmolumentoXmls.add(mpItemEmolumentoXml1);						
						//
						MpEmolumentosXml mpEmolumentosXml = new MpEmolumentosXml(paramList[11], paramList[12], paramList[13],
																				 paramList[14], paramList[15], paramList[16],
																				 paramList[17], paramList[18], paramList[19],
																				 paramList[20],
																				 mpItemEmolumentoXmls);
						//
						MpCancelamentoPrenotacaoXml mpCancelamentoPrenotacaoXml = new MpCancelamentoPrenotacaoXml(
							paramList[1],  paramList[2], paramList[3],
							mpAtosVinculadosXml, mpEmolumentosXml);
						//
						mpCancelamentoPrenotacaoXmls.add(mpCancelamentoPrenotacaoXml);
						//
					}
					else
					// ------------------------------- //
					// ---       (11) REGISTRO     --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("RGIREG")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						//
					}
					else
					// ------------------------------- //
					// ---       (12) AVERBAÇÃO     --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("RGIAVER")	|| line.substring(0, 4).equals("RGIAVE")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						//
					}
					else
					// ------------------------------------ //
					// --- (13) COMPLEMENTO EMOLUMENTOS --- //
					// ------------------------------------ //
					if (line.substring(0, 4).equals("RGICOMP")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						//
					}
					else
					// ------------------------------- //
					// ---  (14) INFORMAÇÃO VERBAL --- //
					// ------------------------------- //
					if (line.substring(0, 4).equals("RGIINFOV")) {
						//
						MpParticipantesXml mpParticipantesXml = new MpParticipantesXml();
						//
					}
					//
				}
				//
				contLinha++;
//				if (contLinha == 1) break;
				//
			}
			//
			br.close();
			//
			MpRemessaXml mpRemessaXml = new MpRemessaXml();

			mpRemessaXml.setId((dateX.getTime() + "").substring(0, 10)); // "1572010157" => php time() !
			mpRemessaXml.setCodServico(serventiaX); // 731 / 1078 ???	
			mpRemessaXml.setTipoOperacao("I");		
			mpRemessaXml.setSoftware("TopMaster_73735"); // Mp7Oficio ???	
			mpRemessaXml.setAmbiente("PRD");		
			//
			if (serventiaX.equals("1078")) { // RegistroImoveis
				//
				MpRegistroImoveisXml mpRegistroImoveisXml = new MpRegistroImoveisXml();
				
				mpRegistroImoveisXml.setVersaoLayout("1.06");			
				//
				try {
		            //Create JAXB Context
		            JAXBContext jaxbContext = JAXBContext.newInstance(MpRegistroImoveisXml.class);
		             
		            //Create Marshaller
		            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		            
		            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");	            
		            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		 
		            //Print XML String to Console
		            StringWriter sw = new StringWriter();
		             
		            //Write XML to StringWriter
		            jaxbMarshaller.marshal(mpRegistroImoveisXml, sw);
		             
		            //Verify XML Content
		            xmlContent = sw.toString();
		            
//		            System.out.println( xmlContent );
		            //
		        } catch (JAXBException e) {
		        	//
		            e.printStackTrace();
		        }  
			} else {
				//
				MpRegistroDistribuicaoXml mpRegistroDistribuicaoXml = new MpRegistroDistribuicaoXml();
				
				mpRegistroDistribuicaoXml.setVersaoLayout("2.15");			
				mpRegistroDistribuicaoXml.setMpRemessaXml(mpRemessaXml);
			
				// Define Arrays(2) XMLs ...
				if (mpDistribuicaoExtrajudicialXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpDistribuicaoExtrajudicialXmls(mpDistribuicaoExtrajudicialXmls);
				
				if (mpCertidaoXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpCertidaoXmls(mpCertidaoXmls);
				
				if (mpVistoXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpVistoXmls(mpVistoXmls);
				
				if (mpFolhaAdicionalComplementoXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpFolhaAdicionalComplementoXmls(mpFolhaAdicionalComplementoXmls);
				
				if (mpInformacaoVerbalXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpInformacaoVerbalXmls(mpInformacaoVerbalXmls);
				
				if (mpComplementoEmolumentosXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpComplementoEmolumentosXmls(mpComplementoEmolumentosXmls);
				
				if (mpCertidaoGenericaXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpCertidaoGenericaXmls(mpCertidaoGenericaXmls);
				
				if (mpCertidaoPrenotacaoXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpCertidaoPrenotacaoXmls(mpCertidaoPrenotacaoXmls);
				
				if (mpCancelamentoPrenotacaoXmls.size() > 0)
					mpRegistroDistribuicaoXml.setMpCancelamentoPrenotacaoXmls(mpCancelamentoPrenotacaoXmls);
				
				//
				try {
		            //Create JAXB Context
		            JAXBContext jaxbContext = JAXBContext.newInstance(MpRegistroDistribuicaoXml.class);
		             
		            //Create Marshaller
		            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		            
		            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");	            
		            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		 
		            //Print XML String to Console
		            StringWriter sw = new StringWriter();
		             
		            //Write XML to StringWriter
		            jaxbMarshaller.marshal(mpRegistroDistribuicaoXml, sw);
		             
		            //Verify XML Content
		            xmlContent = sw.toString();
		            
//		            System.out.println( xmlContent );
		            //
		        } catch (JAXBException e) {
		        	//
		            e.printStackTrace();
		        }  
			}
			//
			// 01234567890123
			// 0731231019.731 -> 731_20191024_RD_I_102917.xml
			//
			DateFormat sdfHMS = new SimpleDateFormat("HHmmss");
			
			String fileNameX = fileXml.getName().substring(1, 4) + "_20" + fileXml.getName().substring(8, 10) 
								+ fileXml.getName().substring(6, 8) + fileXml.getName().substring(4, 6)	+ "_RD_I_" 
								+ sdfHMS.format(dateX)	+ ".xml";
			//
			try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
												new FileOutputStream(this.scPathArquivoTxt + fileNameX), "UTF-8"))) {
				//
				writer.write(xmlContent.trim());
				writer.close();
				//
			} catch (IOException e) {
				//
				return "Erro gravação arquivo XML : " + this.scPathArquivoTxt + fileNameX + " ( e = " + e; 
			}
			//
			this.moveArquivoGerado(this.scPathArquivoTxt, fileXml.getName(), this.scPathArquivoTxt + "gerados" 
																										+ File.separator);
			//
		} catch (Exception e) {
			//
            e.printStackTrace();
//			return "MpConverteXmlBean.criaArquivoXml() - Erro.Exception ! ( e = " + e;
		}
		//
		return "Geração arquivo XML...  efetuada ! ( " + fileXml.getName();
	}	
	
	public String criaArquivoTxt(File xmlFile) {
		//
		JAXBContext jaxbContext;
		try {
			//
//			List<MpPedidoXml> mpPedidoXmls = new ArrayList <MpPedidoXml>();
//
//			MpPedidoXml mpPedidoXml = new MpPedidoXml("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
//													"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22");
//			mpPedidoXmls.add(mpPedidoXml);
//			
//			mpPedidoXml = new MpPedidoXml("21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
//													"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42");
//			mpPedidoXmls.add(mpPedidoXml);
//			
//			MpRemessaPedidoXml mpRemessaPedidoXml1 = new MpRemessaPedidoXml("1", "2", "3", "4", "5", "2", mpPedidoXmls);
//
//            //Create JAXB Context
//            JAXBContext jaxbContext1 = JAXBContext.newInstance(MpRemessaPedidoXml.class);
//             
//            //Create Marshaller
//            Marshaller jaxbMarshaller1 = jaxbContext1.createMarshaller();
//            
//            jaxbMarshaller1.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");	            
//            jaxbMarshaller1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
// 
//            //Print XML String to Console
//            StringWriter sw1 = new StringWriter();
//             
//            //Write XML to StringWriter
//            jaxbMarshaller1.marshal(mpRemessaPedidoXml1, sw1);
//             
//            //Verify XML Content
//            String xmlContent1 = sw1.toString();
//            
//            System.out.println("mpRemessaPedidoXml1................................." + xmlContent1 );
			
			//
			jaxbContext = JAXBContext.newInstance(MpRemessaPedidoXml.class);			

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			MpRemessaPedidoXml mpRemessaPedidoXml = (MpRemessaPedidoXml) jaxbUnmarshaller.unmarshal(xmlFile);
			
//			System.out.println("MpConverteXmlTxtBean.criaArquivoTxt().... ( " + mpRemessaPedidoXml.getMpPedidoXmls().size());

			if ( BigDecimal.valueOf( mpRemessaPedidoXml.getMpPedidoXmls().size() ).compareTo(scNumeroRegistroArquivoTxt) > 0) {
				//
	    		MpFacesUtil.addInfoMessage("Limite para quebra do arquivo atingido ! ( " + 
	    							mpRemessaPedidoXml.getMpPedidoXmls().size() + " x Limite: " + scNumeroRegistroArquivoTxt);
			}

			// 0123456789012345678901
			// 0731-20191028-011.xml -> CC072810.R08
			
			String fileNameX = "CC07" + xmlFile.getName().substring(11, 13) + xmlFile.getName().substring(9, 11) 
								+ ".R" + xmlFile.getName().substring(15, 17);
			//
			try {
				//
				@SuppressWarnings("unused")
				int i = 0;
				
				int contPed = 0;
				int contArq = 0;
				//
				BufferedWriter writer = null;
				
				for (MpPedidoXml mpPedidoXml :mpRemessaPedidoXml.getMpPedidoXmls()) {
					//
					i++;
					
					contPed++;
					if (contPed == 1) {  
						// Grava Header !
						writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.scPathArquivoXml 
																									+ fileNameX), "UTF-8"));
						// "1#7ODISTRIBUIDOR#28102019#2#1" -> #2#1 = ??
						writer.write("1#7ODISTRIBUIDOR#" +  mpRemessaPedidoXml.getCampo04().replaceAll("/", "") + "#2#1\n");
					} 
					else 
					if (contPed > scNumeroRegistroArquivoTxt.intValue()) {
						// Grava Footer !
						writer.write("9#7ODISTRIBUIDOR#null#null#null#null#null##null#null#null#null\n");
						writer.write("999\n");
						// Fecha arquivo !
						writer.close();
						// Cria novo arquivo ! 
						contArq++;
						// Grava Novo arquivo !
						writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.scPathArquivoXml 
								+ fileNameX + "_" + contArq), "UTF-8"));
						// Grava Header !
						writer.write("1#7ODISTRIBUIDOR#" +  mpRemessaPedidoXml.getCampo04().replaceAll("/", "") + "#2#1\n");
						//
					}
					//
					writer.write("2#" + mpPedidoXml.getCampo01() + "#" + mpPedidoXml.getCampo02() + "#"
							  + mpPedidoXml.getCampo03() + "#" + mpPedidoXml.getCampo04() + "#"
							  + mpPedidoXml.getCampo05() + "#" + mpPedidoXml.getCampo06() + "#"
							  + mpPedidoXml.getCampo07() + "#" + mpPedidoXml.getCampo08() + "#"
							  + mpPedidoXml.getCampo09() + "#" + mpPedidoXml.getCampo10() + "#"
							  + mpPedidoXml.getCampo11() + "#" + mpPedidoXml.getCampo12() + "#"
							  + mpPedidoXml.getCampo13() + "#" + mpPedidoXml.getCampo14() + "#"
							  + mpPedidoXml.getCampo15() + "#" + mpPedidoXml.getCampo16() + "#"
							  + mpPedidoXml.getCampo17() + "#" + mpPedidoXml.getCampo18() + "#"
							  + mpPedidoXml.getCampo19() + "#" + mpPedidoXml.getCampo20() + "#"
							  + mpPedidoXml.getCampo21() + "#" + mpPedidoXml.getCampo22() + "\n");
					//
//					System.out.println("Loop leitura Pedido .......... ( " + i);
				}
				//
				writer.write("9#7ODISTRIBUIDOR#null#null#null#null#null##null#null#null#null\n");
				writer.write("999\n");
				//
				writer.close();
				//
			} catch (Exception e) {
				//
				System.out.println("Erro gravação arquivo TXT : " + this.scPathArquivoTxt + fileNameX + " ( e = " + e); 
				return "Erro gravação arquivo TXT : " + this.scPathArquivoTxt + fileNameX + " ( e = " + e;
			}
			//
			this.moveArquivoGerado(this.scPathArquivoXml, xmlFile.getName(), 
								   this.scPathArquivoXml + "gerados" + File.separator);
			//
		}
		catch (JAXBException e) {
			//
			System.out.println("MpConverteXmlBean.criaArquivoTxt() - Erro.Exception ! ( e = " + e);
			return "Erro gravação arquivo TXT : " + e;
		}		
		//
		return "Geração arquivo TXT...  efetuada ! ( " + xmlFile.getName();
		//
	}	
	
	public void moveArquivoGerado(String pathArquivoM, String arquivoM, String pathArquivoDest) {
		//
		System.out.println("moveArquivoGerado() --------------------------------> ( " + pathArquivoM + arquivoM 
																		+ " | " + pathArquivoDest + arquivoM);
		try {
			FileUtils.moveFile(FileUtils.getFile(pathArquivoM + arquivoM), FileUtils.getFile(pathArquivoDest + arquivoM));
			//
		} catch (IOException e) {
			//
			MpFacesUtil.addInfoMessage("moveArquivoGerado() ! ( e = " + e);
		}
	}        
	
	public String criaArquivoPdf(File xmlFile) {
		//
		JAXBContext jaxbContext;
		try {
			//
			jaxbContext = JAXBContext.newInstance(MpRemessaPedidoXml.class);			

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			MpRemessaPedidoXml mpRemessaPedidoXml = (MpRemessaPedidoXml) jaxbUnmarshaller.unmarshal(xmlFile);
			
			// Trata geração PDF ...
		    
	        // step 1: creation of a document-object
	        Document document = new Document();
	         
	        try {
	        	
	            // step 2:
	            // we create a writer that listens to the document
	            // and directs a PDF-stream to a file
	             
	            PdfWriter.getInstance(document, new FileOutputStream(this.scPathArquivoXml +  xmlFile.getName() + ".pdf"));
	             
	            // step 3: we open the document
	            document.open();
	            document.setPageSize(PageSize.A4);
	            
	            document.addSubject("Mp7Oficio Apps"); document.addKeywords("www.mpxds.com.br");
	            document.addCreator("MPXDS");
	            document.addAuthor("Marcus Vinicius Pinheiro Rodrigues");
	             
	            // step 4: we add a paragraph to the document
	            
	            Paragraph preface = new Paragraph();
	            // We add one empty line
	            addEmptyLine(preface, 1);
	            // Lets write a big header
	            preface.add(new Paragraph("Data Prática: " + mpRemessaPedidoXml.getCampo04(), catFont));

	            addEmptyLine(preface, 1);
	            //
	            addDetalhe(document, preface, "INFORMAÇÃO VERBAL", mpRemessaPedidoXml.getMpPedidoXmls().get(0));	            
	            addDetalhe(document, preface, "DISTRIBUIÇÃO     ", mpRemessaPedidoXml.getMpPedidoXmls().get(1));	            
	            //
	        }
	        catch(DocumentException de) {
	            System.err.println(de.getMessage());
	        }
	        catch(IOException ioe) {
	            System.err.println(ioe.getMessage());
	        }
	         
	        // step 5: we close the document
	        document.close();			
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("criaArquivoPdf() ! ( e = " + e);
		}
		//
		return "Geração RELATÓRIO arquivo TXT...  efetuada ! ( " + xmlFile.getName();
		//
	}
	
	private void addDetalhe(Document document, Paragraph preface, String label, MpPedidoXml mpPedidoXml)
																							throws DocumentException {
		//
        preface.add(new Paragraph(label, subFont));
        addEmptyLine(preface, 1);
        
        document.add(preface);
        //
        PdfPTable table = new PdfPTable(10);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("EMOLUMENTOS"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("FETJ"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("FUNDPERJ"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("FUNPERJ"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("FUNARPEN"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("RESSAG"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("JG"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("NIHIL"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("SC"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("TOTAL"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        table.setHeaderRows(1);
        //
        addDetalheTable(document, table, mpPedidoXml);
	}

	private void addDetalheTable(Document document, PdfPTable table, MpPedidoXml mpPedidoXml) throws DocumentException {
		//
        table.addCell(mpPedidoXml.getCampo01());
        table.addCell(mpPedidoXml.getCampo02());
        table.addCell(mpPedidoXml.getCampo03());
        table.addCell(mpPedidoXml.getCampo04());
        table.addCell(mpPedidoXml.getCampo05());
        table.addCell(mpPedidoXml.getCampo06());
        table.addCell(mpPedidoXml.getCampo07());
        table.addCell(mpPedidoXml.getCampo08());
        table.addCell(mpPedidoXml.getCampo09());
        table.addCell(mpPedidoXml.getCampo10());

        document.add(table);
	}
	
    private void addEmptyLine(Paragraph paragraph, int number) {
    	//
        for (int i = 0; i < number; i++) {
        	//
            paragraph.add(new Paragraph(" "));
        }
    }		
				
    // ---

	public String getScPathArquivoXml() { return scPathArquivoXml; }
	public void setScPathArquivoXml(String scPathArquivoXml) { this.scPathArquivoXml = scPathArquivoXml; }

	public String getScPathArquivoTxt() { return scPathArquivoTxt; }
	public void setScPathArquivoTxt(String scPathArquivoTxt) { this.scPathArquivoTxt = scPathArquivoTxt; }
    
    public String getArquivoXmlSelecionado() { return arquivoXmlSelecionado; }
	public void setArquivoXmlSelecionado(String arquivoXmlSelecionado) { 
																this.arquivoXmlSelecionado = arquivoXmlSelecionado; }
	
    public List<String> getArquivoXmlList() { return arquivoXmlList; }
	public void setArquivoXmlList(List<String> arquivoXmlList) { this.arquivoXmlList = arquivoXmlList; }
	
    public List<String> getArquivoXmlSelecionadoList() { return arquivoXmlSelecionadoList; }
	public void setArquivoXmlSelecionadoList(List<String> arquivoXmlSelecionadoList) { 
																	this.arquivoXmlSelecionadoList = arquivoXmlSelecionadoList; }

    public String getArquivoTxtSelecionado() { return arquivoTxtSelecionado; }
	public void setArquivoTxtSelecionado(String arquivoTxtSelecionado) { 
																this.arquivoTxtSelecionado = arquivoTxtSelecionado; }
	
    public List<String> getArquivoTxtList() { return arquivoTxtList; }
	public void setArquivoTxtList(List<String> arquivoTxtList) { this.arquivoTxtList = arquivoTxtList; }
	
    public List<String> getArquivoTxtSelecionadoList() { return arquivoTxtSelecionadoList; }
	public void setArquivoTxtSelecionadoList(List<String> arquivoTxtSelecionadoList) { 
																	this.arquivoTxtSelecionadoList = arquivoTxtSelecionadoList; }

}