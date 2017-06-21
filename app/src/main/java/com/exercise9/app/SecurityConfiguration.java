package com.exercise9.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.inMemoryAuthentication()
			.withUser("admin")
			.password("adminuser123")
			.roles("ADMIN");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin").access("hasRole('ADMIN')")
			.antMatchers("/employee/add").access("hasRole('ADMIN')")
			.antMatchers("/employee/update").access("hasRole('ADMIN')")
			.antMatchers("/roles/add").access("hasRole('ADMIN')")
			.antMatchers("/roles/update").access("hasRole('ADMIN')")
			.and()
			.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/login")
			.failureUrl("/loginfailed")
			.defaultSuccessUrl("/employee")
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/");
		http.csrf().disable();
	}
	
}