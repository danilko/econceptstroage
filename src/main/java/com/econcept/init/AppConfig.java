package com.econcept.init;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.oauth2.services.AuthorizationCodeGrantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.econcept.webservice.rest.UserService;
import com.econcept.webservice.rest.FileService;
import com.econcept.webservice.rest.RestServiceApplication;

@Configuration
public class AppConfig
{
	@Bean(destroyMethod = "shutdown")
	

	public SpringBus cxf()
	{
		return new SpringBus();
	}  // SpringBus cxf
	
	@Bean
	public FileService getFileService() {
		return new FileService();
	}  // FileEndpoint getFileService

	@Bean
	public UserService getUserService() {
		return new UserService();
	}  // AccountEndpoint getAccountService

	@Bean
	public RestServiceApplication getRestServiceApplication()
	{
		return new RestServiceApplication();
	}  // RestServiceApplication getRestServiceApplication

	@Bean
	public Server initJAXRSServer() {
		JAXRSServerFactoryBean lFactory = RuntimeDelegate.getInstance().createEndpoint(getRestServiceApplication(),
				JAXRSServerFactoryBean.class);
		lFactory.setServiceBeans(Arrays.<Object>asList(getUserService(), getFileService()));
		
		lFactory.setAddress(lFactory.getAddress());

		return lFactory.create();
	}  // Server initJAXRSServer
}  // AppConfig
