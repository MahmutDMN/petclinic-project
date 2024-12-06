package com.javaegitimleri.petclinic.web;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

public class PetClinicRestControllerTests {

	private RestTemplate restTemplate;

	/*
	 * JUnit'teki @Before anotasyonu, test metodlarından önce çalıştırılması gereken
	 * kodu belirtmek için kullanılır. Yani, her test metodundan önce belirli bir
	 * hazırlık işlemi yapılması gerektiğinde @Before anotasyonu kullanılır.
	 */

	@Before
	public void setUp() {
		restTemplate = new RestTemplate();
	}

	/**
	 * İlk önce springboot uygulamasını Run -> Spring Boot Application ile ayaga kaldırırız.
	 * Sonrasında Junit testi calistirmamız gerekmektedir.
	 * Bu classa sag tık run -> junit ile calıstırıp testlerini yapılmaktadır. 
	 * Altta yazdıgımız testlere göre kontroller yapılmaktadir.
	 * pom.xml e junit dependency'sini test yazmak icin ekledik 
	 * Diger hamcrest kütüphanesinide test kontrollerini yapmak icin ekledik.
	 * 
	 * JUnit 4: @Before anotasyonu ile her test metodundan önce belirli bir hazırlık yapılır.
	 * JUnit 5: JUnit 5'te bu fonksiyonaliteyi sağlayan anotasyon @BeforeEach olarak değiştirilmiştir.
	 */


	@Test
	public void testGetOwnerById() {
		// Basarili durum 1. Kayıt zaten Mahmut gelmekte unitTest basarılı.
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);

		// Hatalı Durum 2. Kayıt zaten Alper geldigi icin unitTestte hata
		// vermektedir.Expected:"Mahmut" but was "Alper" olarak farklı oldugu
		// görülmektedir.
		// Failure nedenini Fauliure Trace(Acılan Pencere) den anlayabiliyoruz.

//		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/2", Owner.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Mahmut"));

	}
	
	@Test
	public void testGetOwnersByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners?ln=Duman", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String, String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		//basarili durum
		MatcherAssert.assertThat(firstNames, Matchers.contains("Mahmut", "Alper Sahin", "Alperen", "Hakan"));
		
		//hatali durum
//		MatcherAssert.assertThat(firstNames, Matchers.contains("Mahmut", "Alper Sahin", "Alperen1", "Hakan"));
		
	}
	
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String, String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		//basarili durum
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Mahmut"));
		/*
		 * MatcherAssert.assertThat(): Bu, bir doğrulama (assertion) yapar. firstNames koleksiyonunun, belirtilen şartları sağladığından emin olmak için kullanılır.
		 * Matchers.containsInAnyOrder("Alper"):
		 * Bu, firstNames koleksiyonunun öğelerinin herhangi bir sırayla "Alper"
		 * öğesini içerip içermediğini kontrol eder. Yani, koleksiyonun sırası önemli değildir;
		 * yalnızca belirli öğe veya öğeler olup olmadığına bakılır.
		 *
		 * */
		
		
	}
	
	/**
	 * JUnit 4 ve 5 arasında yeni sürümde mavenda artifactId si degismistir.
	 * biz ilk olanı kullandıgımız icin maven bunun son surumu olan 4.12 yi indirmistir.
	 * 
	 * 	 * JUnit 5'i kullanmak için junit-jupiter-api ve junit-jupiter-engine bağımlılıklarını açıkça belirtmeniz gerekmektedir.
	 * <dependency>
	 * 	    <groupId>org.junit.jupiter</groupId>
	 * 		<artifactId>junit-jupiter-api</artifactId>
	 * 		<version>5.7.2</version> <!-- Veya en son sürüm -->
	 * 		<scope>test</scope>
	 * </dependency>
	 * 
	 * <dependency>
	 * 
	 * <groupId>org.junit.jupiter</groupId>
	 * 		<artifactId>junit-jupiter-engine</artifactId>
	 * 		<version>5.7.2</version> <!-- Veya en son sürüm -->
	 * 		<scope>test</scope>
	 * </dependency>
	 * 
	 */

}
