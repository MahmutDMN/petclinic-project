package com.javaegitimleri.petclinic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetClinicConfiguration {
	
	@Autowired
	private PetClinicProperties petClinicProperties; 
	
	//bean atamaları yapıldıktan bean olusturulduktan sonra cagirir(PostConstruct)
	@PostConstruct
	public void init() {
		System.out.println("Display owners with pets : " +petClinicProperties.isDisplayOwnersWithPets());
	}

}
