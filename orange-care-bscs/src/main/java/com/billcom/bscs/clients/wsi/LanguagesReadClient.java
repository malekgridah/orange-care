package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.languagesread.LanguagesReadRequest;
import com.ericsson.languagesread.LanguagesReadResponse;
import com.ericsson.languagesread.LanguagesReadService;
import com.ericsson.languagesread.LanguagesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;


@Component
public class LanguagesReadClient {
    private final static Logger log = LogManager.getLogger(LanguagesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public LanguagesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private LanguagesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing LanguagesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.LANGUAGES_READ;
            service = new LanguagesReadService_Service(new URL(serviceUrl));

            log.debug("LanguagesReadService URL: {}", serviceUrl);
            log.info("Initialization of LanguagesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize LanguagesReadClient", e);
            throw new RuntimeException("Failed to initialize LanguagesReadClient",e);
        }
    }

    public LanguagesReadResponse execute(LanguagesReadRequest request, String username, String password) {
        log.info("Acquiring LanguagesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        LanguagesReadService port = service.getLanguagesReadServiceSoap11();
        log.debug("LanguagesReadService port acquired successfully.");
        log.info("Initiating LanguagesRead call for user: {}", username);
        LanguagesReadResponse response = port.languagesRead(request);
        log.info("LanguagesRead affected successfully");
        return response;
    }
}
