package com.mpxds.basic.service;

public interface MpSecurityService {
	//
    String findLoggedInUsername();

    void autologin(String username, String password);
}
