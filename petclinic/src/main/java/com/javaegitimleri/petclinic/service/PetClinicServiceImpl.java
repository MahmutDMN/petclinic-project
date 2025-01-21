package com.javaegitimleri.petclinic.service;

import java.util.List;

import javax.transaction.Transactional.TxType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.dao.PetRepository;
import com.javaegitimleri.petclinic.dao.jpa.VetRepository;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.exception.VetNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.model.Vet;

@Validated
@Service
@Transactional(rollbackFor = Exception.class)
public class PetClinicServiceImpl implements PetClinicService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VetRepository vetRepository;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Secured(value = {"ROLE_USER", "ROLE_EDITOR"})
	public List<Owner> findOwners() {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Owner> findOwners(String lastName) {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS ,readOnly = true)
	public Owner findOwner(Long id) throws OwnerNotFoundException {
		Owner owner=ownerRepository.findById(id);
		if(owner == null) throw new OwnerNotFoundException("Owner not found with id : "+ id);
		return owner;
	}

	@Override
	@CacheEvict(cacheNames = "allOwners", allEntries = true) //burada allOwners ile cachelenen veriler allEntries = true oldugu için remove edilecek.Burada dönüş tipi el vermedigi için mevcut cache'i sıfırlıyoruz.
	public void createOwner(@Valid Owner owner) {
		ownerRepository.createOwner(owner);
		String from="k@s";
		String to="m@y";
		String subject="Owner Created!";
		String text="Owner entity with id : "+ owner.getId() + " created successfully";
		
		sendFakeMail(from,to,subject,text);
		
//		customMailSend();

	}
	
	private void sendFakeMail(String from, String to, String subject, String body) {
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);
		
		mailSender.send(msg);
		
	}
	
	private void customMailSend() {
		
//		String from="mahmut.duman@example.com";
		String from="mahmut.duman@gmail.com";
		String to="alper@gmail.com";
		String subject="Test Mail";
		String text="Selam Alperim mailim geldi mi?";
		
		emailService.sendEmail(from, to, subject, text);
		
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

	@Override
	public List<Vet> findVets() {
		return vetRepository.findAll();
	}

	@Override
	public Vet findVet(Long id) throws VetNotFoundException {
		return vetRepository.findById(id).orElseThrow(()-> {
			return new VetNotFoundException("Vet not found by id : " + id );	
		});
	}

}
