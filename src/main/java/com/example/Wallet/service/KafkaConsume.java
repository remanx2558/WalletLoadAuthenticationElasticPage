package com.example.Wallet.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Wallet.entity.TransModel;
import com.example.Wallet.repository.TransRepository; 
@Service
public class KafkaConsume {

	 @Autowired
	    TransRepository transactionRepository;
	    @KafkaListener(topics = "trans", groupId = "group-id")
	    public void consume(TransModel transaction)
	    {
	    	
	        transactionRepository.save(transaction); //save to normal repo //change for elastic
	        
	    }


	}
