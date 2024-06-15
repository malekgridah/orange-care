package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.martitalstatusread.MaritalStatusReadRequest;
import com.ericsson.martitalstatusread.MaritalStatusReadResponse;
import com.ericsson.martitalstatusread.MaritalStatusReadService;
import com.ericsson.martitalstatusread.MaritalStatusReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;


@Component
public class MaritalStatusReadClient {
    private final static Logger log = LogManager.getLogger(MaritalStatusReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public MaritalStatusReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private MaritalStatusReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing MaritalStatusReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.MARITAL_STATUS_READ;
            service = new MaritalStatusReadService_Service(new URL(serviceUrl));

            log.debug("MaritalStatusReadService URL: {}", serviceUrl);
            log.info("Initialization of MaritalStatusReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize MaritalStatusReadClient", e);
            throw new RuntimeException("Failed to initialize MaritalStatusReadClient",e);
        }
    }

    public MaritalStatusReadResponse execute(MaritalStatusReadRequest request, String username, String password) {
        log.info("Acquiring MaritalStatusReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        MaritalStatusReadService port = service.getMaritalStatusReadServiceSoap11();
        log.debug("MaritalStatusReadService port acquired successfully.");
        log.info("Initiating MaritalStatusRead call for user: {}", username);
        MaritalStatusReadResponse response = port.maritalStatusRead(request);
        log.info("MaritalStatusRead affected successfully");
        return response;
    }
}
