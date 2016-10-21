package com.crm.keymanager.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.crm.keymanager.security.EDAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private EDAuthenticationProvider edAuthenticationProvider;

	// Form, Web Pages Security with Authentication and ROLE based access
	@Configuration
	@Order(2)
	public static class FormSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					// admin pages, will redirect to /login if not authenticated
					// as
					// ROLE_ADMIN
					.authorizeRequests().antMatchers("/").permitAll().antMatchers("/admin**").hasRole("ADMIN").and()
					// login page
					.formLogin().loginPage("/login").defaultSuccessUrl("/admin").failureUrl("/login?error")
					.usernameParameter("username").passwordParameter("password").permitAll()
					// logout
					.and().logout().logoutSuccessUrl("/login?logout").permitAll();
		}

		// Web Pages and CSS, JS, Images inside /WEB-INF folder
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**",
					// "/api/**",
					"/views/**");
		}
	}

	// REST End point security
	@Configuration
	@Order(1)
	public static class RestSecurityConfig extends WebSecurityConfigurerAdapter {

		// Basic Authentication, and ROLE_ADMIN
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					// disable csrf
					.csrf().disable()
					// should be ROLE_ADMIN
					.antMatcher("/api/**").authorizeRequests()
					// for any request of /api/** pattern
					.anyRequest().hasAnyRole("ADMIN")
					// http Basic Auth
					.and().httpBasic();
		}
	}

	@Bean
	public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviders) {
		return new ProviderManager(authenticationProviders);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(edAuthenticationProvider);
	}

}
