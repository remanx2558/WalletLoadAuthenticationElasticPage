package com.example.Wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.entity.Wallet;
import com.example.Wallet.exception.ResourceNotFoundException;
import com.example.Wallet.repository.WalletRepository;
import com.example.Wallet.service.WalletService;

@RestController
public class WalletController {
	 @Autowired
	    private WalletService walletService;
	 
	  @Autowired
	    private WalletRepository walletRepository;

	    //will create wallet for a user
	    @PostMapping(value = "/wallet")
	    public String addWallet(@RequestBody Wallet walletModel) {
	        List<Wallet> phone_number = walletService.findbyPhone(walletModel.getPhone()); // check for same phone number

	        if (!phone_number.isEmpty()) {
	            return "Wallet already exists";
	        } else walletService.addWallet(walletModel);
	        return "Wallet created";

	    }

	    //1: for displaying all the wallets present in the database
	    @GetMapping(value = "/wallet/all")
	    public List<Wallet> displayAll() {
	        return walletService.getWallets();
	    }
	    //2: display specific wallet user
	    @GetMapping(value = "/wallet/{phone}")
	    public List<Wallet> displaySpecific(@PathVariable (value = "phone") int phone) {
	    	List<Wallet> ans=this.walletService.findbyPhone(phone);
	    	if(ans.size()>0) {
	    		return ans;
	    	}
	    	throw new ResourceNotFoundException("user not Fondd with phone : "+phone);
	    	
	        
	    }
	    //3:Delete wallet
	    @DeleteMapping(value = "/wallet/{phone}")
		public ResponseEntity<Wallet> deleteUser(@PathVariable("phone") int phone){
			
	    	List<Wallet> ans=this.walletService.findbyPhone(phone);
	    	if(ans.size()>0) {
	    		this.walletService.delete(ans.get(0));
				return ResponseEntity.ok().build();

	    	}
	    	throw new ResourceNotFoundException("user not Fondd with phone : "+phone);
	   }
	    
//	    @PutMapping
//		public Wallet updateUser(int phone ,int amount) {
//	    	
//	    	List<Wallet> ans=this.walletService.findbyPhone(phone);
//	    	if(ans.size()>0) {
//	    		Wallet existingUser=ans.get(0);
//	    		return this..walletService.saveSingle(existingUser);
//	    		
//	    	}
//	    	else {
//	    		throw new ResourceNotFoundException("user not Fondd with id : "+phone);
//	    	}
//			User existingUser = this.userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not Fondd with id : "+userId));
//			
//			existingUser.setFirstName(user.getFirstName());
//			existingUser.setLastName(user.getLastName());
//			existingUser.setEmail(user.getEmail());
//			existingUser.setAddress1(user.getAddress1());
//			existingUser.setAddress2(user.getAddress2());
//			existingUser.setMobile(user.getMobile());
//			
//			
//			return this.userRepository.save(existingUser);//this will directly save into database
//		}
	    
}
