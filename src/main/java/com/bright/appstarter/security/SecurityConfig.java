package com.bright.appstarter.security;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.bright.appstarter.userlogin.ParameterKeepingRedirectAuthenticationFailureHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Inject
	private UserDetailsService userDetailsService;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/static/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.failureHandler(
				new ParameterKeepingRedirectAuthenticationFailureHandler(
					"/login?error", "emailAddress", "remember-me"))
			.usernameParameter("emailAddress")
			.permitAll()
			.and()
			.logout()
			.permitAll()
			.and()
			.rememberMe()
			.tokenRepository(persistentTokenRepository());
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository()
	{
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
}
