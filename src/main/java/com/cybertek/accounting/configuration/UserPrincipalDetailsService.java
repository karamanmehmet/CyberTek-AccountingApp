package com.cybertek.accounting.configuration;

import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

	


	
	private UserRepository userRepository;
	

	@Autowired
    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      
    	User user = this.userRepository.findByEmail(s);

    	if(user == null)
    	    throw  new UsernameNotFoundException("I dont");
        UserPrincipal userPrincipal = new UserPrincipal(user);



        
        
        return userPrincipal;
    }
}
