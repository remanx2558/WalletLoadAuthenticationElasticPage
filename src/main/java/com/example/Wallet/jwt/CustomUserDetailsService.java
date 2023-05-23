package com.example.Wallet.jwt;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Wallet.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;

		DAOUser user = userDao.findByUsername(username);
		if (user != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
			return new User(user.getUsername(), user.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);
	}

	public DAOUser save(UserDTO user) {
		//By creating a new instance of DAOUser and setting the encoded password, the code ensures that the stored password is secure and not directly exposed.
		// Additionally, it prevents accidentally saving the password in plain text or modifying the UserDTO object before saving.
		//
		//This approach follows best practices for handling passwords securely and separates the concerns of creating a new entity and performing password encoding.
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		//the password is encoded using the passwordEncoder before setting it in the newUser. This step is important for security reasons as it ensures that the password is not stored in plain text in the database.
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		return userDao.save(newUser);
	}

}