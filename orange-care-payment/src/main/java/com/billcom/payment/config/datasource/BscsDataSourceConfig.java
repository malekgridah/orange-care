package com.billcom.payment.config.datasource;

import com.billcom.payment.commons.domains.bscs.FinTrxInterface;
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
@EnableJpaRepositories(basePackages = "com.billcom.payment.commons.repositories.bscs",
        entityManagerFactoryRef = "bscsEntityManagerFactory",
        transactionManagerRef = "bscsTransactionManager")
public class BscsDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.bscs")
    public DataSourceProperties bscsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourceBscs")
    public DataSource dataSourceBscs(DataSourceProperties bscsDataSourceProperties) {
        return bscsDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean bscsEntityManagerFactory(@Qualifier("dataSourceBscs")DataSource dataSourceBscs) {
        var jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan(FinTrxInterface.class.getPackage().getName());
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setDataSource(dataSourceBscs);
        factory.setPersistenceUnitName("bscs");
        return factory;
    }

    @Bean
    public PlatformTransactionManager bscsTransactionManager(@Qualifier("bscsEntityManagerFactory")LocalContainerEntityManagerFactoryBean bscsEntityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(bscsEntityManagerFactory.getObject());
        return txManager;
    }
}
