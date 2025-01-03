package com.javaegitimleri.petclinic;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

//Burada WebSecurityConfigurerAdapter den degilde 
//AbstractSecurityConfiguration (Bura zaten WebSecurityConfigurerAdapter içeriyor)(abstractan extend etmemizi) sagladık 
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends AbstractSecurityConfiguration {

	public SecurityConfiguration() {

	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	//AbstractSecurityConfiguration kısmına tasidik
//	@Autowired
//	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// burayı override edip icerisini bos bırakırsak basicAuth islemi kaldırılır.
		// bu configure methodunda  http.authorizeRequests().anyRequest().authenticated(); yazarsak form
		// login yazmadıgımız için istekte 403 hatası alırız.
		//login sayfasına yönlenebilmek için http.formLogin(); yazmamız gerekiyor.
		
		http.authorizeRequests().antMatchers("/**/favicon.ico", "/css/**", "js/**", "/images/**", "/webjars/**","/login.html").permitAll();
		//RestSecurityConfiguration'a tasindi.
//		http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')");
		http.authorizeRequests().antMatchers("/actuator/**").access("hasRole('ADMIN')");
		http.authorizeRequests().anyRequest().authenticated();
		http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").failureUrl("/login.html?loginFailed=true");
		http.rememberMe().userDetailsService(userDetailsService);
		
		//Abstract classa tasıdık
//		http.httpBasic();

	}
	
	//Jdbc real configurasyonu bu kadar.
	//AbstractSecurityConfiguration kısmına tasidik
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource);
//	}


}
