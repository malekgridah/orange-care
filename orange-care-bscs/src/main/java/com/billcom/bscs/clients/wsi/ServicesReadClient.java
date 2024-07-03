package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.servicesread.ServicesReadRequest;
import com.ericsson.servicesread.ServicesReadResponse;
import com.ericsson.servicesread.ServicesReadService;
import com.ericsson.servicesread.ServicesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ServicesReadClient {
    private final static Logger log = LogManager.getLogger(ServicesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public ServicesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private ServicesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ServicesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.SERVICES_READ;
            service = new ServicesReadService_Service(new URL(serviceUrl));

            log.debug("ServicesReadService URL: {}", serviceUrl);
            log.info("Initialization of ServicesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize ServicesReadClient", e);
            throw new RuntimeException("Failed to initialize ServicesReadClient",e);
        }
    }

    public ServicesReadResponse execute(ServicesReadRequest request, String username, String password) {
        log.info("Acquiring ServicesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ServicesReadService port = service.getServicesReadServiceSoap11();
        log.debug("ServicesReadService port acquired successfully.");
        log.info("Initiating ServicesRead call for user: {}", username);
        ServicesReadResponse response = port.servicesRead(request);
        log.info("ServicesRead affected successfully");
        return response;
    }
}
