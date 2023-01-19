package com.mpxds.basic.primefaces.view;

import java.io.Serializable;

import javax.inject.Named;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//
//@ManagedBean
//@SessionScoped

@Named
public class ThemeView implements Serializable {
	//
	private static final long serialVersionUID = 1L;
	
	private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public void change(String color) {
        if(color.equals("green"))
            this.color = null;
        else
            this.color = "-" + color;
    }
    
}
