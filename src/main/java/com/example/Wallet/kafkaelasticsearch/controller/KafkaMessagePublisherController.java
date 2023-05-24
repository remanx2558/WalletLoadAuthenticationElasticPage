package com.example.Wallet.kafkaelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

//In summary, the first method always publishes messages to the same topic (Kafka_Example), while the second method allows for dynamic topic selection based on the value provided in the request.
@RestController
@RequestMapping(value = "/kafka/messages")
public class KafkaMessagePublisherController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "Kafka_Example";


    @GetMapping("/publish/{message}")
    public String publishMessage(@PathVariable("message") final String message) {
        kafkaTemplate.send(TOPIC, message);
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
        kafkaTemplate.send(topicName, message);
        return "Message published successfully";
    }

}