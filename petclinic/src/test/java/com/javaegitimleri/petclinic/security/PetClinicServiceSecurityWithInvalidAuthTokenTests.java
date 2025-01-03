package com.javaegitimleri.petclinic.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.service.PetClinicService;

/**
 * 
 * @author MahmutDuman
 *	Burada authentication kısmını tokenle erisebilip ilgili tokenin yetkisi olmadıgı için erisemeyecegini test ettik.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=dev")
public class PetClinicServiceSecurityWithInvalidAuthTokenTests {
	
	@Autowired
	private PetClinicService petClinicService;
	
	@Before
	public void setUp() {
		TestingAuthenticationToken auth= new TestingAuthenticationToken("user1", "user1", "ROLE_XXX");
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}
	
	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
		
	}

//	@Test
//	@Test(expected = AuthenticationCredentialsNotFoundException.class) 
	//burayı yazarsak bu hatayı basarılı kabul edecegi icin testi gecer
	@Test(expected = AccessDeniedException.class)
	public void testFindOwners() {
		petClinicService.findOwners();
		
	}
}
