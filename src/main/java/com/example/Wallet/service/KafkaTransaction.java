package com.example.Wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Wallet.entity.TransModel;
@Service
public class KafkaTransaction {
	String kafkaTopic = "transactionstore";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    public void send(TransModel message) throws Exception{
        this.kafkaTemplate.send("trans", message);

    }
}
