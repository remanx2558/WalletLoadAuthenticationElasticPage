package com.example.Wallet.kafkaelasticsearch.model;

import com.example.Wallet.entity.TransModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//
//It specifies the indexName, indexStoreType, and shards properties for indexing and storage configuration.

/**
 * Java class ElasticTransaction that is used as a model for Kafka and Elasticsearch integration in a Spring application.\
 * @Document: is used to indicate that instances of this class will be stored as documents in Elasticsearch
 * The @Document annotation plays a crucial role in mapping the ElasticTransaction class to the corresponding Elasticsearch index and configuring the index properties such as name, store type, and number of shards.
 * By using the @Document annotation, you can define how your Java objects are persisted in Elasticsearch, including specifying the index name, store type, and shard configuration. This allows you to easily map and store your domain objects in Elasticsearch and perform various operations on them.
 *
 *     indexName: This attribute specifies the name of the Elasticsearch index where the documents of the annotated class will be stored. In this case, the indexName is set to "elastic", indicating that the documents of the ElasticTransaction class will be stored in the "elastic" index.
 *
 *     indexStoreType: This attribute specifies the type of index store used by Elasticsearch for the annotated class. It is set to "transaction" in this code snippet, indicating that the documents will be stored using the "transaction" index store type.
 *
 *     shards: This attribute specifies the number of shards that the Elasticsearch index should have. Shards are the units of scalability in Elasticsearch, allowing data to be distributed across multiple nodes in a cluster for efficient storage and retrieval. In this case, the shards attribute is set to 2, indicating that the "elastic" index should have 2 shards.
 */
@Document(indexName="elastic",indexStoreType = "transaction",shards = 2)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticTransaction {

        @Id//@Id annotation from the Spring Data Elasticsearch library is used to mark the transactionid field as the identifier field for Elasticsearch.
        @GeneratedValue(strategy = GenerationType.IDENTITY)////@GeneratedValue annotation from the javax.persistence package is used to specify the generation strategy for the transactionid field
        private String transactionid;

        private Integer senderphone;
        private Integer receiverphone;
        private Integer amount;

        public ElasticTransaction(TransModel transModel){
                transactionid= String.valueOf(transModel.getTransactionid());
                senderphone=transModel.getPayeephone();
                receiverphone=transModel.getPayerphone();
                amount=transModel.getAmount();
        }

}