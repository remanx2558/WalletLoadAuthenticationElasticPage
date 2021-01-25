package com.example.Wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Wallet.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {
    List<Wallet> findAll();
    public List<Wallet> findByPhone(Integer phone);
}