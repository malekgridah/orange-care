package com.billcom.contracts.clients.wsi;

import com.billcom.contracts.clients.commons.ClientResolver;
import com.billcom.contracts.config.WebServicesProperties;
import com.billcom.contracts.utils.BaseWsWebService;
import com.ericsson.rateplansread.RateplansReadRequest;
import com.ericsson.rateplansread.RateplansReadResponse;
import com.ericsson.rateplansread.RateplansReadService;
import com.ericsson.rateplansread.RateplansReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;


@Component
public class RateplansReadClient {
    private final static Logger log = LogManager.getLogger(RateplansReadClient.class);

    private final WebServicesProperties webServiceConfig;

    @Autowired
    public RateplansReadClient(WebServicesProperties webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private RateplansReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing RateplansReadClient...");

            String serviceUrl = webServiceConfig.getWsi().getUrl() + BaseWsWebService.RATEPLANS_READ;
            service = new RateplansReadService_Service(new URL(serviceUrl));

            log.debug("RateplansReadService URL: {}", serviceUrl);
            log.info("Initialization of RateplansReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize RateplansReadClient", e);
            throw new RuntimeException("Failed to initialize RateplansReadClient",e);
        }
    }

    public RateplansReadResponse execute(RateplansReadRequest request, String username, String password) {
        log.info("Acquiring RateplansReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsi().getUsername();
            password = webServiceConfig.getWsi().getPassword();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        RateplansReadService port = service.getRateplansReadServiceSoap11();
        log.debug("RateplansReadService port acquired successfully.");
        log.info("Initiating RateplansRead call for user: {}", username);
        RateplansReadResponse response = port.rateplansRead(request);
        log.info("RateplansRead affected successfully");
        return response;
    }
}

