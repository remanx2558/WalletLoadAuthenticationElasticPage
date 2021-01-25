package com.example.Wallet.repository;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.Wallet.entity.ElasticTransaction;
public interface ElasticRepository extends ElasticsearchRepository<ElasticTransaction, Integer> {       //defining database (object type, id type)
    public List<ElasticTransaction> findByTransactionid(Integer transactionid); //find by email
    public List<ElasticTransaction> findBySenderphone(Integer senderphone);
    public List<ElasticTransaction> findByReceiverphone(Integer receiverphone);
    //public List<Users> findById(String id);
    //public List<Wallet> findByPhone(Integer phone); //find by phone number
}