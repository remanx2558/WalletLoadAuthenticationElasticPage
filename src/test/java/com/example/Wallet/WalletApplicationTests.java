package com.example.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.Wallet.entity.Wallet;
import com.example.Wallet.repository.WalletRepository;
import com.example.Wallet.service.WalletService;

@RunWith(SpringRunner.class)
@SpringBootTest
class WalletApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private WalletService walletService;
	@MockBean
	private WalletRepository walletRepository;
	
	
	//1:Delete
	@Test
	public void DeleteUser() {
		Wallet use=new Wallet(123456,995);
		walletService.delete(use);
		verify(walletRepository,times(1)).delete(use);//this just verify how many times delete method of Repo is called
		
	}
	
	//2:Get
	@Test
	public void getWalletsTest() {
		when(walletRepository.findAll()).thenReturn(Stream
		.of(new Wallet(37612344, 31), new Wallet(95812345,35)).collect(Collectors.toList()));///when performed operation X then we are tured a Stram of data ad repository is mocked here.
       assertEquals(2, walletService.getWallets().size());/////(expected : Actual outPut)
		
	}
	
	
	//3:GET
	public void findbyPhoneTest() {
		int phone = 12345;
		when(walletRepository.findByPhone(phone))
				.thenReturn(Stream.of(new Wallet(3761234,31)).collect(Collectors.toList()));
		assertEquals(1, walletService.findbyPhone(phone).size());
		
	}
	
	//4:Post
	public void addWalletTest(){
		Wallet user = new Wallet(99377379,33);
		when(walletRepository.save(user)).thenReturn(user);
		assertEquals(user, walletService.addWallet(user));
		
	}


}
