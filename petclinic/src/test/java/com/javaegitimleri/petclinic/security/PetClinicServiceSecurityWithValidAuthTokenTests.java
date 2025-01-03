package com.javaegitimleri.petclinic.security;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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

import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

/**
 * 
 * @author MahmutDuman
 *	Burada authentication kısmını tokenle erisebilip hata almadan islemi dogru yaptıgını test ettik.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=dev")
public class PetClinicServiceSecurityWithValidAuthTokenTests {
	
	@Autowired
	private PetClinicService petClinicService;
	
	@Before
	public void setUp() {
		TestingAuthenticationToken auth= new TestingAuthenticationToken("user1", "user1", "ROLE_USER");
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}
	
	@After
	public void tearDown() {//burada method test edildikten sonra securityholder kısmını kaldırır.
		SecurityContextHolder.clearContext();	
	}

	@Test
//	@Test(expected = AuthenticationCredentialsNotFoundException.class) 
	//burayı yazarsak bu hatayı basarılı kabul edecegi icin testi gecer
//	@Test(expected = AccessDeniedException.class)
	public void testFindOwners() {
		List<Owner> owners = petClinicService.findOwners();
		MatcherAssert.assertThat(owners.size(), Matchers.equalTo(10));
		
	}
}
