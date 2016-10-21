package com.crm.keymanager.app;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConfig {

	private static final String JDBC_PREFIX = "jdbc:postgresql://";

	private static final String DATABASE_URL = "DATABASE_URL";

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("${postgres.username}")
	private String username;

	@Value("${postgres.password}")
	private String password;

	@Value("${postgres.url}")
	private String url;

	@Value("${postgres.driver}")
	private String driverClassName;

	@Bean(name = "dataSource")
	@Profile("!heroku")
	public DataSource localDataSource() {
		return DataSourceBuilder.create().username(username).password(password).url(url)
				.driverClassName(driverClassName).build();
	}

	@Bean(name = "dataSource")
	@Profile("heroku")
	public DataSource herokuDataSource() {
		String databaseUrl = System.getenv(DATABASE_URL);
		try {
			URI uri = new URI(databaseUrl);
			url = JDBC_PREFIX + uri.getHost() + ":" + uri.getPort() + uri.getPath();
			username = uri.getUserInfo().split(":")[0];
			password = uri.getUserInfo().split(":")[1];
		} catch (URISyntaxException e) {
			LOGGER.error("Some error in getting DB connection .... " + e.toString());
		}
		return DataSourceBuilder.create().username(username).password(password).url(url)
				.driverClassName(driverClassName).build();
	}

}
