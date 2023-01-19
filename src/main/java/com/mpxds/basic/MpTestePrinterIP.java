package com.mpxds.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.print.PrintException;

public class MpTestePrinterIP {
	//
    public static final String fileTEXT = "C:/temp/testePrinterIP.txt";
    public static final String printerIP = "\\192.168.0.10:9100";

	public static void main(String[] args) {
		//
		File fileX = new File(fileTEXT);
		if (!fileX.exists()) {
			//
			System.out.println("Arquivo não Existe! ( " + fileTEXT);
			return;
		}
		//
		try {
			printFile(fileX, printerIP); // f : file to print , ip printer
		} catch (Exception e) {
			System.out.println(e + "--file");
		}
	}
        
    public static void printFile(File file, String printerIpX) throws PrintException, IOException {
    	//
		String printerPort = printerIpX.substring(printerIpX.indexOf(":") + 1);

		String printerIp = printerIpX.replace("\\", "");
		printerIp = printerIp.substring(0, printerIpX.indexOf(":") - 1);

		System.out.println("PrinterIP.......... ( " + fileTEXT + " / " + printerIp + " / " + printerPort);
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
 
}

