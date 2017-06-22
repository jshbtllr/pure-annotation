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

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@PropertySource("classpath:hibernate.properties")
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
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", dialect);
		properties.put("hibernate.show_sql", showSql);
		properties.put("hibernate.cache.region.factory_class", cacheProvider);
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
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}	

	@Bean
	@Autowired
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
}