package com.example.Wallet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.entity.TransModel;
import com.example.Wallet.entity.Wallet;
import com.example.Wallet.repository.ElasticRepository;
import com.example.Wallet.repository.TransRepository;
import com.example.Wallet.repository.WalletRepository;
import com.example.Wallet.service.KafkaTransaction;

@RestController
public class ElasticTransactionResource {
//    @Autowired
//    TransRepository transactionRepository;        // defining reference
//
//    @Autowired
//    ElasticRepository elasticRepository;
//
//    @Autowired
//    WalletRepository walletRepository;
//
//    @Autowired
//    KafkaTransaction kafkaTransaction;
//
//    @Autowired
//    UsersRepository usersRepository;
//    @GetMapping(value = "/transaction/all")
//    public List<TransModel> displayAll(){
//        return transactionRepository.findAll();
//    }
//
//    @PostMapping(value = "/transaction")           // post mapping
//    public String persist(@RequestBody final Transaction transaction) {
//        List<Wallet> sender_phone = walletRepository.findByPhone(transaction.getSenderphone());
//        List<Wallet> receiver_phone = walletRepository.findByPhone(transaction.getReceiverphone());
//        if(!sender_phone.isEmpty() && !receiver_phone.isEmpty()) {
//            if(sender_phone.get(0).getBalance() >= transaction.getAmount()) {
//                //System.out.println(transaction.getTransactionid());
//                transactionRepository.save(transaction);
//
//                sender_phone.get(0).incrementBalance(-transaction.getAmount()); // editing summoned objects
//                receiver_phone.get(0).incrementBalance(transaction.getAmount());
//                walletRepository.save(sender_phone.get(0));    //saving back the data
//                walletRepository.save(receiver_phone.get(0));
//                return "transaction successful";
//            }
//            else return "insufficient funds";
//        }
//        else return "invalid phone number";
//    }
//    @PostMapping(value = "/trans")           // post mapping
//    public String elastic(@RequestBody final Transaction transaction) {
//        List<Wallet> sender_phone = walletRepository.findByPhone(transaction.getSenderphone());
//        List<Wallet> receiver_phone = walletRepository.findByPhone(transaction.getReceiverphone());
//        if(!sender_phone.isEmpty() && !receiver_phone.isEmpty()) {
//            if(sender_phone.get(0).getBalance() >= transaction.getAmount()) {
//                ///change for kafka
//                //transactionRepository.save(transaction); // for simple repository
//                //elasticRepository.save(transaction);  //for elastic search
//                try {
//                    kafkaTransaction.send(transaction);
//                }catch (Exception e){
//
//                }
//                //kafkaTransaction.send("debug1"); //debug
//                ///change for kafka
////                sender_phone.get(0).incrementBalance(-transaction.getAmount()); // editing summoned objects
////                receiver_phone.get(0).incrementBalance(transaction.getAmount());
//                walletRepository.save(sender_phone.get(0));    //saving back the data
//                walletRepository.save(receiver_phone.get(0));
//                return "transaction successful";
//            }
//            else return "insufficient funds";
//        }
//        else return "invalid phone number";
//    }
//
//    @GetMapping(value = "/transaction")            //part 4
//    public String getStatus(@RequestParam(value = "txnId", defaultValue = "") Integer transid) {
//        List<TransModel> comparative_transactions = transactionRepository.findByTransactionid(transid);
//        if(comparative_transactions.isEmpty())
//            return "failed";
//        else return "Successfull";
//    }
//
//    @GetMapping(value = "/transactions")            //part 3
//    public List<TransModel> getPaginated(@RequestParam(value = "userId", defaultValue = "") String uid,
//                                          @RequestParam(value = "page", defaultValue = "0") Integer pageno,
//                                          @RequestParam(value = "size", defaultValue = "1") Integer pagesize) {
//        List<Users> user_list = usersRepository.findByUserid(uid);
//        if(!user_list.isEmpty()) {
//            int phone_number = user_list.get(0).getPhone();  //get phones from id
//            List<TransModel> receiver_list = transactionRepository.findByPayerphone(phone_number);
//            List<TransModel> sender_list = transactionRepository.findByPayeephone(phone_number);
//            sender_list.addAll(receiver_list);
//            List<TransModel> return_list = sender_list.subList(pageno*pagesize,(pageno+1)*pagesize);
//            return return_list;
//        }
//        else {
//            List <TransModel> Dummy = new ArrayList<>();
//            return Dummy;
//        }
//    }
}