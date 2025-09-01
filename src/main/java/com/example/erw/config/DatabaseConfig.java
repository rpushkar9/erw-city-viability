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
            // Format: postgresql://user:pass@host:port/db -> jdbc:postgresql://host:port/db
            databaseUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://");
            
            // Ensure proper format for PostgreSQL driver
            if (!databaseUrl.contains(":5432/") && !databaseUrl.matches(".*:\\d+/.*")) {
                // If no port specified, add default PostgreSQL port
                String[] parts = databaseUrl.split("@");
                if (parts.length == 2) {
                    String[] hostDb = parts[1].split("/");
                    if (hostDb.length == 2) {
                        databaseUrl = parts[0] + "@" + hostDb[0] + ":5432/" + hostDb[1];
                    }
                }
            }
        } else if (databaseUrl == null) {
            // Fallback for local development
            databaseUrl = "jdbc:postgresql://localhost:5432/erwdb";
        }
        
        return DataSourceBuilder.create()
                .url(databaseUrl)
                .build();
    }
}