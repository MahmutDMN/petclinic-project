package com.javaegitimleri.petclinic.dao.mem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

//spring container çalısma zamaninda bir bean yaratilir
@Repository
public class OwnerRepositoryInMemoryImpl implements OwnerRepository {

	private Map<Long, Owner> ownersMap = new HashMap<>();

	
	
	

	public OwnerRepositoryInMemoryImpl() {
		
		Owner owner1 = new Owner();
		owner1.setId(1L);
		owner1.setFirstName("Mahmut");
		owner1.setLastName("Duman");
		
		Owner owner2 = new Owner();
		owner2.setId(2L);
		owner2.setFirstName("Alper Sahin");
		owner2.setLastName("Oner");
		
		Owner owner3 = new Owner();
		owner3.setId(3L);
		owner3.setFirstName("Alperen");
		owner3.setLastName("Duman");
		
		Owner owner4 = new Owner();
		owner4.setId(4L);
		owner4.setFirstName("Hakan");
		owner4.setLastName("Duman");
		
		ownersMap.put(owner1.getId(), owner1);
		ownersMap.put(owner2.getId(), owner2);
		ownersMap.put(owner3.getId(), owner3);
		ownersMap.put(owner4.getId(), owner4);
		


	}

	@Override
	public List<Owner> findAll() {
		return new ArrayList<>(ownersMap.values());
	}

	@Override
	public Owner findById(Long id) {
		return ownersMap.get(id);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {

//		return ownersMap.values().stream().filter(o->o.getLastName().equals(lastName)).collect(Collectors.toList());
		
		Stream<Owner> ownerStream = ownersMap.values().stream();
		Stream<Owner> filteredStream = ownerStream.filter(o->o.getLastName().equals(lastName));
		
		return filteredStream.collect(Collectors.toList());

	}

	@Override
	public void createOwner(Owner owner) {
		
		owner.setId(new Date().getTime());
		ownersMap.put(owner.getId(), owner);
	}

	@Override
	public Owner updateOwner(Owner owner) {
		ownersMap.replace(owner.getId(), owner);
		return ownersMap.get(owner.getId());
		
	}

	@Override
	public void deleteOwner(Long id) {
		ownersMap.remove(id);
	}

}
