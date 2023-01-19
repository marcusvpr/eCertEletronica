package com.mpxds.basic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mp_role")
public class MpRole extends MpEntity  {
	//
	private static final long serialVersionUID = 1L;

	@Column(name="name")
	private String name;
	
	@ManyToMany(mappedBy="mpRoles")
	private List<MpUser> mpUsers = new ArrayList<MpUser>();

	// 
	
	public MpRole() {
		//
	}
	
	public MpRole(Long id, String name) {
		//
		super();
		
		this.id = id;
		this.name = name;
	}
	
	// ---
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public List<MpUser> getMpUsers() { return mpUsers; }
	public void setMpUsers(List<MpUser> mpUsers) { this.mpUsers = mpUsers; }

}