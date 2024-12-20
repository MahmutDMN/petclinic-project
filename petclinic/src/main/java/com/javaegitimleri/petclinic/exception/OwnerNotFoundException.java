package com.javaegitimleri.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @ResponseStatus, Spring Framework'de kullanılan bir anotasyondur
 * ve bir HTTP yanıt durum kodunu bir özel istisna sınıfına (exception class)
 * bağlamanıza olanak tanır. Böylece, belirli bir istisna atıldığında
 * otomatik olarak belirtilen HTTP durum kodu döndürülür.
 */

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class OwnerNotFoundException extends RuntimeException {

	public OwnerNotFoundException(String message) {
		super(message);
	}
	
	

}
