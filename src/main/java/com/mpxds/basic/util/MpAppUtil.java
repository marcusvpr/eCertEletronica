package com.mpxds.basic.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class MpAppUtil {
	//
	public static Integer calculaIdadeAnos(Date dataCalc) {
		//
		Integer idade = 0;
		LocalDate ldDataCalc;
		
    	if (null == dataCalc)
    		assert(true); // nop
    	else {
//    		System.out.println("MpAppUtil.calculaIdadeAnos() - ( " + dataCalc);
    		//
    		if (dataCalc instanceof java.sql.Date) {
    		    ldDataCalc = ((java.sql.Date) dataCalc).toLocalDate();
    		} else {
    		    ldDataCalc = dataCalc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    		}
    		//
    		idade = Period.between(ldDataCalc, LocalDate.now()).getYears();
    	}
    	//		
		return idade;
	}
	
    public static String getRandomName() {
    	//
        int i = (int) (Math.random() * 10000000);
         
        return String.valueOf(i);
    }	
	
    public static byte[] getFileContents(InputStream in) {
    	//
		byte[] bytes = null;
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int read = 0;
			bytes = new byte[1024];
			//
			while ((read = in.read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
			bytes = bos.toByteArray();
			//
			in.close();
			in = null;
			bos.flush();
			bos.close();
			bos = null;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return bytes;
	}   
   
    public static String convertIS(InputStream inputStream, Charset charset) throws IOException {
    	//
    	StringBuilder stringBuilder = new StringBuilder();
    	String line = null;
    	
    	try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {	
    		while ((line = bufferedReader.readLine()) != null) {
    			stringBuilder.append(line);
    		}
    	}
    	//
    	return stringBuilder.toString();
    } 
           
}