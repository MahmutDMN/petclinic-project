package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Order(value=0) : Normal H2 den önce girsin diye value -1 yapildi.
@Configuration
@Order(value = -1)
public class HalBrowserSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/hal/**").authorizeRequests().anyRequest().permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

}
