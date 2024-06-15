package com.billcom.payment.config.datasource;

import com.billcom.payment.commons.domains.bscs.FinTrxInterface;
import com.billcom.payment.utils.PaymentApiSettingProperties;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.billcom.payment.commons.repositories.bscs",
        entityManagerFactoryRef = "bscsEntityManagerFactory",
        transactionManagerRef = "bscsTransactionManager")
public class BscsDataSourceConfig {

    @Resource(name = "appSettingsProperties")
    private Properties properties;
    
    @Bean
    public DataSource dataSourceBscs() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource(this.properties.getProperty(PaymentApiSettingProperties.BSCS_JNDI_NAME));
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean bscsEntityManagerFactory() {
        var jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(false);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan(FinTrxInterface.class.getPackage().getName());
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setDataSource(dataSourceBscs());
        factory.setPersistenceUnitName("bscs");
        return factory;
    }

    @Bean
    public PlatformTransactionManager bscsTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(bscsEntityManagerFactory().getObject());
        return txManager;
    }
}
