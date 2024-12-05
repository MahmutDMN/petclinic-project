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
	 * @RequestMapping Spring Boot eslesmeyen web isteklerini statik resource olarak
	 *                 cozumler.
	 * 
	 *                 Bütün eslesmeyen resourceslar static resource (/**) static
	 *                 resource olarak ele alınır.(Html, Css, Js dosyaları static
	 *                 resourceslara ornektir.)
	 * 
	 * @src/main/resources/application.properties icerisinde
	 *                                            spring.mvc.static-path-pattern=/static-content/**
	 *                                            olarakta sadece bu path altında
	 *                                            eslesenler konfigure edilir.
	 * 
	 * @SpringFramework bunu static web resourcesleri ilk önce META-INF/resources/
	 *                  dizini altında arar.Yoksa sırasıyla resources/ ,static/
	 *                  public/dizini altında arar.En son src/main/webapps altına
	 *                  bakilir
	 * 
	 * 
	 *                  http://localhost:8080/pcs
	 */
	public String welcome() {
		return "Welcome to PetClinic Word";

	}

}
