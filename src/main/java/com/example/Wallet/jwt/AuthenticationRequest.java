package com.example.Wallet.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //. This annotation generates a no-args constructor for the class
@AllArgsConstructor //This annotation generates a constructor that includes all the class's fields as parameters.
public class AuthenticationRequest {
	private String username;
	private String password;

}
