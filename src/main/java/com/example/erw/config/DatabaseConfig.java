package com.example.erw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            try {
                // Parse the Render PostgreSQL URL using URI
                URI uri = URI.create(databaseUrl);
                
                // Extract components
                String host = uri.getHost();
                int port = uri.getPort() != -1 ? uri.getPort() : 5432;
                String database = uri.getPath().substring(1); // Remove leading /
                String userInfo = uri.getUserInfo();
                
                // Reconstruct as JDBC URL without credentials (they're handled separately)
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
                
                // Extract username and password for DataSource
                String username = null;
                String password = null;
                if (userInfo != null && userInfo.contains(":")) {
                    String[] credentials = userInfo.split(":", 2);
                    username = credentials[0];
                    password = credentials[1];
                }
                
                System.out.println("DEBUG: Original URL: " + databaseUrl);
                System.out.println("DEBUG: Parsed JDBC URL: " + jdbcUrl);
                System.out.println("DEBUG: Username: " + username);
                
                return DataSourceBuilder.create()
                        .url(jdbcUrl)
                        .username(username)
                        .password(password)
                        .build();
                        
            } catch (Exception e) {
                System.err.println("ERROR parsing DATABASE_URL: " + e.getMessage());
                e.printStackTrace();
                // Fall back to localhost
                databaseUrl = "jdbc:postgresql://localhost:5432/erwdb";
            }
        }
        
        if (databaseUrl == null) {
            // Fallback for local development
            databaseUrl = "jdbc:postgresql://localhost:5432/erwdb";
        }
        
        return DataSourceBuilder.create()
                .url(databaseUrl)
                .build();
    }
}