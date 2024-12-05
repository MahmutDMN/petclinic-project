package com.javaegitimleri.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Bu anatasyon sayesinde Spring container bir kontroler beanı yaratacak gelen
 * web isteklerini ilgili methodlarla eşleştirmeye calisacak
 */

@Controller
public class PetClinicController {

	@RequestMapping("/pcs")
	@ResponseBody
	/**
	 * @Controller, Spring Framework'de kullanılan bir anotasyondur ve temel olarak
	 * bir sınıfı web katmanında bir kontrolcü olarak tanımlamak için kullanılır.
	 * MVC (Model-View-Controller) tasarım deseninde,
	 * 
	 * @Controller bir Controller bileşenini temsil eder.
	 * @ResponseBody Eger bu anatosyon olmassa web requestlerini handler edecek
	 *               ilgili handler methodlarını dispach eden springin dispacher
	 *               servlet yapısı controller methodunun döndügü String donusu
	 *               bunun bir view olarak render etmeye calısır bunun onune geçmek
	 *               icin responsun bodysi oldugunu göstermek gerekir
	 * 
	 * 
	 * 
	 *                  http://localhost:8080/pcs
	 */
	public String welcome() {
		return "Welcome to PetClinic Word";

	}

}
