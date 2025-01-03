package com.javaegitimleri.petclinic.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.javaegitimleri.petclinic.model.Owner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional //burada database de ilgili degişikligin yapılmaması gerekli oldugu icin ekledik.
//OwnerRepositoryTests classi icin @Transactional yaptigim icin testCreateOwner3 metodu icin Owner tablosuna kayit ekler mi ?
//@Transactional anotasyonunun etkisi nedeniyle testCreateOwner3 metodunda eklediğiniz veriler veritabanına kalıcı olarak kaydedilmez. 
//Bunun nedeni, @Transactional anotasyonunun, her bir test metodunun başında bir işlem başlatıp, metodun sonunda işlemi rollback etmesidir.	
public class OwnerRepositoryTest {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
//	@Autowired //autowired ile de enjecte edilebilir veya @PersistenceContext ile de enjecte edilebilir.
	@PersistenceContext
	private EntityManager entityManager;
	
	
//	@Test
	@Test(expected = PersistenceException.class)
	//expected = PersistenceException.class yazarak bu hatayı alırsa basarılı olarak ele almamızı sagladı.
	//eger bu hatayı almassa test hatalı olacaktı.
	public void testCreateOwner() {
		
		Owner owner = new Owner();
		owner.setFirstName(null);
		owner.setLastName(null);
		
		ownerRepository.createOwner(owner);
		
		//burada flush yazarsak database e yazdırır bunu yazmassak False positive durumundan dolayı hata alıp rollback yapacagı 
		//için basarılı alacaktı buda bizi yanlıs yönlendirecekti
		//inserti db ye yazdırmasını sagladi.
		// /* insert com.javaegitimleri.petclinic.model.Owner */ insert into t_owner (first_name, last_name, id) values (?, ?, ?) [23502-200]

		entityManager.flush();
		
		
		
	}



}
