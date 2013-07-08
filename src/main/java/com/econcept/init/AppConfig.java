package com.econcept.init;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.econcept.webservice.rest.AccountResource;
import com.econcept.webservice.rest.RestServiceApplication;
import com.econcept.webservice.rest.FileResource;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter
{
	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf()
	{
		return new SpringBus();
	}  // SpringBus cxf
	
	@Bean
	public FileResource getFileService() {
		return new FileResource();
	}  // FileService getFileService

	@Bean
	public AccountResource getAccountService() {
		return new AccountResource();
	}  // AccountService getAccountService

	@Bean
	public RestServiceApplication getRestServiceApplication()
	{
		return new RestServiceApplication();
	}  // RestServiceApplication getRestServiceApplication

	
	@Bean
	public Server initJAXRSServer() {
		JAXRSServerFactoryBean lFactory = RuntimeDelegate.getInstance().createEndpoint(getRestServiceApplication(),
				JAXRSServerFactoryBean.class);
		lFactory.setServiceBeans(Arrays.<Object>asList(getAccountService(), getFileService()));
		
		lFactory.setAddress(lFactory.getAddress());

		return lFactory.create();
	}  // Server initJAXRSServer
	
	@Bean
	public JpaVendorAdapter getJpaVendorAdapter()
	{
		JpaVendorAdapter lVendorAdapter = new OpenJpaVendorAdapter();
		
		return lVendorAdapter;
	}  // JpaVendorAdapter getJpaVendorAdapter
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean()
	{
		LocalContainerEntityManagerFactoryBean lBean = new LocalContainerEntityManagerFactoryBean();
		
		lBean.setJpaVendorAdapter(getJpaVendorAdapter());
		
		lBean.setDataSource(getDataSource());
		
		lBean.setPackagesToScan(new String[] {"com.econcept.dao", "com.econcept.entities"});
		
		lBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		
		//lBean.setLoadTimeWeaver(new RelflectiveLoadTeimeWeaver());
		return lBean;
	}  // LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean
	
	@Bean
	public PlatformTransactionManager getTransactionManager()
	{
		JpaTransactionManager lTransactionManager = new JpaTransactionManager();
		lTransactionManager.setEntityManagerFactory(getEntityManagerFactoryBean().getObject());
		return lTransactionManager;
	}  // 
	
	@Bean
	public DriverManagerDataSource getDataSource()
	{
		DriverManagerDataSource lDataSource = new DriverManagerDataSource();
		lDataSource.setDriverClassName("org.postgresql.Driver");
		lDataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		lDataSource.setPassword("postgres");
		lDataSource.setPassword("postgresql");
		
		return lDataSource;
	}  // 
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}  
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
	}  
}  // ApplicationConfiguration
