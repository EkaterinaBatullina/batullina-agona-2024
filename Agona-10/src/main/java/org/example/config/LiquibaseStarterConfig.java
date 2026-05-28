package org.example.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(name = "liquibase.enabled", havingValue = "true", matchIfMissing = false)
public class LiquibaseStarterConfig {

    @Value("${liquibase.changelog}")
    private String changelog;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        if (changelog == null || changelog.isEmpty()) {
            throw new IllegalArgumentException("Liquibase changelog must be provided in application.yml.");
        }
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changelog);
        return liquibase;
    }
}
