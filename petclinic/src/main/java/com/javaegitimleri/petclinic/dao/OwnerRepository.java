package com.javaegitimleri.petclinic.dao;

import java.util.List;

import com.javaegitimleri.petclinic.model.Owner;

public interface OwnerRepository {
	
	public List<Owner> findAll();
	public Owner findById(Long id);
	public List<Owner> findByLastName(String lastName);
	public void createOwner(Owner owner);
	public Owner updateOwner(Owner owner);
	public void deleteOwner(Long id);

}
