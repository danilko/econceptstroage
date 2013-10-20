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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.econcept.provider.FileProvider;
import com.econcept.provider.UserProvider;

@Configuration
public class ServiceConfig 
{
	@Bean
	public FileProvider getFileProvider() {
		return new FileProvider();
	}  // FileProvider getFileProvider

	@Bean
	public UserProvider getUserProvider() {
		return new UserProvider();
	}  // UserProvider getUserProvider()
	
	@Bean
	public AuthenticationProvider getAuthenticationService()
	{
		DaoAuthenticationProvider lProvider =new DaoAuthenticationProvider();

		// Use SHA-256
		ShaPasswordEncoder lEncoder = new ShaPasswordEncoder(256);
		lEncoder.setIterations(1000);
		
		ReflectionSaltSource lSource = new ReflectionSaltSource();
		lSource.setUserPropertyToUse("getUserName");
		
		lProvider.setPasswordEncoder(lEncoder);
		lProvider.setSaltSource(lSource);
		
		lProvider.setUserDetailsService(getUserProvider());
		
/*		User lUser = new User();
		lUser.setUserName("admin_username");
		
		String lTest = lEncoder.encodePassword("admin_password", lSource.getSalt(lUser));
		System.out.println(lTest);
		System.out.println(lEncoder.isPasswordValid(lTest, "admin_password", lSource.getSalt(lUser)));

		lProvider.setUserDetailsService(mUserProvider);
		*/
		
		return lProvider;
	}  // AuthenticationProvider getAuthenticationProvider
}  // class ServiceConfig 
