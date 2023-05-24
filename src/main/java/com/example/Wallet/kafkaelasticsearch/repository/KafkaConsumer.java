package com.example.Wallet.kafkaelasticsearch.repository;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;

/**
 *     Kafka is a distributed streaming platform that allows the exchange of messages between producers and consumers.
 *     The class KafkaConsumer acts as a Kafka consumer and is responsible for consuming messages from a Kafka topic.
 *
 *
 */
@Service
public class KafkaConsumer {
    @Autowired
    private elasticrepo elasticRepository;

    //The concurrentKafkaListenerContainerFactory referenced in the @KafkaListener annotation is a bean that configures the Kafka listener container factory. It is responsible for creating and configuring the listener containers that consume Kafka messages.
    @KafkaListener(topics = "txnById", groupId = "group_json",
            containerFactory = "concurrentKafkaListenerContainerFactory")//@KafkaListener: annotation is from the Spring for Apache Kafka project and is used to configure a method to be a Kafka message listener.
    public void consumeJson(ElasticTransaction transaction) {
        //The KafkaConsumer class also interacts with an Elasticsearch repository (elasticRepository) for saving the consumed ElasticTransaction objects.
        //After consuming a JSON message from Kafka, the consumeJson method saves the ElasticTransaction object to the Elasticsearch repository using the elasticRepository.save(transaction) statement.
        elasticRepository.save(transaction);
        System.out.println("Consumed JSON Message: " + transaction);
    }


    //ConsumerRecord<String, ElasticTransaction> object instead of a plain String message. This allows access to both the key and value of the Kafka record.
    @KafkaListener(topics = "txnById", groupId = "group_json", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, ElasticTransaction> record) {
        ElasticTransaction transaction = record.value();
        elasticRepository.save(transaction);
        System.out.println("Consumed JSON Message: " + transaction);
    }
    }
