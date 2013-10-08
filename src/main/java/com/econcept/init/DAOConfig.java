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
