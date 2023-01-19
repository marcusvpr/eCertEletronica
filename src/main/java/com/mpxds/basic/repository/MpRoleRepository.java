package com.mpxds.basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpRole;

public interface MpRoleRepository extends JpaRepository<MpRole, Long> {
	//    
	@Transactional(readOnly=true)
	public List<MpRole> findAllByOrderByName();
}
