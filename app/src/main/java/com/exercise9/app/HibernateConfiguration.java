package com.exercise9.app;

import java.util.Properties;
import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class HibernateConfiguration {
	
	@Value("${hibernate.dialect}")
	private String dialect;

	@Value("${hibernate.show.sql}")
	private String showSql;

	@Value("${hibernate.cache.provider}")
	private String cacheProvider;

	@Value("${hibernate.driver}")
	private String driverClass;

	@Value("${hibernate.url}")
	private String url;

	@Value("${hibernate.username}")
	private String username;

	@Value("${hibernate.password}")
	private String password;


	@Bean
	public BasicDataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/employeeregistration");
		dataSource.setUsername("postgres");
		dataSource.setPassword("ex1stgl0bal");
		return dataSource;
	}

	@Bean
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.put("hibernate.cache.use_second_level_cache","true");
		properties.put("hibernate.cache.use_query_cache","true");
		return properties;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactoryBean() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(getDataSource());
		sessionFactoryBean.setHibernateProperties(getProperties());
		sessionFactoryBean.setPackagesToScan(new String[] {"com.exercise9.core.model"});
		return sessionFactoryBean;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
}