package com.test.handson1.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppConfig {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${spring.datasource.driverClassName}")
	private String driverClass;

	@Bean
	public DataSource dataSource() {
		MultiTenancy multiTenancy = new MultiTenancy();

		multiTenancy.addTenant("default", defaultDataSource());
		multiTenancy.addTenant("0", defaultDataSource());
		multiTenancy.addTenant("1", h2DataSource());
//		multiTenancy.addTenant("2", mysqlDataSource());

		multiTenancy.setDefaultTargetDataSource(defaultDataSource());
		multiTenancy.setTargetDataSources((Map) multiTenancy.getTenants());

		// Call this to finalize the initialization of the data source.
		multiTenancy.afterPropertiesSet();

		return multiTenancy;
	}

	private DataSource defaultDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/source");
		hikariConfig.setUsername("postgres");
		hikariConfig.setPassword("riaoracle");
		hikariConfig.setDriverClassName("org.postgresql.Driver");

		return new HikariDataSource(hikariConfig);
	}

	private DataSource h2DataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl("jdbc:h2:mem:h2-test-db");
		hikariConfig.setUsername("sa");
		hikariConfig.setPassword("password");
		hikariConfig.setDriverClassName("org.h2.Driver");

		return new HikariDataSource(hikariConfig);
	}

	private DataSource mysqlDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/source");
		hikariConfig.setUsername("postgres");
		hikariConfig.setPassword("riaoracle");
		hikariConfig.setDriverClassName("org.postgresql.Driver");

		return new HikariDataSource(hikariConfig);
	}

}
