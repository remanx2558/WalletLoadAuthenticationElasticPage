package com.example.Wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="transaction")
public class TransModel {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	    private Integer transactionid;
	@NotNull
	@Column(name=" payerphone")
	    private Integer payerphone;
	@NotNull
	@Column(name=" payeephone")
	    private Integer payeephone;
	
	
	@Column(name="amount")
	    private Integer amount;
	

	    public TransModel() {
//	        super();
	    }

	    public TransModel( Integer payerphone, Integer payeephone, Integer amount) {
//	       
	        this.payerphone = payerphone;
	        this.payeephone = payeephone;
	        this.amount = amount;
	    }

	    public Integer getTransactionid() {
	        return transactionid;
	    }

	    public void setTransactionid(Integer transactionid) {
	        this.transactionid = transactionid;
	    }

	    public Integer getPayerphone() {
	        return payerphone;
	    }

	    public void setPayerphone(Integer payerphone) {
	        this.payerphone = payerphone;
	    }

	    public Integer getPayeephone() {
	        return payeephone;
	    }

	    public void setPayeephone(Integer payeephone) {
	        this.payeephone = payeephone;
	    }

	    public Integer getAmount() {
	        return amount;
	    }

	    public void setAmount(Integer amount) {
	        this.amount = amount;
	    }
}
