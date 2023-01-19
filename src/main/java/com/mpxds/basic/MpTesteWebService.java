package com.mpxds.basic;

import com.itextpdf.text.DocumentException;
import com.mpxds.basic.util.MpAppUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.datacontract.schemas._2004._07.ecartorio_service.ArrayOfPedido;
import org.datacontract.schemas._2004._07.ecartorio_service.Pedido;
import org.tempuri.MpHeaderHandlerResolver;

public class MpTesteWebService {
	//
	private String scOficServentiaECartorioRJ = "731";
	
	private Boolean scIndProducaoECartorioRJ = true;
	
	private String arquivoX;
	private String pathX;
	
	private Date dataInicio;
	private Date dataFim;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private List<Pedido> pedidoList = new ArrayList<Pedido>();

	private File fileCE;
	
	// --- 
	
	public static void main(String[] args) throws DocumentException, IOException, JAXBException {
    	//
    	new MpTesteWebService().listarExigencias();
    }
    
    public void listarExigencias() throws JAXBException, IOException {
    	//
		this.pathX = "c:" + File.separator + "temp" + File.separator;
		this.arquivoX = "teste_arrayOfPedido.txt";
    	    	
		Calendar dataIni = Calendar.getInstance();
		dataIni.setTime(new Date());
		dataIni.set(Calendar.MONTH, 0);
		dataIni.set(Calendar.DAY_OF_MONTH, 1);
		
		this.dataInicio = dataIni.getTime();	
		this.dataFim = new Date();
		
		//
    	
		org.tempuri.Service serviceXD = new org.tempuri.Service();
		org.tempuri.ServiceProducao serviceXP = new org.tempuri.ServiceProducao();
        
        MpHeaderHandlerResolver mpHandlerResolver = new MpHeaderHandlerResolver();
        
        org.tempuri.IService serviceInterfaceX;
        
        if (this.scIndProducaoECartorioRJ) {
			//
	        serviceXP.setHandlerResolver(mpHandlerResolver);

	        serviceInterfaceX = serviceXP.getPort(org.tempuri.IService.class,
													new javax.xml.ws.soap.AddressingFeature());
		} else {
			//
			serviceXD.setHandlerResolver(mpHandlerResolver);
			
			serviceInterfaceX = serviceXD.getPort(org.tempuri.IService.class,
													new javax.xml.ws.soap.AddressingFeature());
		}
          
		ArrayOfPedido arrayOfPedido = serviceInterfaceX.listarPedidos(this.scOficServentiaECartorioRJ, "", 
										"", sdf.format(dataInicio), sdf.format(dataFim),
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
		
		jaxbMarshaller.marshal(arrayOfPedido, System.out);

		// //Marshal the employees list in file
		// jaxbMarshaller.marshal(arrayOfPedido, new
		// File("c:/temp/employees.xml"));
		//
		String msgWS = MpAppUtil.convertIS(inputStreamX, charsetX);
		
		String respostaWS = "Número Pedidos retornado pelo WebService = ( " + arrayOfPedido.getPedido().size() +
																" )\n\n" + msgWS.substring(0, msgWS.length() / 2);
//		this.indFile = true;
		
//		System.out.println("MpECartorioRJBean.executaWS() - RespostaWS = " + this.respostaWS);
		//

		// Unmarshal ArrayOfPedifo ...
		JAXBContext jaxbContextX = JAXBContext.newInstance(ArrayOfPedido.class);
        Unmarshaller unmarshallerX = jaxbContextX.createUnmarshaller();
//      ArrayOfPedido arrayOfPedidoX = (ArrayOfPedido) unmarshallerX.unmarshal(this.fileX);
        ArrayOfPedido arrayOfPedidoX = (ArrayOfPedido) JAXBIntrospector.getValue(unmarshallerX.unmarshal(this.fileCE));
        //
		this.pedidoList.clear();
		
		for (Pedido pedidoX : arrayOfPedidoX.getPedido()) {
			//
			if (pedidoX.getDsPdStatusPedidoExt().getValue().equals("EM EXIGÊNCIA"))
				this.pedidoList.add(pedidoX);
//			System.out.println("MpECartorioRJBean.executaWS() - Pedidos = " + pedidoX.getNuValorTotal().getValue());
		}		
		//
	}
}

