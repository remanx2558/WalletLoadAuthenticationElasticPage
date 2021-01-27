package com.example.Wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="wallet")
public class Wallet {
	 @Id
	 @Column(name="phone")
	 @NotNull
	 @Size(min = 2 , message="First Name should have atelast 2 characters")
	    private Integer phone;
	 @NotBlank
	 @Column(name="balance")
	    private Integer balance;

	    public Wallet() {
	    }

	    public Wallet(Integer phone, Integer balance) {
	        this.phone = phone;
	        this.balance = balance;
	    }

	    public Integer getPhone() {
	        return phone;
	    }

	    public void setPhone(Integer phone) {
	        this.phone = phone;
	    }

	    public Integer getBalance() {
	        return balance;
	    }

	    public void setBalance(Integer balance) {
	        this.balance = balance;
	    }

	    public void changeBalance(Integer amount) {
	        this.balance += amount;
	    }
}
