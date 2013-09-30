package com.econcept.init;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;

import com.econcept.entities.User;
import com.econcept.provider.UserProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Resource
	AuthenticationProvider mAuthenticationProvider;
	
	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder pAuth) throws Exception
	{
		pAuth
			.authenticationProvider(mAuthenticationProvider);
		
	}  // void registerAuthetnication
	
	@Override
	protected void configure(HttpSecurity pHttpSecurity) throws Exception
	{
		try {
			pHttpSecurity
				.authorizeRequests()
					.antMatchers("/rest/**").authenticated()
					.and().httpBasic();
		} // try
		catch (Exception pException) 
		{
			pException.printStackTrace();
			throw new Exception(pException);
		}  // catch
	}  // void configure
}  // class SecurityConfig 
