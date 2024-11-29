package com.javaegitimleri.petclinic.dao;

import java.util.List;

import com.javaegitimleri.petclinic.model.Pet;

public interface PetRepository {
	
	public Pet findById(Long id);
	public List<Pet> findByOwnerId(String ownerId);
	public void create(Pet pet);
	public Pet update(Pet pet);
	public void delete(Long id);
	public void deleteByOwnerId(Long ownerId);

}
