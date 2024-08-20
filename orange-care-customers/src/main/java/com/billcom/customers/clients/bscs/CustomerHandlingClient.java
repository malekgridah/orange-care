package com.billcom.customers.clients.bscs;

import com.billcom.customer.handling.*;
import com.billcom.customer.handling.CustomerHandlingService;
import com.billcom.customers.clients.commons.ClientResolver;
import com.billcom.customers.config.WebServicesProperties;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class CustomerHandlingClient {
    private final static Logger log = LogManager.getLogger(CustomerHandlingClient.class);

    private final WebServicesProperties wsProperties;

    @Autowired
    public CustomerHandlingClient(WebServicesProperties wsProperties) {
        this.wsProperties = wsProperties;
    }

    private CustomerHandlingService service;

    @PostConstruct
    private void init() {
        try {
            log.info("Initializing CustomerHandlingClient...");

            String serviceUrl = wsProperties.getCustomerHandling().getUrl();
            service = new CustomerHandlingService(new URL(serviceUrl));

            log.debug("CustomerHandlingService URL: {}", serviceUrl);
            log.info("Initialization of CustomerHandlingClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize CustomerHandlingClient", e);
            throw new RuntimeException("Failed to initialize CustomerHandlingClient",e);
        }
    }

    public EntityResponse execute(CustomerRequest request) throws UnexpectedError {
        return this.execute(request, "ADMX", "ADMX");
    }

    public EntityResponse execute(CustomerRequest request, String username, String password) throws UnexpectedError {
        log.info("Acquiring CustomerHandlingService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = wsProperties.getCustomerHandling()
                    .getUsername();
            password = wsProperties.getCustomerHandling()
                    .getPassword();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        CustomerHandling port = service.getCustomerHandling();
        log.debug("CustomerHandlingService port acquired successfully.");
        log.info("Initiating CustomerHandling call for user: {}", username);
        EntityResponse response = port.createCustomer(request);
        log.info("CustomerHandling affected successfully");
        return response;
    }
}
