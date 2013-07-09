package com.econcept.init;

import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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

import com.econcept.dao.AccountDAOImpl;
import com.econcept.entities.Account;
import com.econcept.webservice.rest.AccountResource;
import com.econcept.webservice.rest.RestServiceApplication;
import com.econcept.webservice.rest.FileResource;

@Configuration
@EnableWebMvc
@PropertySource(value="classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter
{
	// Inject PropertySource into environment instance
	@Inject
	private Environment mEnvironment;
	
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
	public AccountDAOImpl getAccountDAOImpl()
	{
		return new AccountDAOImpl();
	}  // AccountDAO getAccountDAO

	@Bean
	public Account getAccount()
	{
		return new Account();
	}  // Account getAccount
	
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
		lDataSource.setDriverClassName(mEnvironment.getProperty("jdbc.driverClassName"));
		lDataSource.setUrl(mEnvironment.getProperty("jdbc.url"));
		lDataSource.setUsername(mEnvironment.getProperty("jdbc.username"));
		lDataSource.setPassword(mEnvironment.getProperty("jdbc.password"));
		
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
