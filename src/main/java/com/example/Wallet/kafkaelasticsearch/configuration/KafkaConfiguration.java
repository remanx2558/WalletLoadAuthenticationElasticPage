package com.example.Wallet.kafkaelasticsearch.configuration;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Concepts Used:
 *
 *     Producer: The producerFactory() method creates a ProducerFactory bean responsible for creating Kafka producers. It configures the bootstrap servers, key serializer(StringSerializer), and value serializer for the producer(JsonSerializer for the ElasticTransaction class)in the config map.
 *
 *     Consumer: The transactionConsumerFactory() method creates a DefaultKafkaConsumerFactory bean responsible for creating Kafka consumers. It configures the bootstrap servers, group ID, key deserializer, and value deserializer for the consumer.
 *
 *     Topics: The configuration sets up the topics for producing and consuming Kafka messages. The topic is not explicitly defined in this code, but it should be configured elsewhere in the application.
 *
 *     Serialization: The configuration specifies serializers and deserializers for keys and values. It uses StringSerializer and JsonSerializer for serialization, and StringDeserializer and JsonDeserializer for deserialization.
 *     Serialization plays a crucial role in Kafka as it is responsible for converting data objects into a format that can be efficiently transmitted and stored in Kafka topics. When messages are produced to Kafka, they need to be serialized into a byte array, and when consumed, they need to be deserialized back into their original object form.
 *     Producer Serialization: The ProducerFactory bean created in the producerFactory() method configures the serialization of the message value. It sets the VALUE_SERIALIZER_CLASS_CONFIG property to JsonSerializer.class. This means that when producing messages, the JsonSerializer will be used to serialize the message value (of type ElasticTransaction) into a JSON format before sending it to Kafka. This allows for easy representation and compatibility of the message data.
 *     Consumer Serialization: The DefaultKafkaConsumerFactory bean created in the transactionConsumerFactory() method configures the deserialization of the message value. It sets the VALUE_DESERIALIZER_CLASS_CONFIG property to JsonDeserializer.class. This indicates that when consuming messages, the JsonDeserializer will be used to deserialize the message value from JSON back into its original object form (ElasticTransaction). This ensures that the consumer can correctly interpret and process the message data.
 */
@EnableKafka//annotation enables Kafka functionality in the Spring application.
@Configuration//annotation indicates that this class is a configuration class.
@ComponentScan
public class KafkaConfiguration {
    //The producerFactory() method creates a ProducerFactory bean that is responsible for creating Kafka producers
    @Bean//The @Bean annotation is used to mark each method as a bean, making them available for dependency injection.
    public ProducerFactory<String, ElasticTransaction> producerFactory() {
        //. It creates a HashMap called config to hold the Kafka producer configuration properties
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    //bean, which is a higher-level abstraction for sending Kafka messages. It uses the producerFactory() to create the Kafka producer.
    @Bean
    public KafkaTemplate<String, ElasticTransaction> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //The transactionConsumerFactory() method creates a DefaultKafkaConsumerFactory bean responsible for creating Kafka consumers.
    @Bean
    public DefaultKafkaConsumerFactory transactionConsumerFactory() {
        //Similar to the producerFactory() method, it creates a HashMap called config to hold the Kafka consumer configuration properties. It sets the bootstrap servers, group ID, key deserializer (StringDeserializer), and value deserializer (JsonDeserializer for the ElasticTransaction class) in the config map. Then, it creates a DefaultKafkaConsumerFactory using the config map and returns it.
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        DefaultKafkaConsumerFactory defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(ElasticTransaction.class));
        return defaultKafkaConsumerFactory;

    }

    //This factory is used to create Kafka listener containers, which are responsible for consuming Kafka messages
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ElasticTransaction>
    concurrentKafkaListenerContainerFactory() {
        //it sets the ConsumerFactory created in the transactionConsumerFactory() method using the setConsumerFactory() method. Finally, it returns the concurrentKafkaListenerContainerFactory bean.

        //Concurrency and Parallelism: The ConcurrentKafkaListenerContainerFactory allows you to specify the level of concurrency for consuming messages from Kafka. By setting the appropriate concurrency level, you can achieve parallel processing of messages, which can improve the overall throughput and responsiveness of your application. This is particularly useful when dealing with a high volume of messages or when you want to process messages concurrently to distribute the workload across multiple threads.
        //Consumer Configuration: The ConcurrentKafkaListenerContainerFactory is responsible for creating and configuring the Kafka consumer instances used by the listener containers. It uses the transactionConsumerFactory() method to create a DefaultKafkaConsumerFactory that provides the necessary configuration for the consumer, such as bootstrap servers, group ID, key and value deserializers, etc.
       // Listener Container Customization: The ConcurrentKafkaListenerContainerFactory allows you to customize various aspects of the Kafka listener containers. For example, you can configure error handling, message acknowledgment, retry policies, batch processing, and other container-specific properties. By customizing the container factory, you can adapt the Kafka listeners to match your specific application requirements.
        ConcurrentKafkaListenerContainerFactory<String, ElasticTransaction> concurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(transactionConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }


}


//Improvements can be done:
//1) Consider providing more configuration options for fine-tuning Kafka behavior, such as setting the number of consumer threads or configuring Kafka security protocols, based on the specific requirements of your application.

/**
 *
 * Design Pattern Concepts Used:
 *
 *     Producer-Consumer Design Pattern: The code follows the producer-consumer design pattern where the producerFactory() method creates a Kafka producer and the transactionConsumerFactory() method creates a Kafka consumer. These components work together to produce and consume Kafka messages.
 *
 *     Serialization
 *
 *     Serialization configuration is crucial to ensure that producers and consumers can effectively communicate and understand the data exchanged through Kafka. It provides a consistent and interoperable data format that can be easily understood by different systems and applications. By using a standardized serialization mechanism, such as JSON in this case, the code promotes compatibility and allows for seamless integration between different components of the application ecosystem.
 *     Additionally, serialization allows for flexibility in message payload handling. Different serialization formats can be used depending on the requirements, such as Avro, Protobuf, or custom serializers, based on factors like performance, schema evolution, or data size.
 *     Overall, serialization configuration in Kafka is essential for ensuring the proper encoding and decoding of message data, facilitating interoperability, and enabling efficient communication between producers and consumers.
 */