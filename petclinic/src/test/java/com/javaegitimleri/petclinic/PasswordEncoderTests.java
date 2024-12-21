package com.javaegitimleri.petclinic;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTests {

	public PasswordEncoderTests() {
	}

	@Test
	public void generateEncodedPasswords() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println("{bcrypt}" + passwordEncoder.encode("secret"));
		System.out.println("{bcrypt}" + passwordEncoder.encode("secret"));
		System.out.println("{bcrypt}" + passwordEncoder.encode("secret"));
		
		System.out.println("******************************************************************");

		System.out.println("{bcrypt}" + passwordEncoder.encode("user1"));
		System.out.println("{bcrypt}" + passwordEncoder.encode("user2"));
		System.out.println("{bcrypt}" + passwordEncoder.encode("user3"));
	}

}
