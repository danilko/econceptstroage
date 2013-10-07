package com.econcept.init;


import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
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
import com.jolbox.bonecp.BoneCPDataSource;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

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
	public DataSource getDataSource()
	{
		BoneCPDataSource lDataSource = new BoneCPDataSource();
		lDataSource.setDriverClass(mEnvironment.getProperty("jdbc.driverClassName"));
		lDataSource.setJdbcUrl(mEnvironment.getProperty("jdbc.url"));
		lDataSource.setUsername(mEnvironment.getProperty("jdbc.username"));
		lDataSource.setPassword(mEnvironment.getProperty("jdbc.password"));
		lDataSource.setPassword(mEnvironment.getProperty("jdbc.password"));
		lDataSource.setMaxConnectionsPerPartition(Integer.parseInt(mEnvironment.getProperty("jdbc.maxConnectionsPerPartition")));
		lDataSource.setMinConnectionsPerPartition(Integer.parseInt(mEnvironment.getProperty("jdbc.minConnectionsPerPartition")));

		return lDataSource;
	}  // DriverManagerDataSource getDataSource
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
	{
		return new PersistenceExceptionTranslationPostProcessor();
	}  // PersistenceExceptionTranslationPostProcessor exceptionTranslation
	
/*	@Bean
	public MongoDbFactory getMongoDBFactory() throws Exception
	{
		MongoClient lClient = new MongoClient(new ServerAddress(mEnvironment.getProperty("mongodb.host"), Integer.parseInt(mEnvironment.getProperty("mongodb.port"))));
		UserCredentials lUserCredentials = new UserCredentials(mEnvironment.getProperty("mongodb.username"), mEnvironment.getProperty("mongodb.password"));
		
		SimpleMongoDbFactory lFactory = new SimpleMongoDbFactory(lClient, mEnvironment.getProperty("mongodb.dbname"), lUserCredentials);
		
		return lFactory;
	}  // MongoDbFactory getMongoDBFactory
	
	@Bean
	public MongoTemplate getMongoTemplate() throws Exception
	{
		return new MongoTemplate(getMongoDBFactory());
	}  // MongoDbFactory getMongoDBFactory

	@Bean
	public MongoOperations getMongoOperations() throws Exception
	{
		return getMongoTemplate();
	}  // MongoDbFactory getMongoDBFactory
*/
}  // DAOConfig
