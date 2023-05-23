package com.example.Wallet.jwt;

import lombok.Data;

@Data //@Getter, @Setter, @ToString, and @EqualsAndHashCode are covered by this
public class UserDTO {
	private String username;
	private String password;
	private String role;
}