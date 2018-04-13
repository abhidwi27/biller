package com.app.biller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan
@PropertySources({
	@PropertySource("classpath:sqlquery.properties"),
	@PropertySource("classpath:biller.properties")})
public class RootConfig {

	private DriverManagerDataSource dataSource = new DriverManagerDataSource();

	@Bean
	@Profile("dev")
	public DataSource dataSourceDev() {
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/biller");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	@Bean
	@Profile("prod")
	public DataSource dataSourceProd() {
		dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
		dataSource.setUrl("jdbc:db2://inmbzp5180.in.dst.ibm.com:50000/BILLER");
		dataSource.setUsername("billeradmin");
		dataSource.setPassword("admin1234");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}

}
