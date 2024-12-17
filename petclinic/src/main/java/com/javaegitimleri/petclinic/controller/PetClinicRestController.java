package com.javaegitimleri.petclinic.controller;

import java.net.URI;
import java.util.List;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaegitimleri.petclinic.exception.InternalServerException;
import com.javaegitimleri.petclinic.exception.OwnerNotFoundException;
import com.javaegitimleri.petclinic.model.Owner;
import com.javaegitimleri.petclinic.service.PetClinicService;

@RestController
@RequestMapping("/rest")
public class PetClinicRestController {

	@Autowired
	private PetClinicService petClinicService;

	/**
	 * Burada ResponseEntity ile donersek hata kodları headers gibi kısımları
	 * degiştirip dönus yapabiliriz. Response donuslerindeki yapı tamamen
	 * aynıdır.(getOwner,getOwners2)
	 * 
	 * @return ResponseEntity<List<Owner>>
	 * 
	 *         Postman petclinic-project -> 1-/rest/owners
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/owners")
	public ResponseEntity<List<Owner>> getOwners() {
		List<Owner> owners = petClinicService.findOwners();
		return ResponseEntity.ok(owners);
	}

	/**
	 * Burada direk objeyi dönersek basarılı durumda HTTP.ok()(200) döner.Hata
	 * kodlarını ve response header gibi seyleri yonetemeyiz. Response
	 * donuslerindeki yapı tamamen aynıdır.(getOwners,getOwners2)
	 * 
	 * @return List<Owner>
	 * 
	 *         Postman petclinic-project -> 2-/rest/owners2
	 *         http://localhost:8080/rest/owners
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/owners2")
	public List<Owner> getOwners2() {
		List<Owner> owners = petClinicService.findOwners();
		return owners;
	}

	/**
	 * 
	 * 
	 * Postman petclinic-project -> 3-/rest/owners pathParameter
	 * http://localhost:8080/rest/owner?ln=Duman
	 * 
	 * @param lastName
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/owner")
	public ResponseEntity<List<Owner>> getOwners(@RequestParam("ln") String lastName) {
		List<Owner> owners = petClinicService.findOwners(lastName);
		return ResponseEntity.ok(owners);

	}

	/**
	 * 
	 * Postman petclinic-project -> 4-/rest/owner/{id} pathVariable
	 * http://localhost:8080/rest/owner/1 Eger try catch yazmayıp 5 id li olmayan
	 * bir kayıt icin hata alırsa 500 atıyor. Burada hatayı tipine göre yönetmek
	 * icin try catch ile yakalıyoruz.
	 * 
	 * @RequestMapping(method = RequestMethod.GET, value = "/owner/{id}")
	 * @param id
	 * @return
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/owner/{id}")
	public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
		try {
			Owner owner = petClinicService.findOwner(id);
			return ResponseEntity.ok(owner);

		} catch (OwnerNotFoundException ex) {
			// 404 kayıt olmadıgı hatalarda donus yapılır.
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// 500 Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * 
	 * HATEOAS (Hypermedia As The Engine Of Application State), RESTful API'ler için
	 * bir tasarım prensibidir. Bu yaklaşım, istemcilere bir kaynağın durumunu ve o
	 * kaynakla yapılabilecek işlemleri hypermedia (bağlantılar) aracılığıyla
	 * sağlar. Yani, bir API yanıtında, sadece veriyi değil, aynı zamanda o veriye
	 * ilişkin hangi işlemlerin yapılabileceğini belirten bağlantılar da yer alır.
	 * 
	 * İstemciler, sunucudan dönen bağlantılar (links) sayesinde API'nin işleyişini
	 * ve sonraki adımları öğrenir. İstemcilerin API'nin iç yapısını bilmesine gerek
	 * kalmaz, çünkü hangi işlemleri yapabileceğini hypermedia bağlantıları
	 * belirtir.
	 * 
	 * Spring Boot'ta, HATEOAS desteği, spring-boot-starter-hateoas kütüphanesi
	 * kullanılarak (pom.xml) kolayca sağlanır. Spring HATEOAS, hypermedia ile
	 * çalışan REST API'ler geliştirmek için yardımcı araçlar sunar.
	 * 
	 * 
	 */

	// produces:HTTP isteği için sunucunun hangi formatta yanıt vereceğini belirtir.
	// @RequestMapping(method = RequestMethod.GET, value = "/owners", produces =
	// {"application/json", "application/xml"})
	@RequestMapping(method = RequestMethod.GET, value = "/owner/{id}", produces = "application/json")
	public ResponseEntity<?> getOwnersAsHateoasResource(@PathVariable Long id) {
		try {
			Owner owner = petClinicService.findOwner(id);
			Link self = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner/" + id).withSelfRel();
			Link create = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner").withRel("create");
			Link update = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner/" + id)
					.withRel("update");
			Link delete = ControllerLinkBuilder.linkTo(PetClinicController.class).slash("/owner/" + id)
					.withRel("delete");

			EntityModel<Owner> resource = new EntityModel<>(owner);
			resource.add(self);
			resource.add(create);
			resource.add(update);
			resource.add(delete);
			return ResponseEntity.ok(resource);

		} catch (OwnerNotFoundException ex) {
			// 404 kayıt olmadıgı hatalarda donus yapılır.
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			// 500 Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		/**
		 * 
		 * Request:http://localhost:8080/rest/getOwnerAsHateoasResource/1 Response: {
		 * "id": 1, "firstName": "Mahmut", "lastName": "Duman", "_links": { "self": {
		 * "href": "http://localhost:8080/rest/getOwnerAsHateoasResource/1" },
		 * "createOwner": { "href": "http://localhost:8080/rest/createOwner" },
		 * "deleteOwner": { "href": "http://localhost:8080/rest/deleteOwner/1" },
		 * "updateOwner": { "href": "http://localhost:8080/rest/updateOwner/1" } } }
		 */

	}

	@RequestMapping(method = RequestMethod.POST, value = "/owner")
	public ResponseEntity<URI> createOwner(@RequestBody Owner owner) {
		try {
			System.out.println("before OwnerID : " + owner.getId());
			petClinicService.createOwner(owner);
			System.out.println("after OwnerID : " + owner.getId());
			Long id = owner.getId();
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/owner/{id}")
	public ResponseEntity<?> updateOwner(@PathVariable("id") Long id, @RequestBody Owner ownerRequest) {
		try {
			Owner owner = petClinicService.findOwner(id);
			owner.setFirstName(ownerRequest.getFirstName());
			owner.setLastName(ownerRequest.getLastName());

			petClinicService.updateOwner(owner);

			return ResponseEntity.ok().build();

		} catch (OwnerNotFoundException ex) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/owner/{id}")
	public ResponseEntity<?> deleteOwner(@PathVariable("id") Long id) {
		try {
			petClinicService.deleteOwner(id);
			return ResponseEntity.ok().build();
		} catch (OwnerNotFoundException ex) {
			// Response yapısını biraz farklılastırdık
//			return ResponseEntity.notFound().build();
			// throw olarak donduk

			throw ex;
		} catch (Exception e) {
			// Response yapısını biraz farklılastırdık
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			// throw olarak donduk

			throw new InternalServerException(e);
		}
	}

}
