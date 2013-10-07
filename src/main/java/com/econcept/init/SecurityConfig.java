package com.econcept.init;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
			AppAuthenticationEntryPoint lEntryPoint = new AppAuthenticationEntryPoint();
			lEntryPoint.setRealmName("EConcept");
			
			pHttpSecurity
				.authorizeRequests()
					.antMatchers("/rest/*").hasAuthority("ROLE_USER")
					.and().httpBasic().authenticationEntryPoint(lEntryPoint);
		} // try
		catch (Exception pException) 
		{
			pException.printStackTrace();
			throw new Exception(pException);
		}  // catch
	}  // void configure
}  // class SecurityConfig 
