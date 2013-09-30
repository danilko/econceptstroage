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
public class ProviderConfig 
{
	@Bean
	public FileProvider getFileProvider() {
		return new FileProvider();
	}  // FileService getFileService

	@Bean
	public UserProvider getAccountProvider() {
		return new UserProvider();
	}  // AccountService getAccountService
	
	@Bean
	public AuthenticationProvider getAuthenticationProvider()
	{
		DaoAuthenticationProvider lProvider =new DaoAuthenticationProvider();

		// Use SHA-256
		ShaPasswordEncoder lEncoder = new ShaPasswordEncoder(256);
		lEncoder.setIterations(1000);
		
		ReflectionSaltSource lSource = new ReflectionSaltSource();
		lSource.setUserPropertyToUse("getUserName");
		
		lProvider.setPasswordEncoder(lEncoder);
		lProvider.setSaltSource(lSource);
		
		lProvider.setUserDetailsService(getAccountProvider());
		
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
