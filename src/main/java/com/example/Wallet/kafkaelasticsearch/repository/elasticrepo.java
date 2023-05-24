package com.example.Wallet.kafkaelasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.Wallet.kafkaelasticsearch.model.ElasticTransaction;

import java.util.List;

/**
 * In summary, the code defines a repository interface that extends ElasticsearchRepository to perform CRUD operations
 * on ElasticTransaction entities stored in Elasticsearch. It demonstrates the integration between Spring and Elasticsearch,
 * utilizing Spring Data Elasticsearch to simplify Elasticsearch operations in a Spring application.
 *
 * The code demonstrates the use of Spring Data Elasticsearch, which integrates Spring applications with Elasticsearch,
 * a distributed search and analytics engine. The ElasticsearchRepository interface provided by Spring Data Elasticsearch
 * abstracts away the complexities of interacting with Elasticsearch and provides a convenient way to perform CRUD operations and search queries.
 *
 *
 * Interface Declaration: The elasticrepo interface is declared, extending the ElasticsearchRepository interface.
 * This interface is provided by Spring Data Elasticsearch and provides basic CRUD operations for working with Elasticsearch.
 *
 *
 */
@Repository
public interface elasticrepo extends ElasticsearchRepository<ElasticTransaction, String> {
    List<ElasticTransaction> findBySenderphone(Integer phone);

    List<ElasticTransaction> findByReceiverphone(Integer phone);
}