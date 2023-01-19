package com.mpxds.basic.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
//import java.text.Normalizer;
//import java.text.Normalizer.Form;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;

//import javax.enterprise.context.SessionScoped;
//import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
import javax.inject.Named;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPedido;
import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfRepasse;
import org.datacontract.schemas._2004._07.ecartorio_service.Pedido;
import org.datacontract.schemas._2004._07.ecartorio_service.Repasse;
import org.primefaces.PrimeFaces;
//import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.tempuri.MpHeaderHandlerResolver;

import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
//import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
//import com.itextpdf.text.pdf.PdfTemplate;
//import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.mpxds.basic.model.MpCertidaoLog;
import com.mpxds.basic.model.MpSistemaConfig;
import com.mpxds.basic.security.MpSeguranca;
import com.mpxds.basic.service.MpCertidaoLogService;
import com.mpxds.basic.service.MpSistemaConfigService;
import com.mpxds.basic.util.MpAppUtil;
import com.mpxds.basic.util.jsf.MpFacesUtil;
//import com.itextpdf.text.pdf.parser.PdfTextExtractor;

@Named
@ViewScoped
public class MpECartorioRJBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MpSistemaConfigService mpSistemaConfigService;

    @Autowired
	private MpCertidaoLogService mpCertidaoLogService;
	
	@Autowired
	private MpSeguranca mpSeguranca;
    
	private String scOficServentiaECartorioRJ = ""; // 0753 ???? 0731
	
	private String scPathCertidaoEletronica = ""; 
	private String scPathEtiqueta = ""; 
	private String scPathCertidaoEletronicaCerp = ""; 
	private String scPathArquivoGerado = ""; 
	private Boolean scIndGeraPDF_A;
	private String scPathArquivoDisquete = ""; 

	// Impressoras ...
	// ==============================================
	private String scImpressoraConfigCertidao = ""; 
	private String scImpressoraConfigCertidaoCerp = ""; 
	private String scImpressoraConfigCertidaoCeesp = ""; 

	private String scImpressoraCertEtiqueta = ""; 
	private String scImpressoraCertCertidaoA4 = ""; 
	private String scImpressoraCertXerox = "";

	private String scImpressoraCancCertidaoA4 = ""; 
	private String scImpressoraCancEtiqueta = ""; // Vrf.Existe! scImpressoraCancEtiquetaUser?(Charles/Rafael) Assumir! ;

	private Boolean scIndPrintJS = false;

	private String scNomeTabeliao = ""; // MVPR-20201204 !
	private String scNomeTabeliaoRectangle = ""; // MVPR-20201204 !
	// ==============================================

	private Boolean scIndProducaoECartorioRJ = false;
	
	private String arquivoX;
	private String arquivoXR;
	private String pathX;
		
	private Date dataInicio;
	private Date dataFim;	
	private Date dataInicioR; // Repasse Inicio
	private Date dataFimR; // Repasse Fim
	
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String respostaWS;
	private String respostaWSR;
    
	private StreamedContent fileSC;
	private StreamedContent fileSCRepasse;
	
	private Boolean indFile = false;
	private Boolean indFileRepasse = false;
	
	private Boolean indAdmin = false;	
	private Boolean indUserCert = false;
	private Boolean indUserCanc = false;

	private File fileCE;
	private File fileCERepasse;
	
	private String arquivoCE = "";
	private String numeroCERP = "";
	private String numeroCERP_A = "";
	
	private String ambienteCE = "( Desenvolvimento )";
	
	private String arquivoSelecionado = "";
	private String arquivoSelecionadoEdita = "";
	private String arquivoSelecionadoEditaP = "";
	
	private String arquivoEtiquetaSelecionado = "";
	private String arquivoBalcaoSelecionado = "";
	private String arquivoPedidoSelecionado = "";
	
	private Long numeroPedidoSelecionado = 0L;
	private String nomePedidoSelecionado = "";
	
	private List<Pedido> pedidoList = new ArrayList<Pedido>();
	private List<Pedido> pedidoListFiltered = new ArrayList<Pedido>();
	
	private List<String> arquivoBalcaoList = new ArrayList<String>();
	private List<Repasse> repasseList = new ArrayList<Repasse>();
	private List<String> arquivoEtiquetaList = new ArrayList<String>();
	
	private List<String> arquivoEtiquetaSelecionadoList = new ArrayList<String>();
	private List<String> arquivoBalcaoSelecionadoList = new ArrayList<String>();
	private List<Pedido> arquivoPedidoSelecionadoList = new ArrayList<Pedido>();

	private Integer tabViewIndex = 0;

	private String textoEditor = "";
	private String textoEditorP = "";
	
//	private String textoEditorX = "";

	private Boolean indImpressao;
	private Boolean indExigencia;
	private Boolean indGratuito;
	
	private String[] lnX = new String[6];
	
	//----------------
	
	public void inicializar() {
		//
		// Trata Configuração Sistema ...
		// ==============================
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathCertidaoEletronica");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathCertidaoEletronica' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathCertidaoEletronica = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathCertidaoEletronicaCerp");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathCertidaoEletronicaCerp' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathCertidaoEletronicaCerp = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathEtiqueta");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathEtiqueta' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathEtiqueta = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathArquivoGerado");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathArquivoGerado' em 'SistemaConfig' ... não existe !");
			this.scPathArquivoGerado = "";
		} else
			this.scPathArquivoGerado = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathArquivoDisquete");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathArquivoDisquete' em 'SistemaConfig' ... não existe !");
			this.scPathArquivoDisquete = "";
		} else
			this.scPathArquivoDisquete = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("oficServentiaECartorioRJ");
		if (null == mpSistemaConfig)
			this.scOficServentiaECartorioRJ = "0731"; // "????";
		else
			this.scOficServentiaECartorioRJ = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("nomeTabeliao");
		if (null == mpSistemaConfig) {
			this.scNomeTabeliao = "RIO DE JANEIRO CARTORIO DO 7° OFICIO DE REGISTRO E DISTRIBUIÇÃO"; // MVPR-20201204 ;
			this.scNomeTabeliaoRectangle = "000 000 190 010 095 700 S"; // MVPR-20201204 ;
		} else {
			this.scNomeTabeliao = mpSistemaConfig.getValor();
			this.scNomeTabeliaoRectangle = mpSistemaConfig.getDescricao().substring(15); // MVPR-20201204 ;
		}
		
		// ================================================================================================
		// Configuração Certidão ... Margem.Superior + Inferior + Tam.Espaçamento + Fonte + FonteEspaço ...
		// ================================================================================================
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("impressoraConfigCertidao");
		if (null == mpSistemaConfig) {
			//                                 012345678901234567890123456
			this.scImpressoraConfigCertidao = "60 15 05 05 10.0 10.0 08.0";
		} else
			this.scImpressoraConfigCertidao = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("impressoraConfigCertidaoCerp");
		if (null == mpSistemaConfig) {
			this.scImpressoraConfigCertidaoCerp = this.scImpressoraConfigCertidao;
		} else
			this.scImpressoraConfigCertidaoCerp = mpSistemaConfig.getValor();
		//
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("impressoraConfigCertidaoCeesp");
		if (null == mpSistemaConfig) {
			this.scImpressoraConfigCertidaoCeesp = this.scImpressoraConfigCertidao;
		} else
			this.scImpressoraConfigCertidaoCeesp = mpSistemaConfig.getValor();
		// ================================================================================================

		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("indProducaoECartorioRJ");
		if (null == mpSistemaConfig)
			this.scIndProducaoECartorioRJ = false;
		else
			this.scIndProducaoECartorioRJ = mpSistemaConfig.getIndValor();
				
		if (this.scIndProducaoECartorioRJ) 
			ambienteCE = "( Produção )";
		
		if (this.scIndProducaoECartorioRJ) 
			ambienteCE = "( Produção )";

		// Trata ativação geração PDF/A (MR-29032019) !
		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("indGeraPDF_A");
		if (null == mpSistemaConfig)
			this.scIndGeraPDF_A = false;
		else
			this.scIndGeraPDF_A = mpSistemaConfig.getIndValor();
		
		// =====================
		// Trata Impressoras ...
		// =====================		
		this.scImpressoraCertEtiqueta = this.capturaImpressoraSC("impressoraCertEtiqueta");
		this.scImpressoraCertCertidaoA4 = this.capturaImpressoraSC("impressoraCertCertidaoA4");
		this.scImpressoraCertXerox = this.capturaImpressoraSC("impressoraCertXerox");
		this.scImpressoraCancCertidaoA4 = this.capturaImpressoraSC("impressoraCancCertidaoA4");
		this.scImpressoraCancEtiqueta = this.capturaImpressoraSC("impressoraCancEtiqueta");

		mpSistemaConfig = this.mpSistemaConfigService.findByParametro("indPrintJS");
		if (null == mpSistemaConfig)
			this.scIndPrintJS = false;
		else
			this.scIndPrintJS = mpSistemaConfig.getIndValor();
		
		// =====================		
		
		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(new Date());
		dataIni.set(Calendar.MONTH, 0);
		dataIni.set(Calendar.DAY_OF_MONTH, 1);
		
		this.dataInicio = dataIni.getTime();	
		this.dataFim = new Date();
		
		this.dataInicioR = dataIni.getTime();	
		this.dataFimR = new Date();
		//		
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

		this.pathX = extContext.getRealPath(File.separator + "resources" + File.separator + 
																					"importacao" + File.separator);
		this.arquivoX = mpSeguranca.getUsuarioLogado() + "_arrayOfPedido.txt";
		this.arquivoXR = mpSeguranca.getUsuarioLogado() + "_arrayOfRepasse.txt";
		
		this.indAdmin = mpSeguranca.isAdmins();
		this.indUserCert = mpSeguranca.isUserCerts();
		this.indUserCanc = mpSeguranca.isUserCancs();

		this.indImpressao = true;
		//
		this.tabViewIndex = 0;
		//
		this.lnX[0] = " ";

//		this.lnX[1] = "A autenticidade dessa certidão deve ser confirmada no site da Central Eletrônica";
//		this.lnX[2] = "de Registros Públicos - ANOREG-RJ (http://validador.e-cartoriorj.com.br).";
//		this.lnX[3] = "A certidão eletrônica estará disponivel para donwload pelo período de 90(noventa)";
//		this.lnX[4] = "dias após sua emissão.";

		this.lnX[1] = "Está certidão eletrônica estará disponivel para download e validação no Portal Extrajudicial da Corregedoria Geral";
		this.lnX[2] = "da Justiça (acesso pela página do TJRJ/Corregedoria/Extrajudicial/Portal Extrajudicial) pelo período de 90 (noventa)";
		this.lnX[3] = "dias após sua emissão.";
		this.lnX[4] = " ";

		this.lnX[5] = "CERP " + this.numeroCERP;
		//		
//		System.out.println("MpECartorioRJBean.preRender() - Path + Arquivo = " + this.pathX + " / " + this.arquivoX);
	}

	public String capturaImpressoraSC(String scParamImpressora) {
		//
		String impressoraSC = "";
		
		// Trata Exceção Impressora  Por Usuário ... (Ex.: Charles ou Rafael) !
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigService.findByParametro(scParamImpressora + 
																		mpSeguranca.getUsuarioLogado().toUpperCase());
		if (null == mpSistemaConfig) {
			//
			mpSistemaConfig = this.mpSistemaConfigService.findByParametro(scParamImpressora);
			if (null == mpSistemaConfig)
				impressoraSC = "";
			else
				impressoraSC = mpSistemaConfig.getValor();
		} else
			impressoraSC = mpSistemaConfig.getValor();	
		//
		return impressoraSC;
	}
	
	public void executaWS() {
		//
		try {
	//		System.out.println("MpECartorioRJBean.executaWS() - 000");        
	        MpHeaderHandlerResolver mpHandlerResolver = new MpHeaderHandlerResolver();
	        
	        org.tempuri.IService serviceInterfaceX;
	        //
	        if (this.scIndProducaoECartorioRJ) {
				//
	        	org.tempuri.ServiceProducao serviceXP = new org.tempuri.ServiceProducao();
	        	
	    		serviceXP.setHandlerResolver(mpHandlerResolver);
	
		        serviceInterfaceX = serviceXP.getPort(org.tempuri.IService.class,
														new javax.xml.ws.soap.AddressingFeature());
			} else {
				//
				org.tempuri.Service serviceXD = new org.tempuri.Service();
	        	
				serviceXD.setHandlerResolver(mpHandlerResolver);
				
				serviceInterfaceX = serviceXD.getPort(org.tempuri.IService.class,
														new javax.xml.ws.soap.AddressingFeature());
			}
	        //
	        String filtroListarPedido = "PAGAMENTO REALIZADO";
	        
	        if (this.indExigencia || this.indGratuito)
	        	filtroListarPedido = "";
	        
			ArrayOfPedido arrayOfPedido = serviceInterfaceX.listarPedidos(this.scOficServentiaECartorioRJ, "", 
											filtroListarPedido, sdf.format(this.dataInicio), sdf.format(this.dataFim),
											"", "", "", "", "", "", "", "");
			//
			JAXBContext jaxbContext = JAXBContext.newInstance(Pedido.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	
			this.fileCE = new File(this.pathX + File.separator + this.arquivoX);
	
			// Marshal the employees list in console
			OutputStream outputStreamX = new FileOutputStream(this.fileCE);
			
			jaxbMarshaller.marshal(arrayOfPedido, outputStreamX);
	
			InputStream inputStreamX = new FileInputStream(this.fileCE);
			
			Charset charsetX = StandardCharsets.UTF_8;
			
	//		jaxbMarshaller.marshal(arrayOfPedido, System.out);
	
			// //Marshal the employees list in file
			// jaxbMarshaller.marshal(arrayOfPedido, new
			// File("c:/temp/employees.xml"));
			//
			String msgWS = MpAppUtil.convertIS(inputStreamX, charsetX);
			
			this.respostaWS = "Número Pedidos retornado pelo WebService = ( " + arrayOfPedido.getPedido().size() +
																	" )\n\n" + msgWS.substring(0, msgWS.length() / 2);
			this.indFile = true;
			
	//		System.out.println("MpECartorioRJBean.executaWS() - RespostaWS = " + this.respostaWS);
			//
	
			// Unmarshal ArrayOfPedifo ...
			JAXBContext jaxbContextX = JAXBContext.newInstance(ArrayOfPedido.class);
	        Unmarshaller unmarshallerX = jaxbContextX.createUnmarshaller();
	//      ArrayOfPedido arrayOfPedidoX = (ArrayOfPedido) unmarshallerX.unmarshal(this.fileX);
	        ArrayOfPedido arrayOfPedidoX = (ArrayOfPedido) JAXBIntrospector.getValue(unmarshallerX.unmarshal(this.fileCE));
	        //
			this.pedidoList.clear();
			this.pedidoListFiltered.clear();
			
			for (Pedido pedidoX : arrayOfPedidoX.getPedido()) {
				//
				if (this.indExigencia) {
					if (pedidoX.getDsPdStatusPedidoExt().getValue().trim().toUpperCase().contains("EXIGÊNCIA")) {
						this.pedidoList.add(pedidoX);
						this.pedidoListFiltered.add(pedidoX);
					}
				} else
					if (this.indGratuito) {
						if (pedidoX.getDsPdStatusPedidoExt().getValue().trim().toUpperCase().contains("ATO GRATUITO")) {
//						||  pedidoX.getDsPdStatusPedidoExt().getValue().trim().toUpperCase().contains("EM EXECUÇÃO CARTÓRIO")
//						||  pedidoX.getDsPdStatusPedidoExt().getValue().trim().toUpperCase().contains("ATO DIVERGENTE"))
							this.pedidoList.add(pedidoX);
							this.pedidoListFiltered.add(pedidoX);
						}
					} else {
						this.pedidoList.add(pedidoX);
						this.pedidoListFiltered.add(pedidoX);
					}
				//
	//			System.out.println("MpECartorioRJBean.executaWS() - Pedidos = (" + 
	//											pedidoX.getDsPdStatusPedidoExt().getValue().trim() + ")");
			}		
			//
			this.tabViewIndex = 0;
			//
        } catch (Exception e) {
			//
        	System.out.println("Erro ao acessar serviço - EcartorioRJ ! ( e ! = " + e);
    		MpFacesUtil.addInfoMessage("Erro ao acessar serviço - EcartorioRJ ! ( e ! = " + e);
		}
	}

	public void executaWSRepasse() throws Exception {
		//
		try {
			String msg = "X";
	//		System.out.println("MpECartorioRJBean.executaWS() - 000");
			MpFacesUtil.addInfoMessage("Em fase de desenvolvimento ... contactar o Suporte ! ");
			if (msg.isEmpty())
				assert(true); //nop
			else {
				//
			    MpHeaderHandlerResolver mpHandlerResolver = new MpHeaderHandlerResolver();
			        
			    org.tempuri.IService serviceInterfaceX;
		        //
		        if (this.scIndProducaoECartorioRJ) {
					//
		        	org.tempuri.ServiceProducao serviceXP = new org.tempuri.ServiceProducao();
	
		        	serviceXP.setHandlerResolver(mpHandlerResolver);
	
			        serviceInterfaceX = serviceXP.getPort(org.tempuri.IService.class,
															new javax.xml.ws.soap.AddressingFeature());
				} else {
					//
					org.tempuri.Service serviceXD = new org.tempuri.Service();
	
					serviceXD.setHandlerResolver(mpHandlerResolver);
					
					serviceInterfaceX = serviceXD.getPort(org.tempuri.IService.class,
															new javax.xml.ws.soap.AddressingFeature());
				}
				//
		        String modelo = "RIO DE JANEIRO";
		        String idCerp = "";
		        
				ArrayOfRepasse arrayOfRepasse = serviceInterfaceX.listarRepasses(this.scOficServentiaECartorioRJ, 
												sdf.format(this.dataInicioR), sdf.format(this.dataFimR), modelo, idCerp);			
				//
				JAXBContext jaxbContext = JAXBContext.newInstance(Repasse.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
				this.fileCERepasse = new File(this.pathX + File.separator + this.arquivoXR);
		
				// Marshal the employees list in console
				OutputStream outputStreamX = new FileOutputStream(this.fileCERepasse);
				
				jaxbMarshaller.marshal(arrayOfRepasse, outputStreamX);
		
				InputStream inputStreamX = new FileInputStream(this.fileCERepasse);
				
				Charset charsetX = StandardCharsets.UTF_8;
				
		//		jaxbMarshaller.marshal(arrayOfPedido, System.out);
		
				// //Marshal the employees list in file
				// jaxbMarshaller.marshal(arrayOfPedido, new
				// File("c:/temp/employees.xml"));
				//
				String msgWS = MpAppUtil.convertIS(inputStreamX, charsetX);
				
				this.respostaWSR = "Número Repasses retornado pelo WebService = ( " + arrayOfRepasse.getRepasse().size() +
																		" )\n\n" + msgWS.substring(0, msgWS.length() / 2);
				this.indFileRepasse = true;
				
		//		System.out.println("MpECartorioRJBean.executaWSRepasse() - RespostaWS = " + this.respostaWSR);
				//
		
				// Unmarshal ArrayOfRepasse ...
				JAXBContext jaxbContextX = JAXBContext.newInstance(ArrayOfRepasse.class);
		        Unmarshaller unmarshallerX = jaxbContextX.createUnmarshaller();
		//      ArrayOfPedido arrayOfPedidoX = (ArrayOfPedido) unmarshallerX.unmarshal(this.fileX);
		        ArrayOfRepasse arrayOfRepasseX = (ArrayOfRepasse) JAXBIntrospector.getValue(unmarshallerX.unmarshal(
		        																						this.fileCERepasse));
				this.repasseList = arrayOfRepasseX.getRepasse();
				
		//		for (Repasse pedidoX : this.repasseList) {
		//			//
		//			System.out.println("MpECartorioRJBean.executaWSRepasse() - Repasses = " + repasseX.getModelo().getValue());
		//		}
			//
			}
			//
        } catch (Exception e) {
			//
    		MpFacesUtil.addInfoMessage("Erro ao acessar serviço Repasse - EcartorioRJ ! ( e ! = " + e);
		}
	}

	public void FileDownloadView() throws FileNotFoundException {
		//
//		InputStream streamX = new FileInputStream(this.fileX);
//        
//        this.fileSC = new DefaultStreamedContent(streamX, "text/plain", "down_" + this.arquivoX);

//        System.out.println("MpECartorioRJBean.FileDownloadView() " + this.fileSC.getName());
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        
        response.setHeader("Content-Disposition", "attachment;filename=pedidos.xml");
        response.setContentLength((int) this.fileCE.length());
        
        FileInputStream input= null;
        //
        try {
            @SuppressWarnings("unused")
			int i= 0;
            input = new FileInputStream(this.fileCE);  
            byte[] buffer = new byte[1024];
            while ((i = input.read(buffer)) != -1) {  
                response.getOutputStream().write(buffer);  
                response.getOutputStream().flush();  
            }               
            facesContext.responseComplete();
            facesContext.renderResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(input != null) {
                    input.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }        
        //
    }

	public void FileDownloadViewRepasse() throws FileNotFoundException {
		//
//		InputStream streamX = new FileInputStream(this.fileX);
//        
//        this.fileSC = new DefaultStreamedContent(streamX, "text/plain", "down_" + this.arquivoX);

//        System.out.println("MpECartorioRJBean.FileDownloadView() " + this.fileSC.getName());
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        
        response.setHeader("Content-Disposition", "attachment;filename=repasses.xml");
        response.setContentLength((int) this.fileCERepasse.length());
        
        FileInputStream input= null;
        //
        try {
            @SuppressWarnings("unused")
			int i= 0;
            input = new FileInputStream(this.fileCERepasse);  
            byte[] buffer = new byte[1024];
            while ((i = input.read(buffer)) != -1) {  
                response.getOutputStream().write(buffer);  
                response.getOutputStream().flush();  
            }               
            facesContext.responseComplete();
            facesContext.renderResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(input != null) {
                    input.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }        
        //
    }

	public void sairWS() {
		//
		this.respostaWS = "";
		
		this.indFile = false;

//		System.out.println("MpECartorioRJBean.sairWS()");
		//
		try {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/dashboard.xhtml");
			
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Erro Redirecionameto Fechar ... contactar o Suporte ! ");
		}
	}	

	public void trataArquivoCE() {
		//
		// Trata Expiração do Sistema !
		Calendar calx = new GregorianCalendar(2022,3,4); // 0-Jan 1-Fev 2-Mar 3-Abr 4-Mai 5-Jun 6-Jul ...
		
		Date dataAtual = new Date();

//		System.out.println("trataArquivoCE --> " + sdf.format(calx.getTime()) + " / " +
//																					sdf.format(dataAtual));	
		if (dataAtual.after(calx.getTime())) {
			//
			MpFacesUtil.addInfoMessage("Error on System. Exception Error Code = (x0331).");
//			return;
		}		
		//
		if (this.arquivoCE.isEmpty()) {
			//
			return;
		}
		//
		String pathXX = this.scPathCertidaoEletronica;
		if (this.arquivoSelecionado.toUpperCase().indexOf(".CER") >= 0)
			pathXX = this.scPathCertidaoEletronicaCerp;

    	File fileX = new File(pathXX + this.arquivoCE);
    	if (!fileX.exists()) {
    		//
    		MpFacesUtil.addInfoMessage("ARQUIVO... Não existe ! ( " + pathXX + this.arquivoCE);
			return;
    	}
		//
    	if (this.arquivoCE.toUpperCase().contains(".XML")) {
    		MpFacesUtil.addInfoMessage(this.atualizaRenameXML_CERP(fileX.getAbsolutePath(), this.numeroCERP));
    		//
    	} else {
    		//
    		this.numeroCERP_A = this.numeroCERP;
    		
    		// Trata geração PDF/A (MR-29032019) ! 
    		if (this.arquivoCE.toUpperCase().contains(".CER") && this.scIndGeraPDF_A ) {
    			//
//    			System.out.println("trataArquivoCE ....... PDF/A - 000");
        		try {
//	    			System.out.println("trataArquivoCE ....... PDF/A - 001 ( " + fileX.getAbsolutePath());
					MpFacesUtil.addInfoMessage(this.criaPDF_A(fileX.getAbsolutePath()));
//	    			System.out.println("trataArquivoCE ....... PDF/A - 002 ( " + fileX.getAbsolutePath());
					//
				} catch (DocumentException | IOException e) {
					MpFacesUtil.addInfoMessage("Erro geração PDF/A... ( e = " + e);
				}
    		} else {
        		MpFacesUtil.addInfoMessage(this.criaPDF(fileX.getAbsolutePath()));
    		}
    		
    		// Trata movimentação arquivo TXT Gerado !!! MPVR-19032019 ...
    		if (null == this.scPathArquivoGerado || this.scPathArquivoGerado.isEmpty())
    			assert(true); // nop
    		else
    			this.moveArquivoGerado(pathXX, this.arquivoCE);
    		//
    	}
	}	

	public void trataArquivoEtiqueta() {
		//
		if (this.arquivoEtiquetaSelecionado.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Informar arquivo !");
			return;
		}
		//
    	File fileX = new File(this.scPathEtiqueta + this.arquivoEtiquetaSelecionado);
    	if (!fileX.exists()) {
    		//
    		MpFacesUtil.addInfoMessage("ARQUIVO... Não existe ! ( " + this.scPathEtiqueta + 
    																			this.arquivoEtiquetaSelecionado);
			return;
    	}
		//
    	MpFacesUtil.addInfoMessage(this.criaEtiquetaPDF(fileX.getAbsolutePath()));
		//
		if (this.scIndPrintJS && this.arquivoEtiquetaSelecionadoList.isEmpty())
			this.printEtiquetaAll();

		// Trata movimentação arquivo TXT Gerado !!! MPVR-19032019 ...
		if (null == this.scPathArquivoGerado || this.scPathArquivoGerado.isEmpty())
			assert(true); // nop
		else
			this.moveArquivoGerado(this.scPathEtiqueta, this.arquivoEtiquetaSelecionado);
	}	
	
	public String criaPDF(String fileX) {
		//
		//                             012345678901234567890123456
		// ImpressoraConfigCertidao = "60 15 05 05 10.0 10.0 08.0";
		//
		Float scMarginLeft    = Float.parseFloat(this.scImpressoraConfigCertidao.substring(0, 2));
		Float scMarginRight   = Float.parseFloat(this.scImpressoraConfigCertidao.substring(3, 5));
		Float scMarginTop     = Float.parseFloat(this.scImpressoraConfigCertidao.substring(6, 8));
		Float scMarginBottom  = Float.parseFloat(this.scImpressoraConfigCertidao.substring(9, 11));
		Float scLineSpace     = Float.parseFloat(this.scImpressoraConfigCertidao.substring(12, 16));
		Float scFntSize       = Float.parseFloat(this.scImpressoraConfigCertidao.substring(17, 21));
		Float scFntSizeBlank  = Float.parseFloat(this.scImpressoraConfigCertidao.substring(22, 26));
		
//		"60 15 05 05 10.0 10.0 08.0";
//		  |  |  |  |   |    |    |
//		  |  |  |  |   |    |    +---> Tamanho Fonte Linha em Branco ;
//		  |  |  |  |   |    +--------> Tamanho Fonte ;
//		  |  |  |  |   +-------------> Tamanho Espaço entre linhas ;
//		  |  |  |  +-----------------> Tamanho Margem Inferior 
//		  |  |  +--------------------> Tamanho Margem Superior  
//		  |  +-----------------------> Tamanho Margem Direita
//		  +--------------------------> Tamanho Margem Esquerda
		
		if (fileX.toLowerCase().contains(".cer")) {
			//
			scMarginLeft    = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(0, 2));
			scMarginRight   = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(3, 5));
			scMarginTop     = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(6, 8));
			scMarginBottom  = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(9, 11));
			scLineSpace     = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(12, 16));
			scFntSize       = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(17, 21));
			scFntSizeBlank  = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(22, 26));
		}
		// Rubens Nova Certidão Aeronáutica - MVPR-15052019 !
		if (fileX.toLowerCase().contains("ceesp")) {
			//
			scMarginLeft    = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(0, 2));
			scMarginRight   = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(3, 5));
			scMarginTop     = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(6, 8));
			scMarginBottom  = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(9, 11));
			scLineSpace     = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(12, 16));
			scFntSize       = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(17, 21));
			scFntSizeBlank  = Float.parseFloat(this.scImpressoraConfigCertidaoCeesp.substring(22, 26));
		}
		//
//		System.out.println("Certidão CONFIGURACAO CERTIDÃO --------------------------------> ( " 
//					+ scMarginLeft + " / " + scMarginRight + " / " + scMarginTop + " / " + scMarginBottom + " / "
//					+ scLineSpace + " / " + scFntSize + " / " + scFntSizeBlank + " )");
		//
		String fileXAnt = fileX;
		//
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileX), "UTF-8"))) {
			//
			// Document(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom)
			Document document = new Document(PageSize.A4, scMarginLeft, scMarginRight, scMarginTop, scMarginBottom);

//			float fntSize = 12f;
//			float lineSpacing = 10f;
			//
			Integer contLinha = 0;
			Integer linhaPosQRCODE = 0;
			
			// Trata extensão do PDF !
			fileX = fileX.toLowerCase().replace(".txt", ".pdf");
			
			// Para arquivo tipo (.CER) ...gerar "num_cerp.pdf" ... (MVPR-08042019) !
			if (fileX.toLowerCase().contains(".cer"))
				fileX = this.scPathCertidaoEletronicaCerp + this.numeroCERP + ".pdf";
			//
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileX));

			document.open();
			document.addCreator("MPXDS wwww.mpxds.com.br");
			document.addAuthor("Marcus VPR");

			Font fontXpt = new Font(FontFamily.COURIER, scFntSize, Font.BOLD); // MR-14032019 (WAS=9) !
			Font fontYpt = new Font(FontFamily.COURIER, scFntSizeBlank); // Line Blank // MR-14032019 (WAS=7.9f) !
			Font fontZpt = new Font(FontFamily.COURIER, scFntSize - 3, Font.BOLD);
			
//			Font fontXpt = new Font(FontFamily.COURIER, 7, Font.BOLD);
//			Font regular = new Font(FontFamily.HELVETICA, 12);
//			Font bold = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
			
	        // ===================================================================
			// MVPR-20201204 !
	        // ===================================================================
			
			// (7) Parâmetros = 000 000 190 010 095 700 S
			//                   |   |    |  |   |   |  |
			//                   |   |    |  |   |   |  |=> (6) Ativa = S - Sim / N - Não ;
			//                   |   |    |  |   |   |====> (5) Posição Y Texto ;          
			//                   |   |    |  |   |========> (4) Posição X Texto ;          
			//                   |   |    |  |============> (3) Posição -Y1 Retângulo ;      
			//                   |   |    |===============> (2) Posição -X1 Retângulo ;      
			//                   |   |====================> (1) Posição -Y Retângulo ;          
			//                   |========================> (0) Posição -X Retângulo ;          

			String[] wordsTabeliao = this.scNomeTabeliaoRectangle.trim().split("\\s+");
			
			System.out.println("MVPR-20201205......................." + this.scNomeTabeliao + " / "
	        						+ this.scNomeTabeliaoRectangle + " / " + wordsTabeliao.length);
	        
	        // ------------- 
	        
//	        if (wordsTabeliao.length == 7) {
//	        	//
////	        	MpFacesUtil.addInfoMessage( "MVPR-20201205......................." + Integer.parseInt(wordsTabeliao[0])
////	        	+ " / " + Integer.parseInt(wordsTabeliao[1]) + " / " + Integer.parseInt(wordsTabeliao[2])
////	        	+ " / " + Integer.parseInt(wordsTabeliao[3]) + " / " + Integer.parseInt(wordsTabeliao[4])
////	        	+ " / " + Integer.parseInt(wordsTabeliao[5]) + " / " + wordsTabeliao[6] );
//
//		        if (wordsTabeliao[6].toUpperCase().equals("S")) {
//	        		//		
//			        Integer x1 = Integer.parseInt(wordsTabeliao[0]);
//			        Integer y1 = Integer.parseInt(wordsTabeliao[1]);
//			        Integer x2 = Integer.parseInt(wordsTabeliao[2]);
//			        Integer y2 = Integer.parseInt(wordsTabeliao[3]);
//			        
//			        PdfContentByte cb = writer.getDirectContent();
//		
//			        Rectangle rect = new Rectangle(x1, y1, x2, y2);
//			        
//			        rect.setBorder(Rectangle.BOX);
//			        rect.setBorderWidth(1);
//			        rect.setBackgroundColor(BaseColor.BLACK);			        
//
//			        cb.rectangle(rect);
//			        
//			        // Trata TEXTO ...
//			        Integer x3 = Integer.parseInt(wordsTabeliao[4]);
//			        Integer y3 = Integer.parseInt(wordsTabeliao[5]);
//
//			        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
//			        									BaseFont.CP1252,
//			        									BaseFont.NOT_EMBEDDED);		        
//			        cb.saveState();
//			        cb.beginText();
//			        cb.moveText(x3, y3);
//			        cb.setFontAndSize(bf, 11);
//			        cb.setColorFill(BaseColor.WHITE);
//			        cb.showText(this.scNomeTabeliao);
//			        cb.endText();
//			        cb.restoreState();
//	        	}
//	        }
//	        // ===================================================================
	        
			//
			Boolean indQRCode = true; // Primeira Linha do Arquivo ...
			String lineQRCode = "";
			String line;
			
			while ((line = br.readLine()) != null) {
				//
				if (line.contains("")) continue;
				//
//				System.out.println("Certidão LINE --------------------------------> " + line);
//				
				if (indQRCode) {
					//
					lineQRCode = line.trim();
					indQRCode = false;
					continue;
				}
				//
				contLinha++;

				if (line.isEmpty()) {
					//
//					System.out.println("Certidão LINE EMPTY--------------------------------> " + line);
//
					document.add(new Paragraph(" ", fontYpt));
				} else {
//					line = line.replaceAll(" ","\u00a0");
					
//					Paragraph p = new Paragraph(new Paragraph(line, fontXpt));	
					
//					document.add(new Paragraph(line, fontXpt));
					//
//					document.add(new Phrase(scLineSpace, line, FontFactory.getFont(FontFactory.COURIER,
//											scFntSize, Font.BOLD)));
					//
					Paragraph p = new Paragraph(line, fontXpt);
					p.setLeading(scLineSpace, 0);
					
//					p.setSpacingBefore(scLineSpace);
//					p.setSpacingAfter(scLineSpace);
					
					document.add(p);
					//
				}
				// 
				if (line.contains("www3.tjrj.jus.br/sitepublico"))
					linhaPosQRCODE = contLinha;
				//
				if (line.toUpperCase().contains("CONTINUA ==>")) {
					contLinha = 0;
					document.newPage();
				}
				//
				if (fileXAnt.toUpperCase().contains(".CER")) {
					//
					if (line.contains("SAC: (21) ") && !line.contains("CONTINUA ==>")) {
						//
				        int sizeLnX = this.lnX.length;
				        
				        for (int iX=0; iX < sizeLnX; iX++) {
				        	//
							Paragraph p = new Paragraph(this.lnX[iX], fontZpt);
				        	if (iX == 5)
				        		p = new Paragraph("CERP " + this.numeroCERP, fontXpt);
				        	
							p.setLeading(scLineSpace, 0);
							//
							document.add(p);
				        }
					}
				} else
					if (line.toUpperCase().contains("TOTAL DO ATO"))
						break;
			}
			//
			// Trata posicionamento QRCODE no fim do PDF ...
			// ==============================================
			Integer posY = (contLinha - linhaPosQRCODE) * 10;
			
			if (fileXAnt.toUpperCase().contains(".CER")) posY = posY - 10 ; // MVPR-08042019 ...
			//
//			if (posY < 120) // MVPR-24072019 (Solicit.Rubens!->v1.05i)
				posY = 10;
			// ==============================================

//			System.out.println("MpECartorioRJBean.criaPDF() ...................... ( ContLinha = " + contLinha + 
//						" / CERP = " + this.numeroCERP + " / Pos.Y = " + posY + 
//						" / LinPosQRCODE = " + linhaPosQRCODE + 
//						" / fileX = " + fileX + 
//						" / arquivoSelecionado = " + this.arquivoSelecionado);
			//
			if (!this.numeroCERP.isEmpty()) {
				//
				// String myString =
				// "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" +
				// numCerpX;

				String qrcodeX = "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" + this.numeroCERP;

				BarcodeQRCode qrcode = new BarcodeQRCode(qrcodeX.trim(), 4, 4, null);

				Image qrcodeImage = qrcode.getImage();
				qrcodeImage.scaleAbsolute(80, 80);
				//
				if (fileXAnt.toUpperCase().contains(".CER"))
					qrcodeImage.setAbsolutePosition(420f, posY);
				else
					qrcodeImage.setAbsolutePosition(320f, posY);
				//
				document.add(qrcodeImage);
			}
			//
			if (!lineQRCode.isEmpty()) {
				//
				BarcodeQRCode qrcode = new BarcodeQRCode(lineQRCode.trim(), 4, 4, null);

				Image qrcodeImage = qrcode.getImage();
				qrcodeImage.scaleAbsolute(80, 80);
				//
				if (fileXAnt.toUpperCase().contains(".CER"))
					qrcodeImage.setAbsolutePosition(500f, posY);
				else
					qrcodeImage.setAbsolutePosition(430f, posY);
				//
				document.add(qrcodeImage);
			}
			//
			document.close();
			//
		} catch (Exception e) {
			return "MpECartorioRJBean.criaPDF() - Erro.Exception ! ( e = " + e;
		}
		//	
//		PdfReader pdfReader;
//		try {
//			pdfReader = new PdfReader(fileX);
//			linhaPosQRCODE = capturaPosPDF(pdfReader, "www3.tjrj.jus.br/sitepublico");
//	        System.out.println("MpECartorioRJBean.criaPDF().capturaPosPDF ...................... ( " + 
//	        																linhaPosQRCODE); 
//			pdfReader.close();		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		//
//		System.out.println("MpECartorioRJBean.criaPDF() ...................... ( " + scImpressoraBalcaoAvulsa +
//															" / " +	this.indImpressao + " / " + fileX); 
		if (this.indImpressao) {
			//
			String impressoraPerfil = "";
			String impressoraPerfilTipo = "";
			
			if (this.indUserCert) {
				impressoraPerfil = scImpressoraCertCertidaoA4;
				impressoraPerfilTipo = "scImpressoraCertCertidaoA4";
				
				if (this.arquivoSelecionado.toUpperCase().indexOf(".CER") >= 0) {
					impressoraPerfil = scImpressoraCertXerox;
					impressoraPerfilTipo = "scImpressoraCertXerox";
				}
			} else
				if (this.indUserCanc) {
					impressoraPerfil = scImpressoraCancCertidaoA4;
					impressoraPerfilTipo = "scImpressoraCancCertidaoA4";
				}
			//
			if (impressoraPerfil.isEmpty())
				MpFacesUtil.addInfoMessage("Impressora... Não Configurada ! Contactar Suporte. ( " + 
																						impressoraPerfilTipo);
			else {
				this.printPDF(fileX, impressoraPerfil);

				MpFacesUtil.addInfoMessage("Impressão... efetuada ! ( " + fileXAnt + " / " + impressoraPerfil);
			}
			//
			// this.indImpressao = false;
		}
				
		// Trata Arquivo: XML...
		if (this.arquivoSelecionado.toUpperCase().indexOf(".CER") >= 0) {
			//
	        String arquivoXML = this.arquivoSelecionado.toUpperCase().replace(".CER", ".XML");
			
//	        System.out.println("MpECartorioRJBean.TrataXML(CER) ...................... ( " + 
//	        														this.scPathCertidaoEletronicaCerp + arquivoXML); 
	        //
	    	File fileXML = new File(this.scPathCertidaoEletronicaCerp + arquivoXML);
	    	if (fileXML.exists())
	    		this.atualizaRenameXML_CERP(this.scPathCertidaoEletronicaCerp + arquivoXML, this.numeroCERP);
	    	//
			this.tabViewIndex = 0;
			this.numeroCERP = "";
			
			String arqX = this.arquivoSelecionado;
			this.arquivoSelecionado = "";

			return "Geração Certidão Eletrônica(PDF/XML)... efetuada ! ( " + arqX + " / " + arquivoXML;
		}
		//
		this.tabViewIndex = 1;
		this.numeroCERP = "";

		this.arquivoSelecionado = "";
		//
		return "Geração Certidão Eletrônica(PDF)... efetuada !!! ( " + fileXAnt;
	}

	public String criaEtiquetaPDF(String fileX) {
		//
        Rectangle pagesize = new Rectangle(298f, 105f);

		// Document(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom)
		Document document = new Document(pagesize, 30f, 10f, 2f, 2f);

		Float lineSpacing = 10f;
		Integer contLinha = 0;
		//
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileX), "UTF-8"))) {
			//
			// Trata extensão do PDF !
			fileX = fileX.toLowerCase().replace(".txt", ".pdf");

			PdfWriter.getInstance(document, new FileOutputStream(fileX));

			document.open();
			document.addCreator("MPXDS wwww.mpxds.com.br");
			document.addAuthor("Marcus VPR");

//			Font fontXpt = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD);
			Font font7pt = new Font(FontFamily.TIMES_ROMAN, 7, Font.BOLD);
			Font font9pt = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			//
			Boolean indQRCode = true; // Primeira Linha do Arquivo ...
			String lineQRCode = "";
			String line;
			//
			document.add(new Paragraph(lineSpacing, " ", font7pt));
			//
			while ((line = br.readLine()) != null) {
				//
				if ((fileX.toUpperCase().indexOf("ETIQM") >= 0)
			    ||  (fileX.toUpperCase().indexOf("ETIQV") >= 0))
					assert(true); // nop
				else
					if (line.contains("") || line.isEmpty())
						continue;
				//
//				System.out.println("Etiqueta LINE --------------------------------> " + line);
				if (indQRCode) {
					//
					lineQRCode = line.trim();
					indQRCode = false;
					continue;
				}
				//
				contLinha++;
				//
				if ((fileX.toUpperCase().indexOf("ETIQM") >= 0)
				||  (fileX.toUpperCase().indexOf("ETIQV") >= 0))
					document.add(new Paragraph(lineSpacing, line, font9pt));
				else {
					if (contLinha == 2 || contLinha == 4 || contLinha ==6)
						document.add(new Paragraph(lineSpacing, line, font9pt));
					else
						document.add(new Paragraph(lineSpacing, line, font7pt));
	//							new Phrase(lineSpacing, line, FontFactory.getFont(FontFactory.COURIER, fntSize)))));
				}
				// 
			}
			//
			if (!lineQRCode.isEmpty()) {
				//
				BarcodeQRCode qrcode = new BarcodeQRCode(lineQRCode.trim(), 2, 2, null);

				Image qrcodeImage = qrcode.getImage();
				qrcodeImage.scaleAbsolute(80, 80);
				qrcodeImage.setAbsolutePosition(195f, 15f);
				//
				document.add(qrcodeImage);
			}
			//
		} catch (Exception e) {
			return "MpECartorioRJBean.criaEtiquetaPDF() - Erro.Exception ! ( e = " + e;
		}
		//
		document.close();
		//
		if (this.indImpressao) {
			//
			String impressoraPerfil = "";
			String impressoraPerfilTipo = "";
			
			if (this.indUserCert) {
				impressoraPerfil = scImpressoraCertEtiqueta;
				impressoraPerfilTipo = "scImpressoraCertEtiqueta";
			} else
				if (this.indUserCanc) {
					impressoraPerfil = scImpressoraCancEtiqueta;
					impressoraPerfilTipo = "scImpressoraCancEtiqueta";
				}
			//
			if (impressoraPerfil.isEmpty())
				MpFacesUtil.addInfoMessage("Impressora... Não Configurada ! Contactar Suporte. ( " + 
																						impressoraPerfilTipo);
			else {
				if (scIndPrintJS)
					assert(true); //nop
				else
					this.printPDF(fileX, impressoraPerfil);
				//
				MpFacesUtil.addInfoMessage("Impressão... efetuada ! ( " + fileX + " / " + impressoraPerfil);
			}
			//
			// this.indImpressao = false;
		}
		//
		this.tabViewIndex = 3;

		return "Geração Etiqueta(PDF)... efetuada ! ( " + this.arquivoEtiquetaSelecionado;
	}
	
	public String atualizaRenameXML_CERP(String fileXML, String numeroCERPXML) {
		//
//      System.out.println("MpECartorioRJBean.atualizaXML_CERP() ...................... ( " + 
//        																		fileXML + " / " + numeroCERPXML); 
        // Rubens não utiliza - Retirar (MVPR-08042019) ...
        //
//      String newLine = System.getProperty("line.separator");
		//
//		File outFile = new File(fileXML + ".bk"); 
//		
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) 
//        {
//    		try (BufferedReader br = new BufferedReader(new InputStreamReader(
//    																new FileInputStream(fileXML), "UTF-8"))) {
//    			//
//    			String line;
//    			while ((line = br.readLine()) != null) {
//    				// #PRD#
////    				line = line.replace("\"AQUI DEVE ENTRAR O CERP\"", numCERP);
//    				
//    				if (line.indexOf("#PRD#") >= 0)
//    					line = line.substring(0, line.indexOf("#PRD#") + 5) + numeroCERPXML;
//    				
//    	            bw.write(line + newLine);
//    			}
//    			//
//    		} catch (Exception e) {
//    			MpFacesUtil.addInfoMessage("MpECartorioRJBean.atualizaXML_CERP() - Exception ! ( e = " + e);
//    			return "MpECartorioRJBean.atualizaXML_CERP() - Exception ! ( e = " + e;
//    		}
//        } 
//        catch (IOException e) 
//        {
//        	MpFacesUtil.addInfoMessage("MpECartorioRJBean.atualizaRenameXML_CERP() - IOException ! ( e = " + e);
//			return "MpECartorioRJBean.atualizaRenameXML_CERP() - IOException ! ( e = " + e;
//        }

        // Trata Geração Arquivo XML !
//		File outFileXML = new File(this.scPathCertidaoEletronicaCerp + numeroCERPXML + ".xml", "ISO-8859-1"); 
		File outFileXML = new File(this.scPathCertidaoEletronicaCerp + numeroCERPXML + ".xml"); 
		
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFileXML))) 
        {
    		try (BufferedReader br = new BufferedReader(new InputStreamReader(
    																new FileInputStream(fileXML), "UTF-8"))) {
    			// CertidaoEletronica ...
    			String Id="_???";
    			String CodServico="_???";
    			String IdPedidoCentral="_???";
    			String Software="Gerador XML TopMaster 3735"; //??
    			String Ambiente="PROD"; // ??
    			
    			// Certidao ...
    			String DataPratica="_???"; 
    			String Selo="_???";
    			String Aleatorio="_???";
    			String TipoCertidao="_???";
    			String NumeroAto="_???";
    			String QuantidadeDistribuicao="_???";
    			String QtdFolhasExcedentes="_???";
    			String DataInicioEficacia="_???";
    			String DataFimEficacia="_???";
    			
    			// Participante...(1?) Rubens mais Meninas confirmaram só 1(Hum) !
    			Boolean indParticipantes = true;
    			String Tipo="_???";
    			String TipoPessoa="_???";
    			String Nome="_???";
    			String CPFCNPJ="_???";

    			// Emolumentos... 
    			String TipoCobranca="_???"; 
    			String ValorTotalEmolumentos="_???"; 
    			String FETJ="_???";
    			String FUNDPERJ="_???";
    			String FUNPERJ="_???";
    			String FUNARPEN="_???";
    			String RESSAG="_???";
    			
    			// ItemEmolumento..(2?) Rubens mais Meninas confirmaram só 2(dois) !
    			String Tabela="_???";
    			String Item="_???";
    			String SubItem="_???";
    			String Quantidade="_???";
    			
    			// Entrada: 19314494073.XML
//                        1         2         3         4         5         6         7         8         9
//              0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890
//    			CertidaoEletronica#8633#731#PRD#8b6e0905-2a3f-4f79-9e06-61179f0f1157	
//    			Certidao#19/03/2019#ECWE83968#WIU#DCDPT#2019314494073#0#1#19/03/2019#16/06/2019
//    			Participante#196#J#KROY CONSTRUCOES EMPREENDIMENTOS E PARTICIPACOES LTDA#30524300000130
//    			Emolumentos#CC#62.59#12.51#3.12#3.12#2.50#.82
//    			ItemEmolumento#19#8##1
//    			ItemEmolumento#16#1##24
    			
    			// Saida: 8b6e0905-2a3f-4f79-9e06-61179f0f1157.xml
    			
//    			<?xml version="1.0" encoding="ISO-8859-1"?>
//    			<RegistroDistribuicao VersaoLayout="2.15"> ===> MVPR-2020/10/09 -> 2,16 !
//    			<CertidaoEletronica Id="4805" CodServico="731" IdPedidoCentral="0c581de1-1cb1-4877-90f4-01f5c243ce94" Software="Gerador XML TopMaster 3735" Ambiente="PRD">	
//    				<Certidao DataPratica="18/03/2019" Selo="ECWE83897" Aleatorio="PYM" TipoCertidao="DCDPT" NumeroAto="2019313492731"  
//    				QuantidadeDistribuicao="0" QtdFolhasExcedentes="1" DataInicioEficacia="18/03/2019" DataFimEficacia="15/06/2019">
//    					<Participantes> 
//    						<Participante Tipo="196" TipoPessoa="J" Nome="COMALTA COMERCIO DE ALIMENTOS LTDA ME" CPFCNPJ="10787920000155" />
//    			</Participantes>
//    					<Emolumentos TipoCobranca="CC" ValorTotalEmolumentos="62.59" FETJ="12.51" FUNDPERJ="3.12" FUNPERJ="3.12" FUNARPEN="2.50" RESSAG=".82">
//    						<ItemEmolumento Tabela="19" Item="8" SubItem="" Quantidade="1"  />
//    						<ItemEmolumento Tabela="16" Item="1" SubItem="" Quantidade="24"  />
//    			</Emolumentos>
//    				</Certidao> 
//    			</CertidaoEletronica>
//    			</RegistroDistribuicao>
    			
    			//
	            bw.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
//	            bw.write("<RegistroDistribuicao VersaoLayout=\"2.16\">\n"); // MR-29022021

	            bw.write("<RegistroDistribuicao VersaoLayout=\"2.17\">\n");
    			//
    			String msgE = "";
    			String line = "";
    			Integer pos = 0;
    			//
    			while ((line = br.readLine()) != null) {
    				//
    				pos = line.indexOf("CertidaoEletronica#");
    				if (pos >= 0) {
    					//
    					String[] params = line.substring(pos + 19).split("#");

    					if (params.length == 4) {
    						//
    						Id = params[0];
    						CodServico = params[1];
    						Ambiente = params[2];
    						IdPedidoCentral = numeroCERPXML; // params[3]; MVPR-22032019
    					} else {
    						msgE = msgE + "(CertidaoEletronica Param.<> 4)";
    						break;
    					}
    					//
    					bw.write(" <CertidaoEletronica Id=\"" + Id + "\""
    							+ " CodServico=\"" + CodServico + "\""
    							+ " IdPedidoCentral=\"" + IdPedidoCentral.trim() + "\""
    							+ " Software=\"" + Software + "\""
    							+ " Ambiente=\"" + Ambiente + "\">\n"
    							);
    					//
    					continue;
    				}
    				//
    				pos = line.indexOf("Certidao#");
    				if (pos >= 0) {
    					//
    					String[] params = line.substring(pos + 9).split("#");
    					
    					if (params.length == 9) {
    						//
    						DataPratica = params[0];
    						Selo = params[1];
    						Aleatorio = params[2];
    						TipoCertidao = params[3];
    						NumeroAto = params[4];
    						QuantidadeDistribuicao = params[5];
    						QtdFolhasExcedentes = params[6];
    						DataInicioEficacia = params[7];
    						DataFimEficacia = params[8];
    					} else {
    						msgE = msgE + "(Certidao Param.<> 9)";
    						break;
    					}
    					//
    					bw.write("  <Certidao DataPratica=\"" + DataPratica + "\""
    							+ " Selo=\"" + Selo + "\""
    							+ " Aleatorio=\"" + Aleatorio + "\""
    							+ " TipoCertidao=\"" + TipoCertidao + "\""
    							+ " NumeroAto=\"" + NumeroAto + "\"\n"
    							+ "     QuantidadeDistribuicao=\"" + QuantidadeDistribuicao + "\""
    							+ " QtdFolhasExcedentes=\"" + QtdFolhasExcedentes + "\""
    							+ " DataInicioEficacia=\"" + DataInicioEficacia + "\""
    							+ " DataFimEficacia=\"" + DataFimEficacia.trim() + "\">\n"
    							);
    					//
    					continue;
    				}
    				//
    				pos = line.indexOf("Participante#");
    				if (pos >= 0) {
    					//
    					String[] params = line.substring(pos + 13).split("#");
    					
    					if (params.length == 4) {
    						//
    						Tipo = params[0];
    						TipoPessoa = params[1];
    						Nome = params[2];
    						CPFCNPJ = params[3];
    					} else {
    						msgE = msgE + "(Certidao Param.<> 4)";
    						break;
    					}
    					//
    					if (indParticipantes) {
    						indParticipantes = false;
        					bw.write("   <Participantes>\n");
    					}
    					//
    					bw.write("    <Participante Tipo=\"" + Tipo + "\""
    							+ " TipoPessoa=\"" + TipoPessoa + "\""
    							+ " Nome=\"" + Nome.replace("&", "&amp;") + "\""
    							+ " CPFCNPJ=\"" + CPFCNPJ.trim() + "\" />\n"
    							);
    					//
    					continue;
    				}
    				//
    				pos = line.indexOf("Emolumentos#");
    				if (pos >= 0) {
    					//
    					String[] params = line.substring(pos + 12).split("#");
    					
    					if (params.length == 7) {
    						//
    						TipoCobranca = params[0];
    						ValorTotalEmolumentos = params[1];
    						FETJ = params[2];
    						FUNDPERJ = params[3];
    						FUNPERJ = params[4];
    						FUNARPEN = params[5];
    						RESSAG = params[6];
    					} else {
    						msgE = msgE + "(Certidao Param.<> 7)";
    						break;
    					}
    					//
    					if (!indParticipantes) {
        					bw.write("   </Participantes>\n");
    					}
    					//
    					bw.write("   <Emolumentos TipoCobranca=\"" + TipoCobranca + "\""
    							+ " ValorTotalEmolumentos=\"" + ValorTotalEmolumentos + "\""
    							+ " FETJ=\"" + FETJ + "\""
    							+ " FUNDPERJ=\"" + FUNDPERJ + "\""
    							+ " FUNPERJ=\"" + FUNPERJ + "\""
    							+ " FUNARPEN=\"" + FUNARPEN + "\""
    							+ " RESSAG=\"" + RESSAG.trim() + "\">\n"
    							);
    					//
    					continue;
    				}
    				//
    				//
    				pos = line.indexOf("ItemEmolumento#");
    				if (pos >= 0) {
    					//
    					String[] params = line.substring(pos + 15).split("#");
    					
    					if (params.length == 4) {
    						//
    						Tabela = params[0];
    						Item = params[1];
    						SubItem = params[2];
    						Quantidade = params[3];
    					} else {
    						msgE = msgE + "(Certidao Param.<> 4)";
    						break;
    					}
    					//
    					bw.write("    <ItemEmolumento Tabela=\"" + Tabela + "\""
    							+ " Item=\"" + Item + "\""
    							+ " SubItem=\"" + SubItem + "\""
    							+ " Quantidade=\"" + Quantidade.trim() + "\" />\n"
    							);
    					//
    					continue;
    				}
    			}
    			//
    			if (!msgE.isEmpty()) {
    				//
    				return "Mensagem : " + msgE;
    			}
    			//
	            bw.write("   </Emolumentos>\n");
	            bw.write("  </Certidao>\n");
	            bw.write(" </CertidaoEletronica>\n");
	            bw.write("</RegistroDistribuicao>\n");
    			//
    		} catch (Exception e) {
    			return "MpECartorioRJBean.atualizaXML_CERP() - Exception ! ( e = " + e;
    		}
        } 
        catch (IOException e) 
        {
			return "MpECartorioRJBean.atualizaRenameXML_CERP() - IOException ! ( e = " + e;
        }
        
		// Trata movimentação arquivos Gerado para pasta Disquete !!! MPVR-19032019 ...
		if (null == this.scPathArquivoDisquete || this.scPathArquivoDisquete.isEmpty())
			assert(true); // nop
		else
			this.moveArquivoDisquete(this.scPathCertidaoEletronicaCerp, numeroCERPXML + ".xml");
        // 
		return "Atualizão XML/CERP... efetuada ! ( ARQUIVO/CERP = " + fileXML + " / " + numeroCERPXML ;
	}
	
	public void listaPathBalcao() {
		//
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathCertidaoEletronica");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathCertidaoEletronica' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathCertidaoEletronica = mpSistemaConfig.getValor();
		//
    	this.listaPath(new File(this.scPathCertidaoEletronica));
	}
	
	public void listaPathEtiqueta() {
		//
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathEtiqueta");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathEtiqueta' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathEtiqueta = mpSistemaConfig.getValor();
		//
    	this.listaPathEtiqueta(new File(this.scPathEtiqueta));
	}
	
	public void listaPathPedido() {
		//
		MpSistemaConfig mpSistemaConfig = this.mpSistemaConfigService.findByParametro("pathCertidaoEletronica");
		if (null == mpSistemaConfig) {
			//
			MpFacesUtil.addInfoMessage("Parâmetro 'pathCertidaoEletronica' em 'SistemaConfig' ... não existe !");
			return;
		} else
			this.scPathCertidaoEletronica = mpSistemaConfig.getValor();
		//
    	this.listaPath(new File(this.scPathCertidaoEletronica));
	}
	
	public void listaPathBalcaoSel() {
		//
		if (this.arquivoBalcaoSelecionadoList.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Nenhuma Arquivo de Balcão...selecionado !");
			return;
		}
		//
		String msgBalcao = "";
		
		for (String balcaoSel : this.arquivoBalcaoSelecionadoList) {
			//
			this.arquivoBalcaoSelecionado = balcaoSel;
			
			this.arquivoCE = this.arquivoBalcaoSelecionado;
			this.trataArquivoCE();
			this.mpTrataCertidaoLog("B"); // Balcão ...
			//
			msgBalcao = msgBalcao + " " + balcaoSel;
		}
		//
		MpFacesUtil.addInfoMessage("Balcão Arquivo(s) Selecionado(s)... ! ( " + msgBalcao);
	}
	
	public void listaPathEtiquetaSel() {
		//
		if (this.arquivoEtiquetaSelecionadoList.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Nenhuma Arquivo de Etiqueta...selecionado !");
			return;
		}
		//
		String msgEtiqueta = "";
		
		for (String etiquetaSel : this.arquivoEtiquetaSelecionadoList) {
			//
			this.arquivoEtiquetaSelecionado = etiquetaSel;
			
			this.trataArquivoEtiqueta();
			//
			msgEtiqueta = msgEtiqueta + " " + etiquetaSel;
		}
		//
		if (this.scIndPrintJS)
			this.printEtiquetaAll();
		//
		MpFacesUtil.addInfoMessage("Etiqueta(s) Selecionada(s)... ! ( " + msgEtiqueta);
	}
	
	public void listaPathPedidoSel() {
		//
		if (this.arquivoPedidoSelecionadoList.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Nenhum Pedido...selecionado !");
			return;
		}
		//
		String msgPedido = "";
		
		for (Pedido pedidoSel : this.arquivoPedidoSelecionadoList) {
			//
			this.arquivoPedidoSelecionado = pedidoSel.getNuPedido() + ".CER";
			this.arquivoPedidoSelecionado = this.arquivoPedidoSelecionado.substring(2);

//			this.arquivoPedidoSelecionado = capturaFilePathNome(filePathX, pedidoSel.getNmBusca().getValue());
//			if (this.arquivoPedidoSelecionado.isEmpty()) {
			if (new File(this.scPathCertidaoEletronicaCerp + this.arquivoPedidoSelecionado).exists())
				assert(true); // nop
			else {
				//
				MpFacesUtil.addInfoMessage("Arquivo '.CERP' relacionado... não existe !! ( Número : " + 
											this.scPathCertidaoEletronicaCerp + this.arquivoPedidoSelecionado);
				continue;
			}
			//
			this.numeroCERP = pedidoSel.getIdCerp().getValue();
			
			this.arquivoSelecionado = this.arquivoPedidoSelecionado;
			this.arquivoCE = this.arquivoSelecionado;
			
			this.trataArquivoCE();
			this.mpTrataCertidaoLog("P"); // Pedido ...
			//
			msgPedido = msgPedido + " " + this.arquivoPedidoSelecionado;
		}
		//
		MpFacesUtil.addInfoMessage("Pedido Arquivo(s) Selecionado(s)... ! ( " + msgPedido);
	}
	
	public String listaPath(File filePath) {
		//
		this.arquivoBalcaoList = new ArrayList<String>();
		
//      System.out.println("listaPath  -------------->" + this.scPathCertidaoEletronica);
		
		File[] filesList = filePath.listFiles();
		
	    for (File f : filesList) {
	        if (f.isDirectory() && !f.isHidden()) {
//	            System.out.println("Directoy name is  --------------> " + f.getName());
	            continue; // listaPath(f);
	        }
	        if( f.isFile() ) {
	        	//
	        	if (f.getName().toLowerCase().contains(".2018.txt") || 
		        	f.getName().toLowerCase().contains(".2019.txt") || 
		        	f.getName().toLowerCase().contains(".2020.txt") || 
		        	f.getName().toLowerCase().contains(".2021.txt") || 
		        	f.getName().toLowerCase().contains(".2022.txt") || 
		        	f.getName().toLowerCase().contains(".2023.txt") || 
		        	f.getName().toLowerCase().contains(".2024.txt") || 
		        	f.getName().toLowerCase().contains(".2025.txt") || 
		        	f.getName().toLowerCase().contains(".2026.txt") || 
		        	f.getName().toLowerCase().contains(".2027.txt") || 
		        	f.getName().toLowerCase().contains(".2028.txt") || 
		        	f.getName().toLowerCase().contains(".2029.txt") || 
		        	f.getName().toLowerCase().contains(".2029.txt") || 
		        	f.getName().toLowerCase().contains(".2030.txt") || 
		        	(f.getName().substring(0,5).equals("18000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("19000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("19001") && f.getName().toLowerCase().contains(".txt")) || // MVPR-09.08.2019
		        	(f.getName().substring(0,5).equals("20000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("21000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("21001") && f.getName().toLowerCase().contains(".txt")) || // MVPR-24.09.2021
		        	(f.getName().substring(0,5).equals("21002") && f.getName().toLowerCase().contains(".txt")) || // MVPR-24.09.2021
		        	(f.getName().substring(0,5).equals("22000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("23000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("24000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("25000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("26000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("27000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("28000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("29000") && f.getName().toLowerCase().contains(".txt")) || 
		        	(f.getName().substring(0,5).equals("30000") && f.getName().toLowerCase().contains(".txt"))) {
	        		//
	        		if (this.isIndAdmin())
	        			this.arquivoBalcaoList.add(f.getName());
	        		else {
	        			Boolean indCanc = false;
	        			if (f.getName().toLowerCase().contains("cebai")
	        			|| (f.getName().toLowerCase().contains("ceesp"))) // Rubens MVPR-15052019 !
	        				indCanc = true;
	        			//
		        		if ((this.isIndUserCanc() && indCanc) 
		        		||	(this.isIndUserCert() && !indCanc))
		        			this.arquivoBalcaoList.add(f.getName());
	        		}
	        	}
	        	//
//	            System.out.println("File name is  -------------->" + f.getName());
	        }
	    }
//	    System.out.println("listaPath  -------------->" + this.arquivoBalcaoList.size());
	    //
	    return "";
	}
	
	public String listaPathEtiqueta(File filePath) {
		//
		this.arquivoEtiquetaList = new ArrayList<String>();
		
//      System.out.println("listaPath  -------------->" + this.scPathEtiqueta);
		
		File[] filesList = filePath.listFiles();
		
	    for (File f : filesList) {
	        if (f.isDirectory() && !f.isHidden()) {
//	            System.out.println("Directoy name is  --------------> " + f.getName());
	            continue; // listaPath(f);
	        }
	        if( f.isFile() ) {
	        	//
	        	if (f.getName().toLowerCase().contains(".txt"))
	        		if (this.isIndAdmin())
	        			this.arquivoEtiquetaList.add(f.getName());
	        		else {
	        			Boolean indCanc = false;
	        			if ((f.getName().toLowerCase().contains("cebai"))
	    	        	||  (f.getName().toLowerCase().contains("ceesp")) // Rubens MVPR-15052019 !
	        			|| 	(f.getName().length() == 13) // Vrf. c/Ruben!? 10183.2019.txt -> Sol.: PastaEspecif.
			        	||  (f.getName().toLowerCase().contains(".2020.txt")) 
			        	||  (f.getName().toLowerCase().contains(".2021.txt")) 
			        	||  (f.getName().toLowerCase().contains(".2022.txt")) 
			        	||  (f.getName().toLowerCase().contains(".2023.txt")) 
			        	||  (f.getName().toLowerCase().contains(".2024.txt")) 
			        	||  (f.getName().toLowerCase().contains(".2025.txt")) 
		        		|| 	(f.getName().length() == 14))
	        				indCanc = true;
	        			
	        			// MVPR-11042019
	        			if (f.getName().toLowerCase().contains("etiq"))
		        			indCanc = false;
	        			//
		        		if ((this.isIndUserCanc() && indCanc) 
		        		||	(this.isIndUserCert() && !indCanc))
		        			this.arquivoEtiquetaList.add(f.getName());
	        		}
//	            System.out.println("File name is  -------------->" + f.getName());
	        }
	    }
	    //
	    return "";
	}
	
	public void mpListenerBalcao(ActionEvent event) {
		//
		this.arquivoSelecionado = (String)event.getComponent().getAttributes().get("arquivoBalcaoSelecionado");

//		System.out.println("MpECartorioRJBean.mpListenerBalcao()  --------------> " + this.arquivoSelecionado);
	}		
	public void mpListenerBalcaoEdita(ActionEvent event) {
		//
		this.arquivoSelecionadoEdita = (String)event.getComponent().getAttributes().get("arquivoBalcaoSelecionadoEdita");

//		System.out.println("MpECartorioRJBean.mpListenerBalcao()  --------------> " + this.arquivoSelecionado);
	}		
	public void mpGeraBalcao() {
		//
//		System.out.println("MpECartorioRJBean.mpGeraBalcao  --------------> " + this.arquivoSelecionado);

		if (null == this.arquivoSelecionado) return;
		//
		this.numeroCERP = "";

		this.arquivoCE = this.arquivoSelecionado;
		
		this.trataArquivoCE();
		this.mpTrataCertidaoLog("B"); // Balcão ...
	}
	public void mpEditaBalcao() {
		//
//		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + this.arquivoSelecionado);

		if (null == this.arquivoSelecionadoEdita) return;
		//
		this.numeroCERP = "";
		//
        this.textoEditor = "";
//        this.textoEditorX = "Teste";

        try {
//			this.textoEditor = new String(Files.readAllBytes(Paths.get(this.scPathCertidaoEletronica + 
//																		this.arquivoSelecionado)), "UTF-8");
//			Scanner scanner = new Scanner( new File(this.scPathCertidaoEletronica + 
//																		this.arquivoSelecionado), "UTF-8" );
//			this.textoEditor = scanner.useDelimiter("\\A").next();
//			scanner.close(); // Put this call in a finally block
//			this.textoEditor = this.textoEditor.substring(0, 200);
//			
		    File file = new File(this.scPathCertidaoEletronica + this.arquivoSelecionadoEdita);
		    
//		    StringBuilder fileContents = new StringBuilder((int)file.length());        
//		    //
//		    String linhaX = "";
//		    Integer cntX = 0;
//		    //
//		    try (Scanner scanner = new Scanner(file)) {
//		    	//
//		        while(scanner.hasNextLine()) {
//		        	cntX++;
////		        	if (cntX > 20) break;
//		    		//
//		    		linhaX = scanner.nextLine(); // + System.lineSeparator();
//		    		
//		    		linhaX = linhaX.replaceAll("\\r\\n", " ");
//		    		linhaX = linhaX.replaceAll("\\r", " ");
//		    		linhaX = linhaX.replaceAll("\\n", " ");
//		    		
//		    		linhaX = linhaX.replaceAll("\r\n", " ");
//		    		linhaX = linhaX.replaceAll("\r", " ");
//		    		linhaX = linhaX.replaceAll("\n", " ");
//		    		
//		    		linhaX = linhaX.replaceAll("", " ");
//		    		
//		    		linhaX = linhaX.replaceAll("\n", System.getProperty("line.separator"));
////		    		linhaX = StringUtils.chomp(linhaX);
//		    		if (linhaX.length() > 0)
//		    			linhaX = linhaX.substring(0, linhaX.length()) + System.lineSeparator();
//		    		//
////		    		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + cntX + " / " + linhaX);
//		    		
//		            fileContents.append(linhaX);
//		        }
//		        this.textoEditor = fileContents.toString();
//				//
//			    scanner.close();
//		    }
	        //
	        InputStream inputStream = new FileInputStream(file);
	        
	        StringBuilder resultStringBuilder = new StringBuilder();
	        
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	        	//
			    Integer cntX = 0;
	            String line;

	            while ((line = br.readLine()) != null) {
	            	//
//	            	line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
//	            	line = Normalizer.normalize(line, Form.NFD);
//	            	line = Normalizer.normalize(line, Form.NFC);
//	            	line = Normalizer.normalize(line, Form.NFKC);
//	            	line = Normalizer.normalize(line, Form.NFKD);
//	            	line = line.replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-zA-Z0-9\\s]", "");
	                
	            	line = line.replaceAll("\\P{Print}", ""); // replaceAll("[^a-zA-Z0-9\\s+]", "");
	            	
	                resultStringBuilder.append(line).append("\n");
	                
	                cntX++;
//	                System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + cntX + " / " + line);
	            }
	            //
	            this.textoEditor = resultStringBuilder.toString();
	            //
			}
		    //
//	        Path path = Paths.get(getClass().getClassLoader().getResource(this.scPathCertidaoEletronica + 
//	        																this.arquivoSelecionadoEdita).toURI());	        	          
//	        Stream<String> lines = Files.lines(path);
//	        this.textoEditor = lines.collect(Collectors.joining("\n"));
//	        lines.close();
	        
//	        FileInputStream fis = new FileInputStream(this.scPathCertidaoEletronica + this.arquivoSelecionadoEdita);
//	        this.textoEditor = IOUtils.toString(fis, "UTF-8");
	        //
		} catch (IOException e) { //  | URISyntaxException e) {
			MpFacesUtil.addInfoMessage("Error ... Arquivo Balcão selecionado ! ( e = " + e);
		}
        //
//		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> TextoEditor : " + this.textoEditor);
	}
	public void mpSalvaEditaBalcao() {
		//
//		System.out.println("MpECartorioRJBean.mpSalvaEditaBalcao  --------------> " + this.arquivoSelecionadoEdita +
//																						" / " + this.textoEditor);
		if (this.textoEditor.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Texto Arquivo Balcão... VAZIO !");
			return;
		}
		//
		try {
			this.textoEditor = this.textoEditor.replaceAll("<p>", "");
			this.textoEditor = this.textoEditor.replaceAll("</p>", "");
			
			Files.write(Paths.get(this.scPathCertidaoEletronica + this.arquivoSelecionadoEdita), 
																					this.textoEditor.getBytes());
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Error Gravação... Arquivo Balcão selecionado ! ( e = " + e);
		}
//		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + this.textoEditor);
	}

	public void mpListenerPedidoEdita(ActionEvent event) {
		//
		this.numeroPedidoSelecionado = (Long)event.getComponent().getAttributes().get("numeroPedidoSelecionado");
		this.numeroCERP = (String)event.getComponent().getAttributes().get("numeroCERPSelecionado");
		//
		if (null == this.numeroPedidoSelecionado) {
			//
			MpFacesUtil.addInfoMessage("Error ... Pedido selecionado !");
			return;
		}

		if (null == this.numeroCERP) {
			//
			MpFacesUtil.addInfoMessage("Error ... CERP selecionado !");
			return;
		}

		this.arquivoSelecionadoEditaP = this.numeroPedidoSelecionado.toString() + ".CER";
		this.arquivoSelecionadoEditaP = this.arquivoSelecionadoEditaP.substring(2);
		//
//		System.out.println("MpECartorioRJBean.mpListenerPedidoEdita()  --------------> " + 
//																					this.arquivoSelecionadoEditaP);
	}		
	public void mpEditaPedido() {
		//
//		System.out.println("MpECartorioRJBean.mpEditaPedido  --------------> " + this.arquivoSelecionadoEditaP);
		//
		if (null == this.arquivoSelecionadoEditaP) return;
		//
		this.numeroCERP = "";
		//
        this.textoEditorP = "";
//        this.textoEditorX = "Teste";

        try {
//			this.textoEditor = new String(Files.readAllBytes(Paths.get(this.scPathCertidaoEletronica + 
//																		this.arquivoSelecionado)), "UTF-8");
//			Scanner scanner = new Scanner( new File(this.scPathCertidaoEletronica + 
//																		this.arquivoSelecionado), "UTF-8" );
//			this.textoEditor = scanner.useDelimiter("\\A").next();
//			scanner.close(); // Put this call in a finally block
//			this.textoEditor = this.textoEditor.substring(0, 200);
//			
		    File file = new File(this.scPathCertidaoEletronicaCerp + this.arquivoSelecionadoEditaP);
		    
		    if (file.exists())
		    	assert(true); // nop
		    else {
		    	//
		    	MpFacesUtil.addInfoMessage("Error ... Arquivo Pedido selecionado ! ( " + this.arquivoSelecionadoEditaP);
		    }
		    
//		    StringBuilder fileContents = new StringBuilder((int)file.length());        
//		    //
//		    String linhaX = "";
//		    Integer cntX = 0;
//		    //
//		    try (Scanner scanner = new Scanner(file)) {
//		    	//
//		        while(scanner.hasNextLine()) {
//		        	cntX++;
////		        	if (cntX > 20) break;
//		    		//
//		    		linhaX = scanner.nextLine(); // + System.lineSeparator();
//		    		
//		    		linhaX = linhaX.replaceAll("\\r\\n", " ");
//		    		linhaX = linhaX.replaceAll("\\r", " ");
//		    		linhaX = linhaX.replaceAll("\\n", " ");
//		    		
//		    		linhaX = linhaX.replaceAll("\r\n", " ");
//		    		linhaX = linhaX.replaceAll("\r", " ");
//		    		linhaX = linhaX.replaceAll("\n", " ");
//		    		
//		    		linhaX = linhaX.replaceAll("", " ");
//		    		
//		    		linhaX = linhaX.replaceAll("\n", System.getProperty("line.separator"));
////		    		linhaX = StringUtils.chomp(linhaX);
//		    		if (linhaX.length() > 0)
//		    			linhaX = linhaX.substring(0, linhaX.length()) + System.lineSeparator();
//		    		//
////		    		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + cntX + " / " + linhaX);
//		    		
//		            fileContents.append(linhaX);
//		        }
//		        this.textoEditor = fileContents.toString();
//				//
//			    scanner.close();
//		    }
	        //
	        InputStream inputStream = new FileInputStream(file);
	        
	        StringBuilder resultStringBuilder = new StringBuilder();
	        
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	        	//
			    Integer cntX = 0;
	            String line;

	            while ((line = br.readLine()) != null) {
	            	//
//	            	line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
//	            	line = Normalizer.normalize(line, Form.NFD);
//	            	line = Normalizer.normalize(line, Form.NFC);
//	            	line = Normalizer.normalize(line, Form.NFKC);
//	            	line = Normalizer.normalize(line, Form.NFKD);
//	            	line = line.replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-zA-Z0-9\\s]", "");
	                
	            	line = line.replaceAll("\\P{Print}", ""); // replaceAll("[^a-zA-Z0-9\\s+]", "");
	            	
	                resultStringBuilder.append(line).append("\n");
	                
	                cntX++;
//	                System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + cntX + " / " + line);
	            }
	            //
	            this.textoEditorP = resultStringBuilder.toString();
	            //
			}
		    //
//	        Path path = Paths.get(getClass().getClassLoader().getResource(this.scPathCertidaoEletronica + 
//	        																this.arquivoSelecionadoEdita).toURI());	        	          
//	        Stream<String> lines = Files.lines(path);
//	        this.textoEditor = lines.collect(Collectors.joining("\n"));
//	        lines.close();
	        
//	        FileInputStream fis = new FileInputStream(this.scPathCertidaoEletronica + this.arquivoSelecionadoEdita);
//	        this.textoEditor = IOUtils.toString(fis, "UTF-8");
	        //
		} catch (IOException e) { //  | URISyntaxException e) {
			MpFacesUtil.addInfoMessage("Error ... Arquivo Pedido selecionado ! ( e = " + e);
		}
        //
//		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> TextoEditor : " + this.textoEditor);
	}
	public void mpSalvaEditaPedido() {
		//
//		System.out.println("MpECartorioRJBean.mpSalvaEditaBalcao  --------------> " + this.arquivoSelecionadoEdita +
//																						" / " + this.textoEditor);
		if (this.textoEditorP.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Texto Arquivo Pedido... VAZIO !");
			return;
		}
		//
		try {
			this.textoEditorP = this.textoEditorP.replaceAll("<p>", "");
			this.textoEditorP = this.textoEditorP.replaceAll("</p>", "");
			
			Files.write(Paths.get(this.scPathCertidaoEletronicaCerp + this.arquivoSelecionadoEditaP), 
																					this.textoEditorP.getBytes());
		} catch (IOException e) {
			MpFacesUtil.addInfoMessage("Error Gravação... Arquivo Pedido selecionado ! ( e = " + e);
		}
//		System.out.println("MpECartorioRJBean.mpEditaBalcao  --------------> " + this.textoEditor);
	}
	
	public void mpListenerEtiqueta(ActionEvent event) {
		//
		this.arquivoEtiquetaSelecionadoList = new ArrayList<String>();
		
		this.arquivoEtiquetaSelecionado = (String)event.getComponent().getAttributes().
																				get("arquivoEtiquetaSelecionado");

//		System.out.println("MpECartorioRJBean.mpListenerEtiqueta()  -----------> " + this.arquivoEtiquetaSelecionado);
	}		
	public void mpGeraEtiqueta() {
		//
//		System.out.println("MpECartorioRJBean.mpGeraEtiqueta  ------------> " + this.arquivoEtiquetaSelecionado);

		if (null == this.arquivoEtiquetaSelecionado) return;
		//
		this.numeroCERP = "";
		//
		this.trataArquivoEtiqueta();
		this.mpTrataCertidaoLog("E"); // Etiqueta ...
	}

	public void mpListenerPedido(ActionEvent event) {
		//
		this.numeroPedidoSelecionado = (Long)event.getComponent().getAttributes().get("numeroPedidoSelecionado");
		this.nomePedidoSelecionado = (String)event.getComponent().getAttributes().get("nomePedidoSelecionado");
		
		this.numeroCERP = (String)event.getComponent().getAttributes().get("numeroCERPSelecionado");

//		System.out.println("MpECartorioRJBean.mpListenerPedido()  --------------> " + this.numeroPedidoSelecionado +
//												" / " + this.numeroCERP + " / " + this.nomePedidoSelecionado);
	}		
	public void mpGeraPedido() {
		//
		System.out.println("mpGeraPedido  -------------->" + this.arquivoSelecionado + " / " + this.numeroCERP +
				" / " +	this.numeroPedidoSelecionado + " / " + this.nomePedidoSelecionado);

		if (null == this.numeroCERP || this.numeroCERP.isEmpty()) return;
		//
		this.arquivoSelecionado = capturaFilePath(new File(this.scPathCertidaoEletronicaCerp), 
																		this.numeroPedidoSelecionado.toString());
//		this.arquivoSelecionado = capturaFilePathNome(new File(this.scPathCertidaoEletronicaCerp), 
//																		this.nomePedidoSelecionado.toString());
		if (this.arquivoSelecionado.isEmpty()) {
			//
			MpFacesUtil.addInfoMessage("Arquivo '.CERP' relacionado... não existe ! ( Número = " + 
																		this.numeroPedidoSelecionado.toString());
//			MpFacesUtil.addInfoMessage("Arquivo .CERP relacionado... não existe ! ( Nome = " + 
//																					this.nomePedidoSelecionado);
			return;
		}
		//
		this.arquivoCE = this.arquivoSelecionado; 
		//
		this.trataArquivoCE();
		this.mpTrataCertidaoLog("P"); // Pedido
	}
	
	public String mpGeraPedidoX(String pedido, String numeroCERPX) {
		//
		MpFacesUtil.addInfoMessage("Pedido relacionado ! ( ID = " + pedido + " / " + numeroCERPX);
		System.out.println("mpGeraPedidoX.Pedido relacionado ! ( ID = " + pedido + " / " + numeroCERPX);

		this.numeroCERP = numeroCERPX;
		//
		this.arquivoSelecionado = capturaFilePath(new File(this.scPathCertidaoEletronicaCerp), pedido);	
		//
		this.arquivoCE = this.arquivoSelecionado; 
		//
		this.trataArquivoCE();
		this.mpTrataCertidaoLog("P"); // Pedido
		
		return pedido;
	}	
	
	public String capturaFilePath(File filePath, String numeroPedido) {
		//
//      System.out.println("listaPath  -------------->" + this.scPathCertidaoEletronica);
		
		File[] filesList = filePath.listFiles();
		
	    for (File f : filesList) {
	        if (f.isDirectory() && !f.isHidden()) {
//	            System.out.println("Directoy name is  -------------->" + f.getName());
	            listaPath(f);
	        }
	        if( f.isFile() ) {
	        	//
//	            System.out.println("File name is  --------------> " + f.getName() + " / " + 
//	            																	numeroPedido.trim().substring(2));
	        	// Trata número Pedido/Ato: 2019128395758 -> 19128395758 ...
	        	if (f.getName().indexOf(numeroPedido.trim().substring(2)) >= 0) {
	        		//
	        		return f.getName();
	        	}
//	            System.out.println("File name is  -------------->" + f.getName());
	        }
	    }
	    //
	    return "";
	}
	
	public String capturaFilePathNome(File filePath, String nomeX) {
		//
//      System.out.println("listaPath  -------------->" + this.scPathCertidaoEletronica);
		
		File[] filesList = filePath.listFiles();
		
	    for (File f : filesList) {
	        if (f.isDirectory() && !f.isHidden()) {
//	            System.out.println("Directoy name is  -------------->" + f.getName());
	            listaPath(f);
	        }
	        if( f.isFile() ) {
	        	//
//	            System.out.println("File name is  --------------> " + f.getName() + " / " + 
//	            																	numeroPedido.trim().substring(2));
	        	//
	        	// Trata número Pedido/Ato: 2019128395758 -> 19128395758 ...
	        	if (f.getName().toUpperCase().contains(".CER"))
	        		if (procuraArquivoNome(f.getPath(), nomeX)) {
	        			//
	        			return f.getName();
	        		}
//	            System.out.println("File name is  -------------->" + f.getName());
	        }
	    }
	    //
	    return "";
	}
		
	public void mpTrataCertidaoLog(String tipoGeracao) {
		//
		MpCertidaoLog mpCertidaoLog = mpCertidaoLogService.findByDataMovimento(sdf.format(new Date()));
		if (null ==  mpCertidaoLog) {
			//
			mpCertidaoLog = new MpCertidaoLog(null, sdf.format(new Date()), 0, 0, 0);
			
			mpCertidaoLog = mpCertidaoLogService.guardar(mpCertidaoLog);
		}
		//
		if (tipoGeracao.toUpperCase().equals("B")) // Balcão ...
			mpCertidaoLog.setContBalcao(mpCertidaoLog.getContBalcao() + 1);
		else
			if (tipoGeracao.toUpperCase().equals("E")) // Etiqueta ...
				mpCertidaoLog.setContEtiqueta(mpCertidaoLog.getContEtiqueta() + 1);
			else // Pedido/Ato ...
				mpCertidaoLog.setContPedido(mpCertidaoLog.getContPedido() + 1);
		//
//      System.out.println("mpTrataCertidaoLog()  ---------> " + tipoGeracao + " / Data = " + sdf.format(new Date()));
		
		mpCertidaoLog = mpCertidaoLogService.guardar(mpCertidaoLog);
	}
	
	public void printPDF(String fileX, String printerX) {
		//
		if (printerX.contains("\\") && printerX.contains(":")) {
			//
			try {
				this.printImpressoraIpPDF(fileX, printerX);
				//
			} catch (PrintException e) {
				MpFacesUtil.addInfoMessage("Erro Impressora IP ! (printException = " + e);
			} catch (IOException e) {
				MpFacesUtil.addInfoMessage("Erro Impressora IP ! (ioException = " + e);
			}
			//
			return;
		}
		//
		FileInputStream fis;

		try {
			DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;

			PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
//			patts.add(Sides.DUPLEX);

			PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
			if (ps.length == 0)
				MpFacesUtil.addInfoMessage("Nenhuma Impressora... Encontrada !");
															// throw new IllegalStateException("No Printer found");
			//
			PrintService myService = null;
			String printersX = "";
			//
			for (PrintService printService : ps) {
				//
//				System.out.println("printPDF() - Available printer: ................................... (" + 
//												printerX + ") / (" + printService.getName() + ") / (" + fileX);
				printersX = printersX + " / (" + printService.getName() + ")/n";
				//
				if (printService.getName().equals(printerX)) { // "Microsoft Print to PDF"
					myService = printService;
					//
					break;
				}
			}
			//
			if (myService == null)
				MpFacesUtil.addInfoMessage("Impressora... Não Encontrada ! ( " + printerX + " " + printersX);
														// throw new IllegalStateException("Printer not found");
			//
			try {
				//
				fis = new FileInputStream(fileX);
				
				Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
				DocPrintJob printJob = myService.createPrintJob();

				printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
				//
				fis.close();
				//
			} catch (IOException e) {
				//
				MpFacesUtil.addInfoMessage("Error Impressão... Arquivo ! ( " + fileX);
			}
			//
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("Impressão... Error ! ( " + e);
		}
	}
	
    public void addMessage() {
    	//
        String summary = this.indImpressao ? "Impressão Habilitada" : "Impressão Desabilitada";
        
        MpFacesUtil.addInfoMessage(summary);
    }	
    public void addMessageExigencia() {
    	//
        String summary = this.indExigencia ? "Exigência Habilitada" : "Exigência Desabilitada";
        
        MpFacesUtil.addInfoMessage(summary);
    }	
    public void addMessageGratuito() {
    	//
        String summary = this.indGratuito ? "Gratuito Habilitada" : "Gratuito Desabilitada";
        
        MpFacesUtil.addInfoMessage(summary);
    }	
	
    public Boolean procuraArquivoNome(String fileX, String searchX) {
    	//
    	// ... COM ERRO NOME ... VEM AÇENTUADO E NO ARQUIVO NÃO NO TEXTO !
    	//
		searchX = searchX.toLowerCase().trim();
		
		File fileSearchX = new File(fileX);
		//
	    try (Scanner scannerX = new Scanner(fileSearchX)) {
	    	//
	        while(scannerX.hasNextLine()) {
	    		//
	    		String linhaXX = scannerX.nextLine(); // + System.lineSeparator();
	    		//
//	    		System.out.println("procuraArquivoNome ---------> " + fileX + " / " + searchX + " / " + linhaXX);

				if (linhaXX.toLowerCase().contains("nada consta contra " + searchX)) {
					// 
					System.out.println(linhaXX);
					//
					scannerX.close();

					return true;
				}
	        }
	        //
	        scannerX.close();
	        //
		} catch (Exception e) {
			//
			MpFacesUtil.addInfoMessage("procuraArquivoNome() ! ( e = " + e);
		}
		//
		return false;
    }	
    
    public void printImpressoraIpPDF(String fileXX, String printerIpX) throws PrintException, IOException {
    	//
		File file = new File(fileXX);

		String printerPort = printerIpX.substring(printerIpX.indexOf(":") + 1);

		String printerIp = printerIpX.replace("\\", "");
		printerIp = printerIp.substring(0, printerIpX.indexOf(":") - 1);
		//
        Socket socket = new Socket(printerIp, Integer.parseInt(printerPort) ); // Port.9100 Default?

        FileInputStream fileInputStream = new FileInputStream(file);
        byte [] mybytearray  = new byte [(int)file.length()];

        fileInputStream.read(mybytearray,0,mybytearray.length);

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write(mybytearray,0,mybytearray.length);

        //Curious thing is that we have to wait some time to make more prints.
        try {
            Thread.sleep(500);
            //
        } catch (InterruptedException e) { }
        //
        outputStream.flush();
        outputStream.close();
        socket.close();
        fileInputStream.close();
    }

	public void printJSEtiqueta(String fileXX, String printerX) {
		//
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

		URI uri;
		try {
			uri = new URI(extContext.getRequestScheme(), null, extContext.getRequestServerName(),
					extContext.getRequestServerPort(), extContext.getRequestContextPath(), null, null);

			String contextPathX = uri.toASCIIString();			
			//
			File fileSource = new File(fileXX);
			
			String pathDest = extContext.getRealPath(File.separator + "resources" + File.separator + "pdf" +
																	 File.separator + fileSource.getName());	
			File fileDest = new File(pathDest);
			//
			FileUtils.copyFile(fileSource, fileDest);
			//
	        try {
	            Thread.sleep(500);
	            //
	        } catch (InterruptedException e) { }
			//
//			RequestContext.getCurrentInstance().execute("printJS('" + contextPathX + "/resources/pdf/" +
//																					fileSource.getName() + "');");
			PrimeFaces.current().executeScript("printJS('" + contextPathX + "/resources/pdf/" +
																					fileSource.getName() + "');");

//			System.out.println("MpECartorioRJBean.printJSEtiqueta() - ( " + fileXX + " / " + printerX + " / " + 
//					contextPathX + " / " + fileSource.getName() + " / " + fileSource.getAbsolutePath() +  
//					" / " + fileDest.getAbsolutePath());
			//
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}  

	public void printEtiquetaAll() {
		//
		SimpleDateFormat sdfX = new SimpleDateFormat("yyyMMddhhmmss");
		
		ExternalContext extContextX = FacesContext.getCurrentInstance().getExternalContext();
        //
		URI uriX;

		String fileNameDestX = this.mpSeguranca.getUsuarioLogado() + "_" + sdfX.format(new Date()) +
																								"_etiquetas.pdf";
		String fileDestX = extContextX.getRealPath(File.separator + "resources" + File.separator + "pdf" +
												 File.separator + fileNameDestX); 
		String fileDestPDFX = this.scPathEtiqueta + fileNameDestX;
		
//		String impressoraPerfil = "";
//		if (this.indUserCert) {
//			impressoraPerfil = scImpressoraCertEtiqueta;
//		} else
//			if (this.indUserCanc) {
//				impressoraPerfil = scImpressoraCancEtiqueta;
//			}
		//		
        List<InputStream> list = new ArrayList<InputStream>();

        try {
    		uriX = new URI(extContextX.getRequestScheme(), null, extContextX.getRequestServerName(),
					extContextX.getRequestServerPort(), extContextX.getRequestContextPath(), null, null);

    		String contextPathXX = uriX.toASCIIString();			
            // Source Pdfs ...
    		if (arquivoEtiquetaSelecionadoList.isEmpty())
    			arquivoEtiquetaSelecionadoList.add(this.arquivoEtiquetaSelecionado);
    		//
    		for (String etiquetaTxtX : arquivoEtiquetaSelecionadoList) {
    			//
    			String etiquetaPDFX = this.scPathEtiqueta + etiquetaTxtX.replace(".txt", ".pdf");
    			
                list.add(new FileInputStream(new File(etiquetaPDFX)));
//                
//    			System.out.println("MpECartorioRJBean.printJSEtiquetaAll() - ( " + etiquetaPDF); 
    		}
            //
            doMerge(list, new FileOutputStream(new File(fileDestPDFX)));
            //
			File fileSourceXX = new File(fileDestPDFX);
			File fileDestXX = new File(fileDestX);
            
			FileUtils.copyFile(fileSourceXX, fileDestXX);
			
//			if (fileDestXX.exists())
//				System.out.println("MpECartorioRJBean.printJSEtiquetaAll() - ( fileDestXX = " + 
//																				fileDestXX.getAbsolutePath());
//			else
//				System.out.println("MpECartorioRJBean.printJSEtiquetaAll() - NãoExiste! ( fileDestXX = " + 
//																				fileDestXX.getAbsolutePath());
			//
	        try {
	            Thread.sleep(5000);
	            //
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			//
//			RequestContext.getCurrentInstance().execute("printJS('" + contextPathX + File.separator + "resources" + 
//												File.separator + "pdf" + File.separator + fileNameDest + "');");
			
			PrimeFaces.current().executeScript("printJS('" + contextPathXX + "/resources/pdf/" + 
												 								fileNameDestX.trim() + "');");
//			PrimeFaces.current().executeScript("printJS('" +
//			"http://localhost:8080/mpbasicocartorio/resources/pdf/charles_20190317105506_etiquetas.pdf');");
//			
//			System.out.println("MpECartorioRJBean.printJSEtiquetaAll() - ( " + "printJS('" + contextPathXX + 
//																	"/resources/pdf/" + fileNameDestX + "');"); 
            //
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}  
	
    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
    	//
		Rectangle pagesize = new Rectangle(298f, 105f);

		// Document(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom)
		Document document = new Document(pagesize, 30f, 10f, 2f, 2f);
        
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        //
        for (InputStream in : list) {
        	//
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
            //
            in.close();
        }
        //
        outputStream.flush();
        document.close();
        outputStream.close();
    }	
	    
	public String criaPDF_A(String fileX) throws DocumentException, IOException {
		//
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();

		String pathColor = extContext.getRealPath(File.separator + "resources" + File.separator + 
											"certidaoEletronica" + File.separator + "color");
		String pathFonts = extContext.getRealPath(File.separator + "resources" + File.separator + 
											"certidaoEletronica" + File.separator + "fonts");
		//
		//                             012345678901234567890123456
		// ImpressoraConfigCertidao = "60 15 05 05 10.0 10.0 08.0";
		//
		Float scMarginLeft    = Float.parseFloat(this.scImpressoraConfigCertidao.substring(0, 2));
		Float scMarginRight   = Float.parseFloat(this.scImpressoraConfigCertidao.substring(3, 5));
		Float scMarginTop     = Float.parseFloat(this.scImpressoraConfigCertidao.substring(6, 8));
		Float scMarginBottom  = Float.parseFloat(this.scImpressoraConfigCertidao.substring(9, 11));
		Float scLineSpace     = Float.parseFloat(this.scImpressoraConfigCertidao.substring(12, 16));
		Float scFntSize       = Float.parseFloat(this.scImpressoraConfigCertidao.substring(17, 21));
		Float scFntSizeBlank  = Float.parseFloat(this.scImpressoraConfigCertidao.substring(22, 26));

		if (fileX.toLowerCase().contains(".cer")) {
			//
			scMarginLeft    = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(0, 2));
			scMarginRight   = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(3, 5));
			scMarginTop     = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(6, 8));
			scMarginBottom  = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(9, 11));
			scLineSpace     = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(12, 16));
			scFntSize       = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(17, 21));
			scFntSizeBlank  = Float.parseFloat(this.scImpressoraConfigCertidaoCerp.substring(22, 26));
		}
		//
//		System.out.println("Certidão CONFIGURACAO CERTIDÃO PDF/A--------------------------------> ( " 
//					+ scMarginLeft + " / " + scMarginRight + " / " + scMarginTop + " / " + scMarginBottom + " / "
//					+ scLineSpace + " / " + scFntSize + " / " + scFntSizeBlank + " )");
		//
		String fileXAnt = fileX;
		//
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileX), "UTF-8"))) {
			//			
			// Document(Rectangle pageSize, float marginLeft, float marginRight, float marginTop, float marginBottom)
			Document document = new Document(PageSize.A4, scMarginLeft, scMarginRight, scMarginTop, scMarginBottom);
			
			// Para arquivo tipo (.CER) ...gerar "num_cerp.pdf" ... (MVPR-08042019) !
//			fileX = this.scPathCertidaoEletronicaCerp + this.numeroCERP_A + "(a).pdf";
			fileX = this.scPathCertidaoEletronicaCerp + this.numeroCERP_A + ".pdf";

			PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(
																	fileX), // .toLowerCase().replace(".cer", "(a).pdf")), 
																	PdfAConformanceLevel.PDF_A_2A);
			writer.createXmpMetadata();
			writer.setTagged();
			//
			document.open();
			document.addLanguage("en-us");
			
			File fileColor = new File(pathColor + File.separator + "sRGB_CS_profile.icm");
			ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream(fileColor));
			
			writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

//			float fntSize = 12f;
//			float lineSpacing = 10f;
			//
			Integer contLinha = 0;
			Integer linhaPosQRCODE = 0;
//			fileX = fileX.toLowerCase().replace(".cer", "(a).pdf");
			fileX = fileX.toLowerCase().replace(".cer", ".pdf");

//			PdfWriter.getInstance(document, new FileOutputStream(fileX));

			document.addCreator("MPXDS wwww.mpxds.com.br");
			document.addAuthor("Marcus VPR");

//			Font fontXpt = new Font(FontFamily.COURIER, scFntSize, Font.BOLD); // MR-14032019 (WAS=9) !
//			Font fontYpt = new Font(FontFamily.COURIER, scFntSizeBlank); // Line Blank // MR-14032019 (WAS=7.9f) !
//			Font fontZpt = new Font(FontFamily.COURIER, scFntSize - 3, Font.BOLD);
			
			Font fontXpt = new Font(BaseFont.createFont(pathFonts + File.separator + "Courier.ttf", 
												BaseFont.IDENTITY_H, BaseFont.EMBEDDED), scFntSize, Font.NORMAL);
			Font fontYpt = new Font(BaseFont.createFont(pathFonts + File.separator + "Courier.ttf", 
												BaseFont.IDENTITY_H, BaseFont.EMBEDDED), scFntSizeBlank);
			Font fontZpt = new Font(BaseFont.createFont(pathFonts + File.separator + "Courier.ttf", 
												BaseFont.IDENTITY_H, BaseFont.EMBEDDED), scFntSize - 3, Font.BOLD);
			//		
//			Font fontXpt = new Font(FontFamily.COURIER, 7, Font.BOLD);
//			Font regular = new Font(FontFamily.HELVETICA, 12);
//			Font bold = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
						
			//
			Boolean indQRCode = true; // Primeira Linha do Arquivo ...
			String lineQRCode = "";
			String line;
			
			while ((line = br.readLine()) != null) {
				//
				if (line.contains("")) continue;
				//
//				System.out.println("Certidão PDF/A LINE --------------------------------> " + line);
//				
				if (indQRCode) {
					//
					lineQRCode = line.trim();
					indQRCode = false;
					continue;
				}
				//
				contLinha++;

				if (line.isEmpty()) {
					//
//					System.out.println("Certidão LINE EMPTY--------------------------------> " + line);
//
					document.add(new Paragraph(" ", fontYpt));
				} else {
//					line = line.replaceAll(" ","\u00a0");
					
//					Paragraph p = new Paragraph(new Paragraph(line, fontXpt));	
					
//					document.add(new Paragraph(line, fontXpt));
					//
//					document.add(new Phrase(scLineSpace, line, FontFactory.getFont(FontFactory.COURIER,
//											scFntSize, Font.BOLD)));
					//
					Paragraph p = new Paragraph(line, fontXpt);
					p.setLeading(scLineSpace, 0);
					
//					p.setSpacingBefore(scLineSpace);
//					p.setSpacingAfter(scLineSpace);
					//
					document.add(p);
					//
				}
				// 
				if (line.contains("www3.tjrj.jus.br/sitepublico"))
					linhaPosQRCODE = contLinha;
				//
				if (line.toUpperCase().contains("CONTINUA ==>")) {
					contLinha = 0;
					document.newPage();
				}
				//
				if (fileXAnt.toUpperCase().contains(".CER")) {
					//
					if (line.contains("SAC: (21) ") && !line.contains("CONTINUA ==>")) {
						//
				        int sizeLnX = this.lnX.length;
				        
				        for (int iX=0; iX < sizeLnX; iX++) {
				        	//
							Paragraph px = new Paragraph(this.lnX[iX], fontZpt);
				        	if (iX == 5)
				        		px = new Paragraph("CERP " + this.numeroCERP_A, fontXpt);
				        	
							px.setLeading(scLineSpace, 0);
							//
							document.add(px);
							//
//							System.out.println("Certidão LINE PDF/A ---------------> " + iX + " / " + this.lnX[iX]);
				        }
					}
				} else
					if (line.toUpperCase().contains("TOTAL DO ATO"))
						break;
			}
			//
			// Trata posicionamento QRCODE no fim do PDF ...
			// ==============================================
			Integer posY = (contLinha - linhaPosQRCODE) * 10;
			
			if (fileXAnt.toUpperCase().contains(".CER")) posY = posY - 10 ; // MVPR-08042019 ...
			//
//			if (posY < 120) // MVPR-24072019 (Solicit.Rubens!->v1.05i)
				posY = 10;
			// ==============================================

//			System.out.println("MpECartorioRJBean.criaPDF() ...................... ( ContLinha = " + contLinha + 
//						" / CERP = " + this.numeroCERP + " / Pos.Y = " + posY + 
//						" / LinPosQRCODE = " + linhaPosQRCODE + 
//						" / fileX = " + fileX + 
//						" / arquivoSelecionado = " + this.arquivoSelecionado);
			//
			if (!this.numeroCERP_A.isEmpty()) {
				//
				// String myString =
				// "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" +
				// numCerpX;

				String qrcodeX = "https://validador.e-cartoriorj.com.br/cerp.aspx?cerp=" + this.numeroCERP_A;

				BarcodeQRCode qrcode = new BarcodeQRCode(qrcodeX.trim(), 4, 4, null);

				Image qrcodeImage = qrcode.getImage();
				qrcodeImage.scaleAbsolute(80, 80);
				//
				if (fileXAnt.toUpperCase().contains(".CER"))
					qrcodeImage.setAbsolutePosition(420f, posY);
				else
					qrcodeImage.setAbsolutePosition(320f, posY);
				//
				qrcodeImage.setAccessibleAttribute(PdfName.ALT, new PdfString("QRCode CERP"));

				document.add(qrcodeImage);
			}
			//
			if (!lineQRCode.isEmpty()) {
				//
				BarcodeQRCode qrcode = new BarcodeQRCode(lineQRCode.trim(), 4, 4, null);

				Image qrcodeImage = qrcode.getImage();				
				qrcodeImage.scaleAbsolute(80, 80);
				//
				if (fileXAnt.toUpperCase().contains(".CER"))
					qrcodeImage.setAbsolutePosition(500f, posY);
				else
					qrcodeImage.setAbsolutePosition(430f, posY);
				//
				qrcodeImage.setAccessibleAttribute(PdfName.ALT, new PdfString("QRCode"));

				document.add(qrcodeImage);
			}
			//
			document.close();
			//
		} catch (Exception e) {
			System.out.println("MpECartorioRJBean.criaPDF_A() ..................... Erro.Exception ! ( e = " + e);
			return "MpECartorioRJBean.criaPDF_A() - Erro.Exception ! ( e = " + e;
		}
		//
		if (this.indImpressao) {
			//
			String impressoraPerfil = "";
			String impressoraPerfilTipo = "";
			
			if (this.indUserCert) {
				impressoraPerfil = scImpressoraCertCertidaoA4;
				impressoraPerfilTipo = "scImpressoraCertCertidaoA4";
				
				if (this.arquivoSelecionado.toUpperCase().indexOf(".CER") >= 0) {
					impressoraPerfil = scImpressoraCertXerox;
					impressoraPerfilTipo = "scImpressoraCertXerox";
				}
			} else
				if (this.indUserCanc) {
					impressoraPerfil = scImpressoraCancCertidaoA4;
					impressoraPerfilTipo = "scImpressoraCancCertidaoA4";
				}
			//
			if (impressoraPerfil.isEmpty())
				MpFacesUtil.addInfoMessage("Impressora... Não Configurada ! Contactar Suporte. ( " + 
																						impressoraPerfilTipo);
			else {
				this.printPDF(fileX, impressoraPerfil);

				MpFacesUtil.addInfoMessage("Impressão... efetuada ! ( " + fileXAnt + " / " + impressoraPerfil);
			}
			//
			// this.indImpressao = false;
		}
		//
		// Trata Arquivo: XML...
		if (this.arquivoSelecionado.toUpperCase().indexOf(".CER") >= 0) {
			//
	        String arquivoXML = this.arquivoSelecionado.toUpperCase().replace(".CER", ".XML");
			
//	        System.out.println("MpECartorioRJBean.TrataXML(CER) ...................... ( " + 
//	        														this.scPathCertidaoEletronicaCerp + arquivoXML); 
	        //
	    	File fileXML = new File(this.scPathCertidaoEletronicaCerp + arquivoXML);
	    	if (fileXML.exists())
	    		this.atualizaRenameXML_CERP(this.scPathCertidaoEletronicaCerp + arquivoXML, this.numeroCERP);
	    	//
		}
				
		// Trata movimentação arquivos Gerado para pasta Disquete !!! MPVR-19032019 ...
		if (null == this.scPathArquivoDisquete || this.scPathArquivoDisquete.isEmpty())
			assert(true); // nop
		else
			this.moveArquivoDisquete(this.scPathCertidaoEletronicaCerp, this.numeroCERP_A + ".pdf");
		//
		this.tabViewIndex = 1;
		this.numeroCERP_A = "";

		this.arquivoSelecionado = "";
		this.arquivoSelecionadoEditaP = "";
		//
		return "Geração Certidão Eletrônica(PDF/A)... efetuada !!! ( " + fileXAnt;
	}
    
	public void moveArquivoGerado(String pathArquivoM, String arquivoM) {
		//
		try {
			FileUtils.moveFile(FileUtils.getFile(pathArquivoM + arquivoM),
													FileUtils.getFile(this.scPathArquivoGerado + arquivoM));
		} catch (IOException e) {
			//
			MpFacesUtil.addInfoMessage("moveArquivoGerado() ! ( e = " + e);
		}
	}        
        
    public void moveArquivoDisquete(String pathArquivoM, String arquivoM) {
    	//
    	DateFormat sdfD = new SimpleDateFormat("yyyy_MM_dd");
    	
    	String pathDia = sdfD.format(new Date());
    	
        try {
			FileUtils.moveFile(FileUtils.getFile(pathArquivoM + arquivoM), 
							   FileUtils.getFile(this.scPathArquivoDisquete + File.separator + pathDia +
									   												File.separator + arquivoM));
		} catch (IOException e) {
			//
			MpFacesUtil.addInfoMessage("moveArquivoDisquete() ! ( e = " + e);
		} 
    }
        
//    //void main
//    File f = new File("C:\\Users\\SHINWAR\\Desktop\\link.txt");
//    try {
//        printFile(f, "192.168.1.100"); //f : file to print , ip printer
//    } catch (Exception e) {
//        System.out.println(e + "--file");
//    }

//	private Integer capturaPosPDF(PdfReader pdfReader, String paramX) {
//		//
//		Integer posParamX = 0;
//		
//		try {
//			String pageTxtPDF = PdfTextExtractor.getTextFromPage(pdfReader, pdfReader.getNumberOfPages());
//
//			posParamX = pageTxtPDF.indexOf(paramX);
//			//
//		} catch (IOException e) {
//			return null;
//		}
//		//
//		return posParamX;
//	}	
	
    // ---
    
	@NotNull
	public String getScOficServentiaECartorioRJ() { return scOficServentiaECartorioRJ; }
	public void setScOficServentiaECartorioRJ(String scOficServentiaECartorioRJ) { 
												this.scOficServentiaECartorioRJ = scOficServentiaECartorioRJ; }

	@NotNull
	public String getScPathCertidaoEletronica() { return scPathCertidaoEletronica; }
	public void setScPathCertidaoEletronica(String scPathCertidaoEletronica) { 
													this.scPathCertidaoEletronica = scPathCertidaoEletronica; }

	@NotNull
	public String getScPathCertidaoEletronicaCerp() { return scPathCertidaoEletronicaCerp; }
	public void setScPathCertidaoEletronicaCerp(String scPathCertidaoEletronicaCerp) { 
												this.scPathCertidaoEletronicaCerp = scPathCertidaoEletronicaCerp; }

	@NotNull
	public String getScPathEtiqueta() { return scPathEtiqueta; }
	public void setScPathEtiqueta(String scPathEtiqueta) { this.scPathEtiqueta = scPathEtiqueta; }
    
	@NotNull
	public Boolean getScIndProducaoECartorioRJ() { return scIndProducaoECartorioRJ; }
	public void setScIndProducaoECartorioRJ(Boolean scIndProducaoECartorioRJ) { 
														this.scIndProducaoECartorioRJ = scIndProducaoECartorioRJ; }
    
	@NotNull
	public Date getDataInicio() { return dataInicio; }
	public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
	@NotNull
	public Date getDataFim() { return dataFim; }
	public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
	
	@NotNull
	public Date getDataInicioR() { return dataInicioR; }
	public void setDataInicioR(Date dataInicioR) { this.dataInicioR = dataInicioR; }
	@NotNull
	public Date getDataFimR() { return dataFimR; }
	public void setDataFimR(Date dataFimR) { this.dataFimR = dataFimR; }
	
	public String getRespostaWS() { return respostaWS; }
	public void setRespostaWS(String respostaWS) { this.respostaWS = respostaWS; }
	
	public String getRespostaWSR() { return respostaWSR; }
	public void setRespostaWSR(String respostaWSR) { this.respostaWSR = respostaWSR; }

    public String getArquivoX() { return arquivoX; }
	public void setArquivoX(String arquivoX) { this.arquivoX = arquivoX; }
	 
    public StreamedContent getFileSC() { return fileSC; } 
    public StreamedContent getFileSCRepasse() { return fileSCRepasse; } 

    public Boolean getIndFile() { return indFile; }
	public void setIndFile(Boolean indFile) { this.indFile = indFile; }

    public Boolean getIndFileRepasse() { return indFileRepasse; }
	public void setIndFileRepasse(Boolean indFileRepasse) { this.indFileRepasse = indFileRepasse; }

    public Boolean isIndAdmin() { return indAdmin; }
    public Boolean isIndUserCert() { return indUserCert; }
    public Boolean isIndUserCanc() { return indUserCanc; }

    public Boolean getIndAdmin() { return indAdmin; }
	public void setIndAdmin(Boolean indAdmin) { this.indAdmin = indAdmin; }

    public String getArquivoCE() { return arquivoCE; }
	public void setArquivoCE(String arquivoCE) { this.arquivoCE = arquivoCE; }

    public String getAmbienteCE() { return ambienteCE; }
	public void setAmbienteCE(String ambienteCE) { this.ambienteCE = ambienteCE; }

    public String getNumeroCERP() { return numeroCERP; }
	public void setNumeroCERP(String numeroCERP) { this.numeroCERP = numeroCERP; }

    public List<Pedido> getPedidoList() { return pedidoList; }
	public void setPedidoList(List<Pedido> pedidoList) { this.pedidoList = pedidoList; }

    public List<Pedido> getPedidoListFiltered() { return pedidoListFiltered; }
	public void setPedidoListFiltered(List<Pedido> pedidoListFiltered) { 
																this.pedidoListFiltered = pedidoListFiltered; }

    public List<Repasse> getRepasseList() { return repasseList; }
	public void setRepasseList(List<Repasse> repasseList) { this.repasseList = repasseList; }

    public List<String> getArquivoBalcaoList() { return arquivoBalcaoList; }
	public void setArquivoBalcaoList(List<String> arquivoBalcaoList) { 
																	this.arquivoBalcaoList = arquivoBalcaoList; }

    public List<String> getArquivoEtiquetaList() { return arquivoEtiquetaList; }
	public void setArquivoEtiquetaList(List<String> arquivoEtiquetaList) { 
																this.arquivoEtiquetaList = arquivoEtiquetaList; }

    public String getArquivoSelecionado() { return arquivoSelecionado; }
	public void setArquivoSelecionado(String arquivoSelecionado) { this.arquivoSelecionado = arquivoSelecionado; }

	public String getArquivoSelecionadoEdita() { return arquivoSelecionadoEdita; }
	public void setArquivoSelecionadoEdita(String arquivoSelecionadoEdita) { 
														this.arquivoSelecionadoEdita = arquivoSelecionadoEdita; }

	public String getArquivoSelecionadoEditaP() { return arquivoSelecionadoEditaP; }
	public void setArquivoSelecionadoEditaP(String arquivoSelecionadoEditaP) { 
														this.arquivoSelecionadoEditaP = arquivoSelecionadoEditaP; }

    public String getArquivoBalcaoSelecionado() { return arquivoBalcaoSelecionado; }
	public void setArquivoBalcaoSelecionado(String arquivoBalcaoSelecionado) { 
														this.arquivoBalcaoSelecionado = arquivoBalcaoSelecionado; }

    public String getArquivoEtiquetaSelecionado() { return arquivoEtiquetaSelecionado; }
	public void setArquivoEtiquetaSelecionado(String arquivoEtiquetaSelecionado) { 
													this.arquivoEtiquetaSelecionado = arquivoEtiquetaSelecionado; }

    public List<String> getArquivoEtiquetaSelecionadoList() { return arquivoEtiquetaSelecionadoList; }
	public void setArquivoEtiquetaSelecionadoList(List<String> arquivoEtiquetaSelecionadoList) { 
											this.arquivoEtiquetaSelecionadoList = arquivoEtiquetaSelecionadoList; }

    public List<String> getArquivoBalcaoSelecionadoList() { return arquivoBalcaoSelecionadoList; }
	public void setArquivoBalcaoSelecionadoList(List<String> arquivoBalcaoSelecionadoList) { 
												this.arquivoBalcaoSelecionadoList = arquivoBalcaoSelecionadoList; }

    public List<Pedido> getArquivoPedidoSelecionadoList() { return arquivoPedidoSelecionadoList; }
	public void setArquivoPedidoSelecionadoList(List<Pedido> arquivoPedidoSelecionadoList) { 
												this.arquivoPedidoSelecionadoList = arquivoPedidoSelecionadoList; }

    public Long getNumeroPedidoSelecionado() { return numeroPedidoSelecionado; }
	public void setNumeroPedidoSelecionado(Long numeroPedidoSelecionado) { 
														this.numeroPedidoSelecionado = numeroPedidoSelecionado; }

    public String getNomePedidoSelecionado() { return nomePedidoSelecionado; }
	public void setNomePedidoSelecionado(String nomePedidoSelecionado) { 
															this.nomePedidoSelecionado = nomePedidoSelecionado; }

    public Integer getTabViewIndex() { return tabViewIndex; }
	public void setTabViewIndex(Integer tabViewIndex) { this.tabViewIndex = tabViewIndex; }

    public String getTextoEditor() { return textoEditor; }
	public void setTextoEditor(String textoEditor) { this.textoEditor = textoEditor; }

    public String getTextoEditorP() { return textoEditorP; }
	public void setTextoEditorP(String textoEditorP) { this.textoEditorP = textoEditorP; }
	
//  public String getTextoEditorX() { return textoEditorX; }
//	public void setTextoEditorX(String textoEditorX) { this.textoEditorX = textoEditorX; }

    public Boolean getIndImpressao() { return indImpressao; }
	public void setIndImpressao(Boolean indImpressao) { this.indImpressao = indImpressao; }

    public Boolean getIndExigencia() { return indExigencia; }
	public void setIndExigencia(Boolean indExigencia) { this.indExigencia = indExigencia; }

	public String getScImpressoraCertEtiqueta() { return scImpressoraCertEtiqueta; }
	public void setScImpressoraCertEtiqueta(String scImpressoraCertEtiqueta) {
														this.scImpressoraCertEtiqueta = scImpressoraCertEtiqueta; }

	public String getScImpressoraCertCertidaoA4() { return scImpressoraCertCertidaoA4; }
	public void setScImpressoraCertCertidaoA4(String scImpressoraCertCertidaoA4) {
													this.scImpressoraCertCertidaoA4 = scImpressoraCertCertidaoA4; }

	public String getScImpressoraCertXerox() { return scImpressoraCertXerox; }
	public void setScImpressoraCertXerox(String scImpressoraCertXerox) {
															this.scImpressoraCertXerox = scImpressoraCertXerox; }

	public String getScImpressoraCancCertidaoA4() { return scImpressoraCancCertidaoA4; }
	public void setScImpressoraCancCertidaoA4(String scImpressoraCancCertidaoA4) {
													this.scImpressoraCancCertidaoA4 = scImpressoraCancCertidaoA4; }

	public String getScImpressoraCancEtiqueta() { return scImpressoraCancEtiqueta; }
	public void setScImpressoraCancEtiqueta(String scImpressoraCancEtiqueta) { 
														this.scImpressoraCancEtiqueta = scImpressoraCancEtiqueta; }

    public Boolean getIndGratuito() { return indGratuito; }
	public void setIndGratuito(Boolean indGratuito) { this.indGratuito = indGratuito; }
}