package com.example.Wallet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//It overrides the loadUserByUsername for fetching user details from the database using the username.
//The Spring Security Authentication Manager calls this method for getting the user details from the database when authenticating the user details provided by the user.
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
    	//part 1:
//    	///Password: password ----> Bcrpted into ---->$2a$10$sl....
    	if ("yashwant".equals(username)) {
            return new User("yashwant", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    	
    	
    	//part 2:
//        //Here we are getting the user details from a hardcoded User List.
//    	List<SimpleGrantedAuthority> roles=null;
//		if(username.equals("admin"))
//		{
//			//admin:admin:12 rounds in Bcrypt
//		roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		return new User("admin", "\r\n"
//				+ "$2y$12$JjCEIkW/5K/igjfH/N/lpOkB2ld8FR/6GKaz2L1lMh3117YFDNRUG",
//					roles);
//		}
//		else if(username.equals("user"))
//		{
//			//user:user:round 12 in bcrypt
//		roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//		return new User("user", "\r\n"
//				+ "$2y12$fdqKbrz5t026y4a6FVGP2utO3FDN2Nbuh..QEk7DrPbbCtXIVppR",
//					roles);
//		}
//		throw new UsernameNotFoundException("User not found with username: " + username);
	
    }
}