package com.billcom.payment.config;

import com.billcom.payment.utils.PaymentApiSettingProperties;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import java.util.Properties;

@Log4j2
@Configuration
public class PaymentProperties {

    @Bean
    public Properties webServicesProperties() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (Properties) jndiTemplate.lookup("paymentApi/WebServices");
    }

    @Bean
    public Properties appSettingsProperties() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (Properties) jndiTemplate.lookup("paymentApi/AppSettings");
    }

    @Bean
    public Properties rechargeProperties() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (Properties) jndiTemplate.lookup("paymentApi/rechargeProperties");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.modules(new JodaModule());
    }

    @Bean
    public String scheduledTimeProperty() throws NamingException {
        return this.appSettingsProperties().getProperty(PaymentApiSettingProperties.RETRY_FAILED_JOB);
    }
}
