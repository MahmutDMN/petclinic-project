package com.javaegitimleri.petclinic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(value=1)
@Configuration
public class RestSecurityConfiguration extends AbstractSecurityConfiguration {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//burada default ayarlar ezecegi icin burayı kapattık.
//		super.configure(http);
		//burada rest icin buraya dusmesini sagladık.
		http.antMatcher("/rest/**");
		http.authorizeRequests().antMatchers("/rest/**").access("hasRole('EDITOR')");
		http.csrf().disable();
		http.httpBasic();
	}

}
