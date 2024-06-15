package com.billcom.bscs.clients.bscs;

import com.billcom.customer.handling.*;
import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.WebServiceConfig;
import jakarta.annotation.PostConstruct;
import jakarta.xml.ws.BindingProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;


@Component
public class CustomerHandlingClient {

    private static final Logger log = LogManager.getLogger(CustomerHandlingClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public CustomerHandlingClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private String CUSTOMER_HANDLING_URL;

    private CustomerHandlingService customerHandlingService;


    @PostConstruct
    private void init() {
        try {
            log.info("Initializing CustomerHandling WebService Client...");

            CUSTOMER_HANDLING_URL = this.webServiceConfig.getCustomerHandlingUrl();

            log.debug("CustomerHandling WebService Endpoint URL: {}", CUSTOMER_HANDLING_URL);

            customerHandlingService = new CustomerHandlingService(new URL(CUSTOMER_HANDLING_URL));
            log.info("Initialization of CustomerHandlingClient with the CustomerHandling WebService is successful");
        } catch (MalformedURLException e) {
            log.error("Failed to initialize CustomerHandlingClient", e);
            throw new RuntimeException("Failed to initialize CustomerHandlingClient", e);
        }

    }

    private CustomerHandling getPort(String username, String password) {
        log.info("Acquiring CustomerHandling port...");

        if (username == null || username.isEmpty()) {
            log.debug("User credentials are not provided or invalid.");
            username = this.webServiceConfig.getCustomerHandlingUrl();
            password = this.webServiceConfig.getCustomerHandlingPass();
            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        }

        try {
            customerHandlingService.setHandlerResolver(new ClientResolver(username, password));
            CustomerHandling port = customerHandlingService.getCustomerHandling();
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, CUSTOMER_HANDLING_URL);

            log.info("CustomerHandling port acquired successfully.");
            return port;
        } catch (Exception e) {
            log.error("Failed to acquire CustomerHandling port: {}", e.getMessage());
            throw new RuntimeException("Error getting CustomerHandling port", e);
        }
    }


    public GetCustomerDetailsResponse getCustomerDetails(GetCustomerDetailsRequest request, String username, String password) throws UnexpectedError {
        log.info("Initiating getCustomerDetails call for user: {}", username);
        long startTime = System.currentTimeMillis();
        try {
            CustomerHandling port = this.getPort(username, password);

            log.debug("Web service port obtained, calling getCustomerDetails operation...");

            GetCustomerDetailsResponse response = port.getCustomerDetails(request);
            log.debug("Response received: {}", response.toString());
            log.info("Customer details retrieved successfully in {} ms", System.currentTimeMillis() - startTime);
            return response;
        } catch (UnexpectedError e) {
            log.error("Unexpected error occurred after {} ms while getting customer details: {}", (System.currentTimeMillis() - startTime), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("General error occurred after {} ms while getting customer details: {}", (System.currentTimeMillis() - startTime), e.getMessage());
            throw new RuntimeException("Error getting customer details", e);
        }
    }


    public EntityResponse createCustomer(CustomerRequest request, String username, String password) throws UnexpectedError {
        log.info("Initiating createCustomer call for user: {}", username);
        long startTime = System.currentTimeMillis();
        try {
            CustomerHandling port = this.getPort(username, password);

            log.debug("Web service port obtained, calling createCustomer operation...");

            EntityResponse response = port.createCustomer(request);
            log.info("Customer created successfully in {} ms", System.currentTimeMillis() - startTime);
            return response;
        } catch (UnexpectedError e) {
            log.error("Unexpected error occurred after {} ms while creating customer: {}", (System.currentTimeMillis() - startTime), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("General error occurred after {} ms while while creating customer: {}", (System.currentTimeMillis() - startTime), e.getMessage());
            throw new RuntimeException("Error getting creating customer", e);
        }
    }


    public OperationResponse updateCustomer(CustomerRequest request, String username, String password) throws UnexpectedError {
        log.info("Initiating updateCustomer call for user: {}", username);
        long startTime = System.currentTimeMillis();
        try {
            CustomerHandling port = this.getPort(username, password);

            log.debug("Web service port obtained, calling updateCustomer operation...");

            OperationResponse response = port.updateCustomer(request);
            log.info("Customer updated successfully in {} ms", System.currentTimeMillis() - startTime);
            return response;
        } catch (UnexpectedError e) {
            log.error("Unexpected error occurred after {} ms while updating customer: {}", (System.currentTimeMillis() - startTime), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("General error occurred after {} ms while updating customer: {}", (System.currentTimeMillis() - startTime), e.getMessage());
            throw new RuntimeException("Error getting updating customer", e);
        }
    }

}
