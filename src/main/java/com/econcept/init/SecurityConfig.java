/**
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * 
 * Permission is hereby granted, free of charge, 
 * to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom 
 * the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice 
 * shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS 
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package com.econcept.init;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final static Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	
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
					.antMatchers("/rest/**").hasAuthority("ROLE_USER")
					.and().httpBasic().authenticationEntryPoint(lEntryPoint);
		} // try
		catch (Exception pException) 
		{
			LOGGER.debug(pException.toString());
			throw new Exception(pException);
		}  // catch
	}  // void configure
}  // class SecurityConfig 
