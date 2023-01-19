package com.mpxds.basic.util.ws;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

//

import com.mpxds.basic.model.xml.MpCepXML;

// ---

public class MpClienteWebService {

	private static int HTTP_COD_SUCESSO = 200;

	private static final String URL_CEP_WS = "http://api.postmon.com.br/v1/cep/";

	// ---

	public static MpCepXML executaCep(String cep) {
		//
		MpCepXML mpCepXML = new MpCepXML();
		//
		try {
			URL url = new URL(URL_CEP_WS + cep + "?format=xml");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//
			if (con.getResponseCode() != HTTP_COD_SUCESSO) {
				// throw new RuntimeException("HTTP error code : "+
				// con.getResponseCode());
				mpCepXML.setComplemento("HTTP error code : " + con.getResponseCode());
			}
			//
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(con.getInputStream()));
			// //
			// String line = "";
			// String text = "";
			// //
			// while ((line = br.readLine()) != null) {
			// text += line;
			// //
			// System.out.println("CepXML.executaCep()... " + text);
			// }
			//
			JAXBContext jaxbContext = JAXBContext.newInstance(MpCepXML.class);
			//
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//
			mpCepXML = (MpCepXML) jaxbUnmarshaller.unmarshal(url);
			//
			// System.out.println("------ Dados do CEP -------- \n");
			// System.out.println("CEP : " + cepXML.getCep());
			// System.out.println("Logradouro : " + cepXML.getLogradouro());
			//
			con.disconnect();
			//
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//
		return mpCepXML;
	}
	
}