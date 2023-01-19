package com.mpxds.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpUser;

public interface MpUserRepository extends JpaRepository<MpUser, Long> {
	//
    MpUser findByUsername(String username);
    
    MpUser findByEmail(String email);
    
	@Transactional(readOnly=true)
	public List<MpUser> findAllByOrderByUsername();
    
	
	MpUser findFirstByUsernameIsNotNullOrderByUsernameAsc();
	MpUser findFirstByUsernameIsNotNullOrderByUsernameDesc();
	
    public List<MpUser> findTop1ByUsernameGreaterThanOrderByUsername(String Username);
    public List<MpUser> findTop1ByUsernameLessThanOrderByUsernameDesc(String Username);
}
