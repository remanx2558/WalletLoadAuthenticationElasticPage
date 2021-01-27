package com.example.Wallet.kafkaelasticsearch.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/kafka/")
public class  KafkaWebController {
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    private static final String TOPIC = "Kafka_Example";
//
//    @GetMapping("/publish/{message}")
//    public String post(@PathVariable("message") final String message) {
//
//        kafkaTemplate.send(TOPIC, message);
//
//        return "Published successfully";
//    }
}