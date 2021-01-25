package com.example.Wallet.controller;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.entity.User;
import com.example.Wallet.exception.ResourceNotFoundException;
import com.example.Wallet.repository.UserRepository;

@RequestMapping("api/users")
@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ArrayList<User> getAllusers(){
		
		return (ArrayList<User>) this.userRepository.findAll();
		
	}
	
	@GetMapping("/{uid}")
	public User getUserById(@PathVariable (value = "uid") long userId ) {
		
		return this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
	}
	
	
	@PostMapping
	public User createUser(@Valid @RequestBody User user) {// we will be requiring request body to get data to fill
		
		ArrayList<User> allUsers=getAllusers();
		for(int i=0;i<allUsers.size();i++) {
			User curr=allUsers.get(i);
			
			if((curr.getFirstName().compareTo(user.getFirstName())==0) && (curr.getLastName().compareTo(user.getLastName())==0)) {
				throw new ResourceNotFoundException("This User Name already Exist");
		     }
			else if((curr.getEmail().compareTo(user.getEmail())==0)) {
				throw new ResourceNotFoundException("This Mail already Exist");
			}
			else if ((curr.getMobile().compareTo(user.getMobile())==0)){
				throw new ResourceNotFoundException("This Mobile alreeady Exist");
			}
			
		}
		
		return this.userRepository.save(user);
		
	}
	
	@PutMapping("/{uid}")
	public User updateUser(@RequestBody User user, @PathVariable("uid") long userId) {
		
		User existingUser = this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setAddress1(user.getAddress1());
		existingUser.setAddress2(user.getAddress2());
		existingUser.setMobile(user.getMobile());
		
		
		return this.userRepository.save(existingUser);//this will directly save into database
	}
	
	@DeleteMapping("/{uid}")
	public ResponseEntity<User> deleteUser(@PathVariable("uid") long userId){
		
		User existingUser = this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
		this.userRepository.delete(existingUser);
		return ResponseEntity.ok().build();

		
	}
}