package com.mpxds.basic.controller;

import java.io.IOException;
import java.io.Serializable;

//import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

//@SuppressWarnings("deprecation")
//@ManagedBean
@Named
public class MpLogoutBean implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	// --------------
	
	public MpLogoutBean() {
		//
//		System.out.println("MpLogoutBean() - 000");
	}
	
	public void inicializar() {
		//
//		System.out.println("MpLogoutBean() - inicializar");

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext extContext = context.getExternalContext();
		
		String url = extContext.encodeActionURL(extContext.getRequestContextPath() + "/Logout");
//																	"/j_spring_security_logout");
		try {
			extContext.redirect(url);
			//
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
		
}