package com.javaegitimleri.petclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public SecurityConfiguration() {

	}
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// burayı override edip icerisini bos bırakırsak basicAuth islemi kaldırılır.
		// bu configure methodunda  http.authorizeRequests().anyRequest().authenticated(); yazarsak form
		// login yazmadıgımız için istekte 403 hatası alırız.
		http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/login.html").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		//login sayfasına yönlenebilmek için http.formLogin(); yazmamız gerekiyor.
		http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").failureUrl("/login.html?loginFailed=true");
		http.rememberMe().userDetailsService(userDetailsService);
		http.httpBasic();

	}

}
