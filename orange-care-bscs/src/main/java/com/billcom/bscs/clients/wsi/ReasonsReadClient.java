package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.reasonsread.ReasonsReadRequest;
import com.ericsson.reasonsread.ReasonsReadResponse;
import com.ericsson.reasonsread.ReasonsReadService;
import com.ericsson.reasonsread.ReasonsReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ReasonsReadClient {
    private final static Logger log = LogManager.getLogger(ReasonsReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public ReasonsReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private ReasonsReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ReasonsReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.REASONS_READ;
            service = new ReasonsReadService_Service(new URL(serviceUrl));

            log.debug("ReasonsReadService URL: {}", serviceUrl);
            log.info("Initialization of ReasonsReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize ReasonsReadClient", e);
            throw new RuntimeException("Failed to initialize ReasonsReadClient",e);
        }
    }

    public ReasonsReadResponse execute(ReasonsReadRequest request, String username, String password) {
        log.info("Acquiring ReasonsReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ReasonsReadService port = service.getReasonsReadServiceSoap11();
        log.debug("ReasonsReadService port acquired successfully.");
        log.info("Initiating ReasonsRead call for user: {}", username);
        ReasonsReadResponse response = port.reasonsRead(request);
        log.info("ReasonsRead affected successfully");
        return response;
    }
}

