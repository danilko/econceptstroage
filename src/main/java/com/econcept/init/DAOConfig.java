package com.econcept.init;


import javax.inject.Inject;

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

import com.econcept.dao.UserDAO;
import com.econcept.dao.UserDAOImpl;
import com.econcept.entities.User;
import com.econcept.entities.UserAuthority;
import com.econcept.entities.UserRole;
import com.econcept.webservice.rest.UserService;

@Configuration
@PropertySource(value="classpath:application.properties")
public class DAOConfig
{
	// Inject PropertySource into environment instance
	@Inject
	private Environment mEnvironment;
	
	@Bean
	public UserService getAccountService() {
		return new UserService();
	}  // AccountService getAccountService

	@Bean
	public UserDAO getUserDAO()
	{
		return new UserDAOImpl();
	}  // AccountDAO getUserDAO

	@Bean
	public User getUser()
	{
		return new User();
	}  // Account getUser
	
	@Bean
	public UserAuthority getUserAuthority()
	{
		return new UserAuthority();
	}  // Account getUser
	
	@Bean
	public UserRole getUserRole()
	{
		return new UserRole();
	}  // Account getUser
	
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
	}  // DriverManagerDataSource getDataSource
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}  // PersistenceExceptionTranslationPostProcessor exceptionTranslation
}  // DAOConfig
