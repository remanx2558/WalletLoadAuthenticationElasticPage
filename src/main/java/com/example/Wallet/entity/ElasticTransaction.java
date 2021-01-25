package com.example.Wallet.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.persistence.Id;

@Document(indexName="rohan",shards=2)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticTransaction {



    @Id                         //defining id field (int in this case)
    @GeneratedValue
    @Column(name = "transactionid")
    private Integer transactionid;
    @Column(name = "senderphone")
    private Integer senderphone;
    @Column(name = "receiverphone")
    private Integer receiverphone;
    @Column(name = "amount")
    private Integer amount;

    //public Transaction() {
    //}


    public ElasticTransaction(Integer senderphone, Integer receiverphone, Integer amount) {
        this.senderphone = senderphone;
        this.receiverphone = receiverphone;
        this.amount = amount;
    }

    public void setTransactionid(Integer transactionid) {
        this.transactionid = transactionid;
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

    public Integer getTransactionid() {
        return transactionid;
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