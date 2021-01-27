package com.example.Wallet.kafkaelasticsearch.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;


@Service
public class KafkaConsumer {
    @Autowired
    private elasticrepo elasticRepository;

    @KafkaListener(topics = "txnById", groupId = "group_json",
            containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consumeJson(ElasticTransaction transaction) {
        elasticRepository.save(transaction); /////////////////////////for saving in elastic repository//////
        System.out.println("Consumed JSON Message: " + transaction);
    }
    

//    @KafkaListener(topics = "txnById", groupId = "group_json")
//    public void consume(String message) {
//             System.out.println("Consumed JSON Message: " + message);
//    }
    }
