package com.example.Wallet.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long uid;
	
	@NotNull
	@Size(min = 2 , message="First Name should have atelast 2 characters")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull
	@Column(name="mobile")
	private Long mobile;
	
	@NotNull
	@Size(min = 2 , message="Last Name should have atelast 2 characters")
	@Column(name="last_name")
	private String lastName;
	
	@NotBlank
	@Email
	@Column(name="email")
	private String email;
	
	@Column(name="address1")
	private String address1;
	
	@Column(name="address2")
	private String address2;

	//Even though the default constructor is not explicitly required in this code, it can be useful in certain scenarios. some frameworks or libraries may rely on the presence of a default constructor for certain operations.
	public User() {}
	public User(String firstName, String lastName, String email, String address1, String address2,long mobile) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.mobile=mobile;
	}

}