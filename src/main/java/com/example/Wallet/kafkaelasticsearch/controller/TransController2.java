package com.example.Wallet.kafkaelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Wallet.entity.TransModel;
import com.example.Wallet.entity.User;
import com.example.Wallet.entity.Wallet;
import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;
import com.example.Wallet.kafkaelasticsearch.repository.elasticrepo;
import com.example.Wallet.service.TransService;
import com.example.Wallet.service.WalletService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransController2 {
    @Autowired                         //dependency injection
    TransService transService;
    @Autowired                         //dependency injection
    WalletService walletService;
    @Autowired
    private elasticrepo elasticRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;
    String kafkaTopic = "txnById";
    //to display all transactions
//    @GetMapping(value = "/transaction/all")
//    public List<TransModel> displayAll() {
//        return transService.displayall();
//    }


    //for checking status of transaction
    @GetMapping(value = "/transaction2/{transactionid}")
    public String displayTransaction(@PathVariable("transactionid") Integer transactionid) {
        List<TransModel> checkTransaction = transService.findByTransactionid(transactionid);
        if (checkTransaction.isEmpty())
            return "Transaction Status: failed";
        else return "Transaction Status: Successful";
    }

    //to display transaction using pagination
    @GetMapping(value = "/transaction2/all")
    public ResponseEntity<List<TransModel>> getAllTransactions(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "1") Integer pageSize) {
        List<TransModel> list = transService.getAllTransactions(pageNo, pageSize);

        return new ResponseEntity<List<TransModel>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    //for checking transaction of particular phone number
    @GetMapping(value = "/transaction2/phone/{payerphone}")
    public List<TransModel> displayTransactions(@PathVariable("payerphone") Integer phoneNo) {
//            return transService.displayTransaction(transactionid);
        List<TransModel> payer_phone = transService.findbyPayerPhone(phoneNo);
        List<TransModel> payee_phone = transService.findbyPayeePhone(phoneNo);
        List<TransModel> newList = new ArrayList<TransModel>();
        newList.addAll(payee_phone);
        newList.addAll(payer_phone);

        return newList;
    }

    //API to transfer money from one wallet to another wallet
    @PostMapping(value = "/transaction2")           // post mapping
    public String addtransaction(@RequestBody TransModel transModel) {
//        transService.addtransaction(transModel);
//        return "transaction successful";
        List<Wallet> payer_phone = walletService.findbyPhone(transModel.getPayerphone());
        List<Wallet> payee_phone = walletService.findbyPhone(transModel.getPayeephone());
        if (!payee_phone.isEmpty()) {
            if (!payer_phone.isEmpty()) {
                if (payee_phone.get(0).getBalance() >= transModel.getAmount()) {
                    transService.addtransaction(transModel);
                    payee_phone.get(0).changeBalance(-transModel.getAmount());
                    payer_phone.get(0).changeBalance(transModel.getAmount());
                    return "transaction successful";
                } else return "Insufficient balance";
            } else return "phone number doesn't exist";
        } else return "phone number doesn't exist";
    }

    @PostMapping("/elastictransaction")
    public String createTxn(@RequestBody ElasticTransaction transaction) {
        List<Wallet> payer_num=walletService.findbyPhone(transaction.getSenderphone());
        List<Wallet> payee_num=walletService.findbyPhone(transaction.getReceiverphone());

        int payer_balance=payer_num.get(0).getBalance();
        int txnAmount=transaction.getAmount();

        if(!payer_num.isEmpty())
        {
            if(!payee_num.isEmpty())
            {
                if(payer_balance>=txnAmount)
                {
//                  transService.addtransaction(transaction);
                    kafkaTemplate.send(kafkaTopic, transaction);


                    Wallet payer=payer_num.get(0);
                    Wallet payee=payee_num.get(0);

                    payer.changeBalance(-txnAmount);
                    payee.changeBalance(txnAmount);



                    return "Successfully sent";
                }
                else return "Insufficient balance";
            } else return "payee doesn't exist";
        } else return "payer doesn't exist";

    }

    @GetMapping("/elastictransaction/{phone}")
    public List<ElasticTransaction> getTransactions(@PathVariable Integer phone){
        List<ElasticTransaction> sender=elasticRepository.findBySenderphone(phone);
        List<ElasticTransaction> reciever=elasticRepository.findByReceiverphone(phone);

        List<ElasticTransaction> newlist= new ArrayList<ElasticTransaction>();
        newlist.addAll(sender);
        newlist.addAll(reciever);
        return newlist;
    }
    
    
    ///////////simle kaska and spring
    @GetMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {

        kafkaTemplate.send(kafkaTopic, new User(name, "Technology","yash@gmail" ,"X","Y",12000L));

        return "Published successfully";
    }
    
    
    
    

}