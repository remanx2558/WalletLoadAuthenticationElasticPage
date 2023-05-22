package com.example.Wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	    }

	    public TransModel( Integer payerphone, Integer payeephone, Integer amount) {
	        this.payerphone = payerphone;
	        this.payeephone = payeephone;
	        this.amount = amount;
	    }

}
