package com.app.biller;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.app.biller.dao.ILCDataDaoImpl;

@Configuration
@ComponentScan
@PropertySources({
	@PropertySource("classpath:sqlquery-${spring.profiles.active}.properties"),
	@PropertySource("classpath:application-${spring.profiles.active}.properties")})
public class RootConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ILCDataDaoImpl.class);
	private DriverManagerDataSource dataSource = new DriverManagerDataSource();

	@Bean
	@Profile("dev")
	public DataSource dataSourceDev() {
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/biller");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		logger.info("Datasource properties set for Dev environment...");
		return dataSource;
	}

	@Bean
	@Profile("prod")
	public DataSource dataSourceProd() {
		dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
		dataSource.setUrl("jdbc:db2://inmbzp5180.in.dst.ibm.com:50000/BILLER");
		dataSource.setUsername("billeradmin");
		dataSource.setPassword("admin1234");
		logger.info("Datasource properties set for Prod environment...");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		logger.info("JDBCTemplate linked with datasource...");
		return jdbcTemplate;
	}

}
