package com.test.handson1.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppConfig {

	@Bean
	public DataSource dataSource() {
		MultiTenancy multiTenancy = MultiTenancy.getInstance();

		multiTenancy.addTenant("0", defaultDataSource());
		multiTenancy.addTenant("1", mysqlDataSource());

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

	private DataSource mysqlDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/springbootdb");
		hikariConfig.setUsername("root");
		hikariConfig.setPassword("Ayush@27300");
		hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");

		return new HikariDataSource(hikariConfig);
	}

}
