package com.gavin.cfg;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages={"com.gavin.mapper"})
public class DruidConfiguration {
	
	@ConfigurationProperties(prefix="spring.datasource")
	@Bean(name = "dataSource")
    public DataSource dataSource() throws SQLException {
        return DataSourceBuilder.create().build();
    }
}
