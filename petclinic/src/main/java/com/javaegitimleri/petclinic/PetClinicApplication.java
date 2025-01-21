package com.javaegitimleri.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "petClinicAuditorAware")
@ServletComponentScan// Servlertleri scan etmesi icin kullanılır.Spring classpatte Servlet ve Filterların olan sınıfları tespit eder.
@SpringBootApplication
@EnableConfigurationProperties(value = PetClinicProperties.class)
public class PetClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

}
