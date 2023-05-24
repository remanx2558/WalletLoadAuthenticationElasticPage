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
public class KafkaConfiguration {
    //The producerFactory() method creates a ProducerFactory bean that is responsible for creating Kafka producers
    @Bean//The @Bean annotation is used to mark each method as a bean, making them available for dependency injection.
    public ProducerFactory<String, ElasticTransaction> producerFactory() {
        //. It creates a HashMap called config to hold the Kafka producer configuration properties
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");//the Kafka broker addresses
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    //bean, which is a higher-level abstraction for sending Kafka messages. It uses the producerFactory() to create the Kafka producer.and provides convenient methods for sending messages to Kafka topics.
    @Bean
    public KafkaTemplate<String, ElasticTransaction> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //

    /**
     *
     * In summary, a Kafka consumer is a more generic term that refers to any component responsible for consuming messages
     * from Kafka topics. On the other hand, a Kafka listener is a specific concept within frameworks or libraries
     * that simplifies the consumption of messages by providing higher-level abstractions and callback mechanisms.
     * Listeners are built on top of Kafka consumers and handle the underlying complexities, allowing developers to
     * focus on message processing logic.
     *
     * the Following two methords  code demonstrates the creation of a Kafka consumer factory and the configuration of a ConcurrentKafkaListenerContainerFactory for consuming messages from Kafka topics. The ElasticTransaction class is used to define the type of messages being exchanged, and the code includes message serialization using JSON
     *
     * The transactionConsumerFactory() method creates a DefaultKafkaConsumerFactory bean responsible for creating Kafka consumers.
     *
     *
     * Serialization:-
     *     String Deserialization: String deserialization is used for deserializing the key of the Kafka message. The key is expected to be a string value, and StringDeserializer is used to deserialize it. The key deserializer is specified using the KEY_DESERIALIZER_CLASS_CONFIG property in the consumer configuration.
     *     JSON Deserialization: JSON deserialization is used for deserializing the value of the Kafka message. The value is expected to be in JSON format, and JsonDeserializer is used to deserialize it. The JSON deserializer is specified using the VALUE_DESERIALIZER_CLASS_CONFIG property in the consumer configuration.
     *     By using JSON deserialization for the value, the code indicates that the actual message content is represented as a JSON object of type ElasticTransaction. The JsonDeserializer is responsible for parsing the JSON data and converting it into an instance of the ElasticTransaction class.
     *
     *
     *    Bootstrap Servers: The bootstrap.servers property specifies the list of bootstrap servers that the Kafka consumer should connect to. These servers are responsible for handling client requests and managing the distribution of Kafka topics and partitions. In the code, the value "127.0.0.1:9092" is set as the bootstrap servers configuration. This means that the consumer will attempt to connect to a Kafka broker running on the local machine at port 9092. You would need to replace this value with the actual address and port of your Kafka cluster.
     *
     *   Group ID: The group.id property specifies the unique identifier for the consumer group that the Kafka consumer belongs to. A consumer group is a group of Kafka consumers that work together to consume and process messages from one or more Kafka topics. Each consumer within a group is responsible for processing a subset of the partitions within the topics. The group ID helps Kafka track the progress of each consumer within the group and ensures that each message is consumed by only one consumer within the group. In the code, the value "group-json" is set as the group ID. You can customize this value to uniquely identify your consumer group.
     *
     *   //the DefaultKafkaConsumerFactory class, which is responsible for creating and configuring Kafka consumer instances for consuming messages from Kafka topics.
     *
     */
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


    /**
     *     Purpose: The ConcurrentKafkaListenerContainerFactory is responsible for creating Kafka listener containers, which are components that consume messages from Kafka topics.
     *
     *     Concurrency and Parallelism: By configuring the ConcurrentKafkaListenerContainerFactory, you can specify the level of concurrency for consuming messages from Kafka. This means you can process messages in parallel using multiple threads, which can improve overall throughput and responsiveness. By setting the appropriate concurrency level, you can distribute the workload across multiple threads and handle a high volume of messages efficiently.
     *
     *     Consumer Configuration: The ConcurrentKafkaListenerContainerFactory utilizes the transactionConsumerFactory() method to create a DefaultKafkaConsumerFactory. This consumer factory is responsible for creating and configuring Kafka consumer instances used by the listener containers. It provides necessary configuration properties such as bootstrap servers, group ID, key and value deserializers, and other consumer-related settings.
     *
     *     Listener Container Customization: The ConcurrentKafkaListenerContainerFactory allows you to customize various aspects of the Kafka listener containers. For example, you can configure error handling, message acknowledgment, retry policies, batch processing, and other container-specific properties. By customizing the container factory, you can adapt the Kafka listeners to match the specific requirements of your application.
     *
     *     Return Value: The concurrentKafkaListenerContainerFactory() method creates an instance of ConcurrentKafkaListenerContainerFactory<String, ElasticTransaction> and configures it accordingly. It sets the consumer factory created by the transactionConsumerFactory() method using the setConsumerFactory() method. Finally, it returns the configured concurrentKafkaListenerContainerFactory bean.
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ElasticTransaction>
    concurrentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ElasticTransaction> concurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory();

        //ElasticTransaction: It is used in the Kafka configuration to define the type of messages that will be exchanged between the Kafka producer and consumer.
        //Message Serialization: Kafka messages are typically serialized before being sent to the Kafka cluster and deserialized when received by consumers. In this code, the ElasticTransaction class is used to specify the type of values that will be serialized and deserialized when producing and consuming Kafka messages. The JsonSerializer and JsonDeserializer classes are configured to handle the JSON serialization and deserialization of ElasticTransaction objects.
        //Integration with Elasticsearch: The ElasticTransaction class might be used as a data transfer object (DTO) for storing transaction information in Elasticsearch. Elasticsearch is a popular search and analytics engine that can be used to index and search large volumes of data efficiently. By using the ElasticTransaction class, you can easily map transaction data to Elasticsearch documents and perform indexing, querying, and analysis operations on the stored transactions.
        concurrentKafkaListenerContainerFactory.setConsumerFactory(transactionConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }


}

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
 *
 * Improvmnts Suggestions:
 *     1) Consider providing more configuration options for fine-tuning Kafka behavior, such as setting the number of consumer threads or configuring Kafka security protocols, based on the specific requirements of your application.
 */