package com.javaegitimleri.petclinic.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

@Repository("ownerRepository")
public class OwnerRepositoryJpaImpl implements OwnerRepository {

	public OwnerRepositoryJpaImpl() {
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Owner> findAll() {
		// entity manager ın Strin sql yerine hql yazıldı çagıracagı methodtta tip
		// referansı belirlemek icin dönmesi gereken modelin clasını
		// yazdık.getResultList() methodu ile liste donduk
		return entityManager.createQuery("from Owner", Owner.class).getResultList();
	}

	@Override
	public Owner findById(Long id) {
		return entityManager.find(Owner.class, id);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		return entityManager.createQuery("from Owner where lastName = :lastName", Owner.class)
				.setParameter("lastName", lastName).getResultList();

	}

	@Override
	public void createOwner(Owner owner) {
		entityManager.persist(owner);
	}

	@Override
	public Owner updateOwner(Owner owner) {
		return entityManager.merge(owner);
	}

	@Override
	public void deleteOwner(Long id) {
		entityManager.remove(entityManager.getReference(Owner.class, id));
	}

}
