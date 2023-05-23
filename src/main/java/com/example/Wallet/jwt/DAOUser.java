package com.example.Wallet.jwt;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user2")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String role;


}