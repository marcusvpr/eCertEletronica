package com.mpxds.basic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

//import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "mp_user")
public class MpUser extends MpEntity  {
	//
	private static final long serialVersionUID = 1L;
	
	@Column(name = "username")
	private String username;

	@Column(name = "email")
//	@Email(message = "*Favor informar e-mail válido")
//	@NotEmpty(message = "*Favor informar e-mail")
	private String email;

	private String emailConfirm;
	
	@Column(name = "password")
	@Length(min = 5, message = "*Senha com pelo menos 5 caracteres")
//	@NotEmpty(message = "*Favor informar senha")
	private String password;

	private String passwordConfirm;
	
//	@Column(name = "name")
//	@NotEmpty(message = "*Favor informar nome")
//	private String name;
//	
//	@Column(name = "last_name")
//	@NotEmpty(message = "*Favor informar sobrenome")
//	private String lastName;
//	
//	@Column(name = "active")
//	private int active;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "mpUser_mpRole",
		joinColumns = @JoinColumn(name = "mpUser_id"),
		inverseJoinColumns = @JoinColumn(name = "mpRole_id")
	)	
	private List<MpRole> mpRoles = new ArrayList<MpRole>();

	//
	
	public MpUser() {
		//
	}
	
	public MpUser(Long id, String username, String email, String password
//					, String name, String lastName, int active
					) {
		//
		super();
		
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
//		this.name = name;
//		this.lastName = lastName;
//		this.active = active;
	}

	// ---

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
    @Transient
    public String getEmailConfirm() { return emailConfirm; }
    public void setEmailConfirm(String emailConfirm) { this.emailConfirm = emailConfirm; }

    public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

    @Transient
    public String getPasswordConfirm() { return passwordConfirm; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }

//	public String getName() { return name; }
//	public void setName(String name) { this.name = name; }
//
//	public String getLastName() { return lastName; }
//	public void setLastName(String lastName) { this.lastName = lastName; }
//
//	public int getActive() { return active; }
//	public void setActive(int active) { this.active = active; }

	public List<MpRole> getMpRoles() { return mpRoles; }
	public void setMpRoles(List<MpRole> mpRoles) { this.mpRoles = mpRoles; }

}