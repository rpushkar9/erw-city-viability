package com.example.erw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            // Convert Render's postgresql:// URL to JDBC format
            databaseUrl = "jdbc:" + databaseUrl;
        } else if (databaseUrl == null) {
            // Fallback for local development
            databaseUrl = "jdbc:postgresql://localhost:5432/erwdb";
        }
        
        return DataSourceBuilder.create()
                .url(databaseUrl)
                .build();
    }
}