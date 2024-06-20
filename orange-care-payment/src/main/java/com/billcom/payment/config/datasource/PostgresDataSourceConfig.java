package com.billcom.payment.config.datasource;

import com.billcom.payment.commons.domains.postgres.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = "com.billcom.payment.commons.repositories.postgres",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager")
public class PostgresDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourcePostgres")
    public DataSource dataSourcePostgres(DataSourceProperties postgresDataSourceProperties) {
        return postgresDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(@Qualifier("dataSourcePostgres")DataSource dataSourcePostgres) {
        var jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setShowSql(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan(User.class.getPackage().getName());
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setDataSource(dataSourcePostgres);
        factory.setPersistenceUnitName("postgres");
        return factory;
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManagerFactory")LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(postgresEntityManagerFactory.getObject());
        return txManager;
    }
}

