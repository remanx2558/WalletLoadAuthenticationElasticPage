package com.example.Wallet.kafkaelasticsearch.controller;

import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

//In summary, the first method always publishes messages to the same topic (Kafka_Example), while the second method allows for dynamic topic selection based on the value provided in the request.
@RestController
@RequestMapping(value = "/kafka/messages")
public class KafkaMessagePublisherController {
    @Autowired
    private KafkaTemplate<String, ElasticTransaction> kafkaTemplate;
    private static final String TOPIC = "Kafka_Example";


    @GetMapping("/publish/{message}")
    public String publishMessage(@PathVariable("message") final String message) {
        ElasticTransaction messageTransaction=new ElasticTransaction();
        messageTransaction.setTransactionid(message);

        kafkaTemplate.send(TOPIC, messageTransaction);
        return "Published successfully";
    }


    /**
     * Publishes a message to the Kafka topic.
     *
     * @param message The message to be published
     * @return A success message indicating the successful publication
     */
    @PostMapping("/{topicName}")
    public String publishMessageInTopic(@PathVariable("topicName") String topicName, @RequestBody String message) {
        ElasticTransaction messageTransaction=new ElasticTransaction();
        messageTransaction.setTransactionid(message);

        kafkaTemplate.send(TOPIC, messageTransaction);
        return "Message published successfully";
    }

}