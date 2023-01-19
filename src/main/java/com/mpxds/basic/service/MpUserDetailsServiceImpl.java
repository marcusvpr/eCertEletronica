package com.mpxds.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpxds.basic.model.MpRole;
import com.mpxds.basic.model.MpUser;
import com.mpxds.basic.repository.MpUserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class MpUserDetailsServiceImpl implements UserDetailsService {
	//
    @Autowired
    private MpUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	//
        MpUser user = new MpUser();
        if (username.indexOf("@") >= 0)
        	user = userRepository.findByEmail(username);
        else
        	user = userRepository.findByUsername(username);
        
        if(user == null) {
        	System.out.println("Usuário não autorizado: " + user);
            throw new UsernameNotFoundException("Usuário não autorizado: " + username);
        }
        
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        for (MpRole role : user.getMpRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        //
        return new org.springframework.security.core.userdetails.User(user.getUsername(), 
        															  user.getPassword(), grantedAuthorities);
    }
}
