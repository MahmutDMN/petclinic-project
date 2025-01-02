package com.javaegitimleri.petclinic.web;


import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.javaegitimleri.petclinic.model.Owner;

public class PetClinicRestControllerTests {

	private RestTemplate restTemplate;
	
	private RestTemplateBuilder restTemplateBuilder;

	/*
	 * JUnit'teki @Before anotasyonu, test metodlarından önce çalıştırılması gereken
	 * kodu belirtmek için kullanılır. Yani, her test metodundan önce belirli bir
	 * hazırlık işlemi yapılması gerektiğinde @Before anotasyonu kullanılır.
	 */

	//BasicAuthenticationInterceptor ile yapılabilir 1.yol
//	@Before
//	public void setUp() {
//		restTemplate = new RestTemplate();
//		BasicAuthenticationInterceptor interceptor=new BasicAuthenticationInterceptor("admin", "mahmut123");
//		restTemplate.setInterceptors(Arrays.asList(interceptor));
//	}
	
	
	//RestTemplateBuilder ile de yapılabilir.2.yol
	@Before
	public void setUp() {
		restTemplateBuilder = new RestTemplateBuilder();

	    // RestTemplate RestTemplateBuilder ile yapılandırılıyor
	    this.restTemplate = restTemplateBuilder
	            .basicAuthentication("admin", "mahmut123") // Basic Auth bilgisi
	            .build();
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
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Ziya"));

	}
	
	@Test
	public void testGetOwnerByIdTest2() {
		//BasicAuth örnegi deneyecegimiz icin cokladık.TCP-Ip monitor portundan gecircegimiz icin 8085 olacak sekilde guncelledik
		ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8085/rest/owner/1", Owner.class);

		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("Ziya"));

	}
	
	@Test
	public void testGetOwnersByLastName() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Duman", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String, String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		//basarili durum
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Mahmut", "Alperen", "Hakan"));
		
		//hatali durum
//		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Mahmut", "Alperen1", "Hakan"));
		
	}
	
	@Test
	public void testGetOwners() {
		ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
		
		MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
		
		List<Map<String, String>> body = response.getBody();
		
		List<String> firstNames = body.stream().map(e->e.get("firstName")).collect(Collectors.toList());
		
		//hatalı durum
//		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Mahmut", "Alper Sahin", "Alperen", "Hakan", "Ahmet"));

		//basarili durum
		MatcherAssert.assertThat(firstNames, Matchers.containsInAnyOrder("Mahmut", "Alperen", "Hakan", "Alper Sahin"));
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
	
	@Test
	public void testCreateOwner() {
		
		Owner owner=new Owner();
		owner.setFirstName("Mustafa");
		owner.setLastName("Kahraman");
		
		//bu method Uri da ekleme yapılan owner'ın id sini path'e setleyerek döner
		
		URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);

		Owner owner2 = restTemplate.getForObject(location, Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));	
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
		
		
	}
	
	@Test
	public void testUpdateOwner() {
		Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
		
		MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("Hakan"));

		owner.setFirstName("Mustafa");
		owner.setLastName("Tuna");
		
		restTemplate.put("http://localhost:8080/rest/owner/4", owner);
		
		Owner owner2 = restTemplate.getForObject("http://localhost:8080/rest/owner/4", Owner.class);
		
		MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo("Mustafa"));
		MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo("Tuna"));

	}
	
	@Test
	public void testDeleteOwner() {
		//aşagıdaki 1 yazan yeri olmayan bir user(40) girseydik 404 verir
//		restTemplate.delete("http://localhost:8080/rest/owner/1");//40
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8080/rest/owner/1", HttpMethod.DELETE,null,Void.class);

		try {
			restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);
			Assert.fail("should have not returned owner");
		// } catch (RestClientException e) { Önceki Hali(Hata degilde ResponseEntity dönüsü yapılırken) bu şekilde ele alınıyordu
		} catch (HttpClientErrorException ex) { // catch (RestClientException e) { Önceki Hali(Hata degilde ResponseEntity dönüsü yapılırken) bu şekilde ele alınıyordu
			MatcherAssert.assertThat(ex.getStatusCode().value(), Matchers.equalTo(404));
			
		}
	}

}
