package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.titlesread.TitlesReadRequest;
import com.ericsson.titlesread.TitlesReadResponse;
import com.ericsson.titlesread.TitlesReadService;
import com.ericsson.titlesread.TitlesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;


@Component
public class TitlesReadClient {
    private final static Logger log = LogManager.getLogger(TitlesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public TitlesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private TitlesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing TitlesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.TITLES_READ;
            service = new TitlesReadService_Service(new URL(serviceUrl));

            log.debug("TitlesReadService URL: {}", serviceUrl);
            log.info("Initialization of TitlesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize TitlesReadClient", e);
            throw new RuntimeException("Failed to initialize TitlesReadClient",e);
        }
    }

    public TitlesReadResponse execute(TitlesReadRequest request, String username, String password) {
        log.info("Acquiring TitlesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        TitlesReadService port = service.getTitlesReadServiceSoap11();
        log.debug("TitlesReadService port acquired successfully.");
        log.info("Initiating TitlesRead call for user: {}", username);
        TitlesReadResponse response = port.titlesRead(request);
        log.info("TitlesRead affected successfully");
        return response;
    }
}

