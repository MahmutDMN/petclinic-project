package com.javaegitimleri.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @XmlRootElement: Sınıfın XML'de bir kök eleman olarak kullanılmasını sağlar.
 *                  <owner> <id>1</id> <firstName>John</firstName>
 *                  <lastName>Doe</lastName> </owner>
 * 
 * @XmlTransient: Belirli bir alanın XML çıktısında görünmesini engeller.
 * @JsonIgnore: Belirli bir alanın JSON çıktısında görünmesini engeller.
 * 
 *              Eğer Accept: application/json başlığı varsa → JSON döner. Eğer
 *              Accept: application/xml başlığı varsa → XML döner. Hiçbir şey
 *              belirtilmezse → Varsayılan (genelde JSON) döner.
 * 
 *
 */

@XmlRootElement
public class Owner {

	private Long id;
	private String firstName;
	private String lastName;

	private Set<Pet> pets = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Owner kayıtlarının xml e donusturulmesi icin Jaxb - XMLRootElement
	 * Anotasyonunun aktif olması gerekir //Bi directional ilişkiye girilmemesi icin
	 * serialization sırasında sonsuz döngüye girilmemesi icin
	 * 
	 * @XmlTransient anatasyonu xml icin koyulur
	 * @JsonIgnore Json da hariç bırakılma için json ignore eklenir.
	 * 
	 * @return
	 */
	@XmlTransient // Bi directional ilişkiye girilmemesi icin serialization sırasında sonsuz
					// döngüye girilmemesi icin
	@JsonIgnore
	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
