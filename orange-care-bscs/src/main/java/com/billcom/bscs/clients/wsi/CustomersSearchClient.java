package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.customerssearch.CustomersSearchRequest;
import com.ericsson.customerssearch.CustomersSearchResponse;
import com.ericsson.customerssearch.CustomersSearchService;
import com.ericsson.customerssearch.CustomersSearchService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class CustomersSearchClient {
    private final static Logger log = LogManager.getLogger(CustomersSearchClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public CustomersSearchClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private CustomersSearchService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing CustomersSearchClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CUSTOMERS_SEARCH;
            service = new CustomersSearchService_Service(new URL(serviceUrl));

            log.debug("CustomersSearchService URL: {}", serviceUrl);
            log.info("Initialization of CustomersSearchClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize CustomersSearchClient", e);
            throw new RuntimeException("Failed to initialize CustomersSearchClient",e);
        }
    }

    public CustomersSearchResponse execute(CustomersSearchRequest request, String username, String password) {
        log.info("Acquiring CustomersSearchService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        CustomersSearchService port = service.getCustomersSearchServiceSoap11();
        log.debug("CustomersSearchService port acquired successfully.");
        log.info("Initiating CustomersSearch call for user: {}", username);
        CustomersSearchResponse response = port.customersSearch(request);
        log.info("CustomersSearch affected successfully");
        return response;
    }
}
