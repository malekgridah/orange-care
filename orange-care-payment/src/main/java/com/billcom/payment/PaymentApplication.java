package com.billcom.payment;

import com.billcom.payment.config.properties.SettingsProperties;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.eclipse.persistence.oxm.sequenced.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentApplication {

    private final SettingsProperties settingsProperties;

    @Autowired
    public PaymentApplication(SettingsProperties settingsProperties) {
        this.settingsProperties = settingsProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    @Bean
    public ApplicationRunner configureLog4j() {
        return args -> {
            String logLevel = settingsProperties.getLogLevel();
            if (logLevel != null) {
                log.info("setting logging level to : {}", logLevel);
                Configurator.setRootLevel(Level.valueOf(logLevel));
            }
        };
    }
}
