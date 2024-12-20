package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Order(value=0) : Normal SecurityConfiguration dan once devreye girsin diye order yazdik.
//Hata veriyor cunku WebSecurityConfigurerAdapter ettigi icin hangisini kullanacagini bilmiyor.
//Cunku norm SecurityConfiguration extends WebSecurityConfigurerAdapter ettigi icin tum resource lar icin tanımlanımlamıştık.
//Fakat biz bu configure metodu icinde /h2-console/ bu path ile baslayan tm webmetotlari(url-istekleri) icin devreye girsin.
@Configuration
@Order(value = 0)
public class H2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/h2-console/**").authorizeRequests().anyRequest().permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
