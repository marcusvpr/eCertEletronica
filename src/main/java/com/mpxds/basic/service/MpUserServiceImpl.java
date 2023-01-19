package com.mpxds.basic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpxds.basic.exception.MpNegocioException;
import com.mpxds.basic.model.MpUser;
import com.mpxds.basic.repository.MpRoleRepository;
import com.mpxds.basic.repository.MpUserRepository;

@Service
public class MpUserServiceImpl implements MpUserService {
	//
    @Autowired
    private MpUserRepository userRepository;
    @Autowired
    private MpRoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(MpUser user) {
    	//
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setMpRoles(new ArrayList<>(roleRepository.findAll()));
        //
        userRepository.save(user);
    }

    @Override
    public MpUser findByUsername(String username) {
    	//
        return userRepository.findByUsername(username);
    }

    @Override
    public MpUser findByEmail(String email) {
    	//
        return userRepository.findByEmail(email);
    }

	@Override
	public MpUser guardar(MpUser mpUser) {
		//
		try {
			MpUser mpObj = this.findByUsername(mpUser.getUsername());
			
			if (mpObj != null && !mpObj.equals(mpUser)) {
				throw new MpNegocioException("Já existe um USUÁRIO... com o NOME/Login informado.");
			}
			
	    	return this.userRepository.saveAndFlush(mpUser);
	    	//
		} catch (OptimisticLockException e) {
			//
			throw new MpNegocioException("Erro de concorrência. Esse USUÁRIO... já foi alterado anteriormente!");
		}
	}

	@Override
	public void remover(MpUser mpUser) {
		//
    	this.userRepository.delete(mpUser);
	}

	@Override
	public MpUser findById(Long id) {
    	//
    	Optional<MpUser> mpObj = this.userRepository.findById(id);

    	return mpObj.orElse(null);
	}

	@Override
	public List<MpUser> findAllByUsername() {
    	//
    	List<MpUser> mpObjs = this.userRepository.findAllByOrderByUsername();

    	return mpObjs;
	}
    
}
