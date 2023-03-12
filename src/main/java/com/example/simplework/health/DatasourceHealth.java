package com.example.simplework.health;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceHealth {

    @Bean
    @ConditionalOnBean(value = DataSource.class, name = "mainDataSource")
    @ConditionalOnEnabledHealthIndicator(value = "db.default")
    public DataSourceHealthIndicator defaultDataSource(
            @Qualifier("mainDataSource") DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource);
    }

    @Bean
    @ConditionalOnBean(value = DataSource.class, name = "secondDataSource")
    @ConditionalOnEnabledHealthIndicator(value = "db.second")
    public DataSourceHealthIndicator secondaryDataSource(
            @Qualifier("secondDataSource") DataSource dataSource) {
        return new DataSourceHealthIndicator(dataSource);
    }
}
