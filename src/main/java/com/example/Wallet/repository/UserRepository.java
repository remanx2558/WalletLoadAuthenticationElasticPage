package com.example.Wallet.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Wallet.entity.User;
public interface UserRepository extends JpaRepository<User,Long>{
}