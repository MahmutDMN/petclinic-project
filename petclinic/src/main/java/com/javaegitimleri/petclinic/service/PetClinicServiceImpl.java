package com.javaegitimleri.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;

@Service
@Transactional
public class PetClinicServiceImpl implements PetClinicService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PetRepository petRepository;

	@Override
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner=ownerRepository.findById(id);
		if(owner == null) throw new OwnerNotFoundException("Owner not found with id : "+ id);
		return owner;
	}

	@Override
	public void createOwner(Owner owner) {
		ownerRepository.createOwner(owner);

	}

	@Override
	public void updateOwner(Owner owner) {
		ownerRepository.updateOwner(owner);

	}

	@Override
	public void deleteOwner(Long id) {
		petRepository.deleteByOwnerId(id);
		ownerRepository.deleteOwner(id);
		// Burada  classın en basına transactional koydugumuz için
		// public methodlarda hata durumunda geriye alma(rollback) islemi yapar.
		// basarili olursa zaten commitlenir.

//		if(true) {
//			throw new RuntimeException("testing rollback");
//		}

		//basarılı durum (hata satırı kapalıysa )// asagıda 404 yazmasının sebebi test case inde sildigi aboneyi sorguladıgında 404 almasıdır yani islem basarılıdır 
//		2024-12-19 04:20:03.880 DEBUG 20268 --- [nio-8080-exec-2] o.s.j.d.DataSourceTransactionManager     : Initiating transaction rollback
//		2024-12-19 04:20:03.880 DEBUG 20268 --- [nio-8080-exec-2] o.s.j.d.DataSourceTransactionManager     : Rolling back JDBC transaction on Connection [HikariProxyConnection@453022867 wrapping conn0: url=jdbc:h2:mem:testdb user=SA]
//		2024-12-19 04:20:03.880 DEBUG 20268 --- [nio-8080-exec-2] o.s.j.d.DataSourceTransactionManager     : Releasing JDBC Connection [HikariProxyConnection@453022867 wrapping conn0: url=jdbc:h2:mem:testdb user=SA] after transaction
//		2024-12-19 04:20:03.881 DEBUG 20268 --- [nio-8080-exec-2] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'application/json', given [application/xml, text/xml, application/json, application/*+xml, application/*+json] and supported [application/json]
//		2024-12-19 04:20:03.881 DEBUG 20268 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Completed 404 NOT_FOUND
		
		//hatalı durum (hata satırı acıksa -> logdaki InternalServerException: java.lang.RuntimeException: testing rollback geldigi gözleniyor. )(Burada internal error hatası olusturduk pet ve owner da yapılan islemler geri alınır.)
//		2024-12-19 04:27:22.652 DEBUG 16784 --- [nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager     : Initiating transaction rollback
//		2024-12-19 04:27:22.653 DEBUG 16784 --- [nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager     : Rolling back JDBC transaction on Connection [HikariProxyConnection@1487521663 wrapping conn11: url=jdbc:h2:mem:testdb user=SA]
//		2024-12-19 04:27:22.653 DEBUG 16784 --- [nio-8080-exec-1] o.s.j.d.DataSourceTransactionManager     : Releasing JDBC Connection [HikariProxyConnection@1487521663 wrapping conn11: url=jdbc:h2:mem:testdb user=SA] after transaction
//		2024-12-19 04:27:22.658  WARN 16784 --- [nio-8080-exec-1] .w.s.m.a.ResponseStatusExceptionResolver : Resolved [com.javaegitimleri.petclinic.exception.InternalServerException: java.lang.RuntimeException: testing rollback]
//		2024-12-19 04:27:22.658 DEBUG 16784 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 500 INTERNAL_SERVER_ERROR

		

	}

}
