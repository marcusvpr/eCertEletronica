package com.mpxds.basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ICC_Profile;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

//import javax.faces.context.ExternalContext;
//import javax.faces.context.FacesContext;

public class MpTestePDF {
	//
    public static final String TEXT = "C:/mpxds/teste.txt";
    public static final String DEST = "C:/mpxds/teste.pdf";
    public static final String DEST_A = "C:/mpxds/testeA.pdf";

    public static void main(String[] args) throws DocumentException, IOException {
    	//
//       File file = new File(DEST);
//       file.getParentFile().mkdirs();
//        
//    	new MpTestePDF().createPdf(DEST);

//    	File fileA = new File(DEST_A);
//        fileA.getParentFile().mkdirs();
//        
//    	new MpTestePDF().createPDF_A(DEST_A);

    	File file = new File(DEST);
        file.getParentFile().mkdirs();
        
        new MpTestePDF().createPdfRect1(DEST);
	
    }
    
    public void createPdf(String dest) throws DocumentException, IOException {
    	//
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        BufferedReader br = new BufferedReader(new FileReader(TEXT));
        String line;
        Paragraph p;
        Font normal = new Font(FontFamily.COURIER, 12); // TIMES_ROMAN
        Font espaco = new Font(FontFamily.COURIER, 6); // TIMES_ROMAN
//        Font bold = new Font(FontFamily.COURIER, 10, Font.BOLD); // TIMES_ROMAN
        //
		Boolean indQRCode = true; // Primeira Linha do Arquivo ...
//		String lineQRCode = "";
        //
//        boolean title = true;
        while ((line = br.readLine()) != null) {
        	//
			if (indQRCode) {
				//
//				lineQRCode = line.trim();
				indQRCode = false;
				continue;
			}
        	//
//          p = new Paragraph(line, title ? bold : normal);
            if (line.isEmpty())
            	p = new Paragraph(" ", espaco);
            else
            	p = new Paragraph(line, normal);
//          p.setAlignment(Element.ALIGN_JUSTIFIED);
//          title = line.isEmpty();
            //
            document.add(p);
        }
        //
        br.close();
        document.close();
        //
        System.out.println("MpTestePDF.createPdf() - PDF criado com sucesso !");        
    }
    
	public void createPDF_A(String dest) throws DocumentException, IOException {
		//
//		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
//
//		String pathFonts = extContext.getRealPath(File.separator + "resources" + File.separator + 
//											"certidaoEletronica" + File.separator + "fonts" + File.separator);		
//		String pathColor = extContext.getRealPath(File.separator + "resources" + File.separator + 
//											"certidaoEletronica" + File.separator + "color" + File.separator);
		String pathFonts = "D:\\temp\\MarcusVPR\\workspaceSTS-3.9.4\\mpBasicoCartorio\\src\\main\\resources\\certidaoEletronica\\fonts\\";
		String pathColor = "D:\\temp\\MarcusVPR\\workspaceSTS-3.9.4\\mpBasicoCartorio\\src\\main\\resources\\certidaoEletronica\\color\\";
		//
		Document document = new Document(PageSize.A4, 15, 15, 30, 20);

		PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(dest), 
																				PdfAConformanceLevel.PDF_A_1A);
		writer.createXmpMetadata();
		writer.setTagged();

		Font fontEmbedded = new Font(BaseFont.createFont(pathFonts + "OpenSans-Regular.ttf", 
														BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 8, Font.BOLD);
		document.open();
		document.addLanguage("en-us");
		
		File file = new File(pathColor + "sRGB_CS_profile.icm");
		ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream(file));
		
		writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);
		
		Paragraph p = new Paragraph("Page 1 content", fontEmbedded); // setting an embedded font
		p.setSpacingBefore(30f);
		
		document.add(p);
		document.newPage();
		document.add(new Paragraph("Content of next page goes here", fontEmbedded));

		document.close();
		//
	}
    
    public void createPdfRect(String dest) throws IOException, DocumentException {
    	//
        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.open();

        PdfContentByte canvas = writer.getDirectContent();
        
        Rectangle rect = new Rectangle(010, 700, 580, 740);
        
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(1);
        rect.setBackgroundColor(BaseColor.BLACK);
        
        canvas.rectangle(rect);

        document.close();
        //
        System.out.println("MpTestePDF.createPdfRect() - PDF criado com sucesso !");
    }	
	
    
    public void createPdfRect1(String dest) throws IOException, DocumentException {
    	//
        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        
        document.open();
        
        PdfPTable table = new PdfPTable(1);

        PdfTemplate template = writer.getDirectContent().createTemplate(190, 10);

        template.setColorFill(BaseColor.CYAN);
        template.rectangle(0, 0, 190, 10);
        template.fill();
        
        writer.releaseTemplate(template);
        
        table.addCell(Image.getInstance(template));
        
        document.add(table);

        //
        String texto = "RIO DE JANEIRO CARTORIO DO 7° OFICIO DE REGISTRO E DISTRIBUIÇÃO";
        Integer x = 95;
        Integer y = 790;
        
        PdfContentByte cb = writer.getDirectContent();

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        
        cb.saveState();
        cb.beginText();
        cb.moveText(x, y);
        cb.setFontAndSize(bf, 11);
        cb.showText(texto);
        cb.endText();
        cb.restoreState();
        
        document.close();
        //
        System.out.println("MpTestePDF.createPdfRect1() - PDF criado com sucesso !");
    }
       
}