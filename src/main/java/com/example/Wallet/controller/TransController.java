package com.example.Wallet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.entity.TransModel;
import com.example.Wallet.entity.Wallet;
import com.example.Wallet.exception.ResourceNotFoundException;
import com.example.Wallet.service.TransService;
import com.example.Wallet.service.WalletService;

@RestController
public class TransController {
	 @Autowired                         //dependency injection
	    TransService transService;
	    @Autowired                         //dependency injection
	    WalletService walletService;

//	   //1:Display  all transactions
	    @GetMapping(value = "/transaction/all")
	    public List<TransModel> displayAll() {
	        return transService.displayall();
	    }

	    
//	    //2. Display specific
//	    @GetMapping(value = "/transaction/{transactionid}")
//	    TransModel displayTransaction2(@PathVariable("transactionid") Integer transactionid){
//	    	return transService.displayTransaction(transactionid);
//	    }

	  
	    //3:  transaction status
	    @GetMapping(value = "/transaction/{transactionid}")
	    public String displayTransaction(@PathVariable("transactionid") Integer transactionid) {
	        List<TransModel> checkTransaction = transService.findByTransactionid(transactionid);
	        if (checkTransaction.isEmpty())
	            return "Transaction Status: failed";
	        else return "Transaction Status: Successful";
	    }
	    
	    //4: transfer Money :Post
	    @PostMapping(value = "/transaction")          
	    public TransModel addtransaction(@RequestBody TransModel transModel) {

	        List<Wallet> payer_phone = walletService.findbyPhone(transModel.getPayerphone());
	        List<Wallet> payee_phone = walletService.findbyPhone(transModel.getPayeephone());
	        
	        if(payee_phone.isEmpty()) {
	        	throw new ResourceNotFoundException("Payee phone not exist");
	        	
	        }
	        else if(payer_phone.isEmpty()) {
	        	throw new ResourceNotFoundException("Payer phone not exist");
	        	//return " Payee phone not exist";
	        }
	        else if(payer_phone.get(0).getBalance() < transModel.getAmount()) {
	        	throw new ResourceNotFoundException("Insufficient balance");
	        	//return "Insufficient balance";
	        	
	        }
	       
	                    transService.saveSingle(transModel);
//	                    payee_phone.get(0).changeBalance(+transModel.getAmount());
//	                    payer_phone.get(0).changeBalance(-transModel.getAmount());
	                    
	                    walletService.updateUserWallet(payer_phone.get(0), -(transModel.getAmount()));
	                    walletService.updateUserWallet(payee_phone.get(0), +transModel.getAmount());
	                    
	                    return transModel;
	                   // return "transaction successful";
	              
	    }
	    
	    //5: Transaction by User
	  //for checking transaction of particular phone number
	    @GetMapping(value = "/transaction/all/{payerphone}")
	    public List<TransModel> displayTransactions(@PathVariable("payerphone") int phone) {
//	            return transService.displayTransaction(transactionid);
	    	
	    	List<Wallet> user_list = walletService.findbyPhone(phone);
	    	if(user_list.size()>0) {
	    		List<TransModel> payer_phone = transService.findbyPayerPhone(phone);
		        List<TransModel> payee_phone = transService.findbyPayeePhone(phone);
		        
		        List<TransModel> newList = new ArrayList<TransModel>();
		        newList.addAll(payee_phone);
		        newList.addAll(payer_phone);
		        return newList;
	    	}
	    	throw new ResourceNotFoundException("This Number do not exist put a valid number");
	    	
	        
	    }

	    
	    
	    
	    
//	    //5:pagenation 1 : sender

	    @GetMapping(value = "/transactions")            //part 3
	    public List<TransModel> getPaginated(@RequestParam(value = "phone", defaultValue = "123") Integer phone,
	                                          @RequestParam(value = "page", defaultValue = "0") Integer pageno,
	                                          @RequestParam(value = "size", defaultValue = "1") Integer pagesize) {
	        List<Wallet> user_list = walletService.findbyPhone(phone);
	        
	        if(!user_list.isEmpty()) {
	            int phone_number = user_list.get(0).getPhone();  //get phones from id
	            List<TransModel> receiver_list = transService.findbyPayerPhone(phone_number);
	            List<TransModel> sender_list = transService.findbyPayeePhone(phone_number);
	            sender_list.addAll(receiver_list);
	            int front=Math.min(pageno*pagesize, sender_list.size());
	            int back=Math.min((pageno+1)*pagesize, sender_list.size());
	            
	            List<TransModel> return_list = sender_list.subList(front,back);
	            return return_list;
	        }
	        else {
	        	throw new ResourceNotFoundException("This Number do not exist put a valid number");
	        }
	    }


	    
	  
}
