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

import javax.websocket.SendResult;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TransController2 {

    private static final String KAFKA_TOPIC = "txnById";

    @Autowired
    private TransService transactionService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private elasticrepo elasticTransactionRepository;

    @Autowired
    private KafkaTemplate<String, ElasticTransaction> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate2;

    // Get transaction status by ID
    @GetMapping("/transactions/{transactionId}")
    public String getTransactionStatus(@PathVariable("transactionId") Integer transactionId) {
        List<TransModel> transactions = transactionService.findByTransactionid(transactionId);
        if (transactions.isEmpty())
            return "Transaction Status: Failed";
        else
            return "Transaction Status: Successful";
    }

    // Get all transactions with pagination
    @GetMapping("/transactions")
    public ResponseEntity<List<TransModel>> getAllTransactions(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "1") Integer pageSize) {
        List<TransModel> transactions = transactionService.getAllTransactions(pageNo, pageSize);
        return new ResponseEntity<>(transactions, new HttpHeaders(), HttpStatus.OK);
    }

    // Get transactions by phone number
    @GetMapping("/transactions/phone/{phoneNumber}")
    public List<TransModel> getTransactionsByPhone(@PathVariable("phoneNumber") Integer phoneNumber) {
        List<TransModel> payerTransactions = transactionService.findbyPayerPhone(phoneNumber);
        List<TransModel> payeeTransactions = transactionService.findbyPayeePhone(phoneNumber);
        List<TransModel> allTransactions = new ArrayList<>();
        allTransactions.addAll(payeeTransactions);
        allTransactions.addAll(payerTransactions);
        return allTransactions;
    }

    // Transfer money from one wallet to another wallet
    @PostMapping("/elastic/transactions")
    public String createTransaction(@RequestBody TransModel transaction) {
        List<Wallet> payerWallets = walletService.findbyPhone(transaction.getPayerphone());
        List<Wallet> payeeWallets = walletService.findbyPhone(transaction.getPayeephone());

        if (!payerWallets.isEmpty() && !payeeWallets.isEmpty()) {
            Wallet payerWallet = payerWallets.get(0);
            Wallet payeeWallet = payeeWallets.get(0);
            int payerBalance = payerWallet.getBalance();
            int transactionAmount = transaction.getAmount();

            if (payerBalance >= transactionAmount) {
                transactionService.addtransaction(transaction);
                kafkaTemplate.send(KAFKA_TOPIC, new ElasticTransaction(transaction));

                payerWallet.changeBalance(-transactionAmount);
                payeeWallet.changeBalance(transactionAmount);

                return "Transaction Successful";
            } else {
                return "Insufficient Balance";
            }
        } else {
            return "Phone number(s) do not exist";
        }
    }

    @GetMapping("/elastictransactions/{phone}")
    public List<ElasticTransaction> getElasticTransactions(@PathVariable Integer phone) {
        List<ElasticTransaction> senderTransactions = elasticTransactionRepository.findBySenderphone(phone);
        List<ElasticTransaction> receiverTransactions = elasticTransactionRepository.findByReceiverphone(phone);
        List<ElasticTransaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(senderTransactions);
        allTransactions.addAll(receiverTransactions);
        return allTransactions;
    }

    // Publish message to Kafka
    @GetMapping("/publish/{name}")
    public String publishToKafka(@PathVariable("name") final String name) {
        kafkaTemplate2.send(KAFKA_TOPIC, new User(name, "Technology", "yash@gmail", "X", "Y", 12000L));
        return "Published successfully";
    }

    @GetMapping(value = "/transaction/all")
    public List<TransModel> displayAll() {
        return transactionService.displayall();
    }

}