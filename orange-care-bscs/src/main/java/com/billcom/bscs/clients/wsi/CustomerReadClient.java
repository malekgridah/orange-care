package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
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

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public CustomerReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private CustomerReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing CustomerReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CUSTOMER_READ;
            service = new CustomerReadService_Service(new URL(serviceUrl));

            log.debug("CustomerReadService URL: {}", serviceUrl);
            log.info("Initialization of CustomerReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize CustomerReadClient", e);
            throw new RuntimeException("Failed to initialize CustomerReadClient",e);
        }
    }

    public CustomerReadResponse execute(CustomerReadRequest request, String username, String password) {
        log.info("Acquiring CustomerReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
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
