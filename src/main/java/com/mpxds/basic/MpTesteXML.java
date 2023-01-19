package com.mpxds.basic;

import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MpTesteXML {
	//
    public static final String fileXML = "D:\\temp\\oficio7\\Pastas teste\\certelet\\19314494073.XML";
    public static final String numeroCERPXML = "8b6e0905-2a3f-4f79-9e06-61179f0f1157";

    public static void main(String[] args) throws DocumentException, IOException {
    	//        
    	System.out.println("MpTesteXML.atualizaRenameXML_CERP()... ( " + 
    															atualizaRenameXML_CERP(fileXML, numeroCERPXML));
    }
    
	public static String atualizaRenameXML_CERP(String fileXML, String numeroCERPXML) {
		//

		// Trata Geração Arquivo XML !
		
        File outFileXML = new File("c:\\temp\\" + numeroCERPXML + ".xml"); 
        
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
//    			<RegistroDistribuicao VersaoLayout="2.15">
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
	            bw.write("<RegistroDistribuicao VersaoLayout=\"2.15\">\n");
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
    						IdPedidoCentral = params[3];
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
    							+ " Nome=\"" + Nome + "\""
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
        //
		return "Atualizão XML/CERP... efetuada ! ( ARQUIVO/CERP = " + fileXML + " / " + numeroCERPXML ;
	}
	
}

