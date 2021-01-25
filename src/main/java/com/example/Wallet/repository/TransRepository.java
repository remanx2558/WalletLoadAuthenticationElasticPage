package com.example.Wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.Wallet.entity.TransModel;

public interface TransRepository extends JpaRepository<TransModel,Integer> ,PagingAndSortingRepository<TransModel,Integer> {
    public List<TransModel> findByTransactionid(Integer transactionid);
    public List<TransModel> findByPayeephone(Integer payeephone);
    public List<TransModel> findByPayerphone(Integer payerphone);
}
