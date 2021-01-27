package com.example.Wallet.kafkaelasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Document(indexName="elastic",indexStoreType = "transaction",shards = 2)
public class ElasticTransaction {
        private String transactionid;

        private Integer senderphone;

        private Integer receiverphone;

        private Integer amount;

        public ElasticTransaction() {
        }


        public ElasticTransaction(String transactionId,Integer senderphone, Integer receiverphone, Integer amount) {
                this.transactionid = transactionId;
        this.senderphone = senderphone;
        this.receiverphone = receiverphone;
        this.amount = amount;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public String getTransactionid() {
                return transactionid;
        }

        public void setTransactionid(String txnid) {
                this.transactionid = txnid;
        }

public void setSenderphone(Integer senderphone) {
        this.senderphone = senderphone;
        }

public void setReceiverphone(Integer receiverphone) {
        this.receiverphone = receiverphone;
        }

public void setAmount(Integer amount) {
        this.amount = amount;
        }



public Integer getSenderphone() {
        return senderphone;
        }

public Integer getReceiverphone() {
        return receiverphone;
        }

public Integer getAmount() {
        return amount;
        }
}