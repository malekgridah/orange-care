package com.billcom.customers.clients.wsi;

import com.billcom.customers.clients.commons.ClientResolver;
import com.billcom.customers.config.WebServicesProperties;
import com.billcom.customers.utils.BaseWsWebService;
import com.ericsson.customerread.CustomerReadRequest;
import com.ericsson.customerread.CustomerReadResponse;
import com.ericsson.customerread.CustomerReadService;
import com.ericsson.customerread.CustomerReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class CustomerReadClient {
    private final static Logger log = LogManager.getLogger(CustomerReadClient.class);

    private final WebServicesProperties webServiceConfig;

    @Autowired
    public CustomerReadClient(WebServicesProperties webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private CustomerReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing CustomerReadClient...");

            String serviceUrl = webServiceConfig.getWsi().getUrl() + BaseWsWebService.CUSTOMER_READ;
            service = new CustomerReadService_Service(new URL(serviceUrl));

            log.debug("CustomerReadService URL: {}", serviceUrl);
            log.info("Initialization of CustomerReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize CustomerReadClient", e);
            throw new RuntimeException("Failed to initialize CustomerReadClient",e);
        }
    }

    public CustomerReadResponse execute(CustomerReadRequest request) {
        return this.execute(request, null, null);
    }

    public CustomerReadResponse execute(CustomerReadRequest request, String username, String password) {
        log.info("Acquiring CustomerReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsi().getUsername();
            password = webServiceConfig.getWsi().getPassword();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        CustomerReadService port = service.getCustomerReadServiceSoap11();
        log.debug("CustomerReadService port acquired successfully.");
        log.info("Initiating CustomerRead call for user: {}", username);
        CustomerReadResponse response = port.customerRead(request);
        log.info("CustomerRead affected successfully");
        return response;
    }

}
