package com.javaegitimleri.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.boot.autoconfigure.domain.EntityScan;

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

@Entity
@Table(name = "t_owner")
@XmlRootElement
public class Owner {

	@Id
//	@Column(name = "id") Burada property name'i ile degişken adı aynı oldugu için eşleştirmeye gerek yok 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "petClinicSeqGen")
	@SequenceGenerator(name = "petClinicSeqGen", sequenceName = "petclinic_sequence", allocationSize = 1)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@OneToMany(mappedBy = "owner")
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
