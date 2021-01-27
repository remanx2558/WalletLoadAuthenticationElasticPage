package com.example.Wallet.kafkaelasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;

import java.util.List;

@Repository
public interface elasticrepo extends ElasticsearchRepository<ElasticTransaction, String> {
    List<ElasticTransaction> findBySenderphone(Integer phone);

    List<ElasticTransaction> findByReceiverphone(Integer phone);
}