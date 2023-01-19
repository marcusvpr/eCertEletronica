package com.mpxds.basic.service;

import java.util.List;

import com.mpxds.basic.model.MpUser;

public interface MpUserService {
	//
    void save(MpUser mpUser);

    MpUser findByUsername(String username);

    MpUser findByEmail(String email);
    
    MpUser guardar(MpUser mpUser);
    
    void remover(MpUser mpUser);
    
    MpUser findById(Long id);
    
    List<MpUser> findAllByUsername();
}
