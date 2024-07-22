package com.billcom.contracts.clients.wsi;

import com.billcom.contracts.clients.commons.ClientResolver;
import com.billcom.contracts.config.WebServicesProperties;
import com.billcom.contracts.utils.BaseWsWebService;
import com.ericsson.servicepackagesread.ServicePackagesReadRequest;
import com.ericsson.servicepackagesread.ServicePackagesReadResponse;
import com.ericsson.servicepackagesread.ServicePackagesReadService;
import com.ericsson.servicepackagesread.ServicePackagesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ServicePackagesReadClient {
    private final static Logger log = LogManager.getLogger(ServicePackagesReadClient.class);

    private final WebServicesProperties wsProperties;

    @Autowired
    public ServicePackagesReadClient(WebServicesProperties wsProperties) {
        this.wsProperties = wsProperties;
    }

    private ServicePackagesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ServicePackagesReadClient...");

            String serviceUrl = wsProperties.getWsi().getUrl() + BaseWsWebService.SERVICE_PACKAGES_READ;
            service = new ServicePackagesReadService_Service(new URL(serviceUrl));

            log.debug("ServicePackagesReadService URL: {}", serviceUrl);
            log.info("Initialization of ServicePackagesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize ServicePackagesReadClient", e);
            throw new RuntimeException("Failed to initialize ServicePackagesReadClient",e);
        }
    }

    public ServicePackagesReadResponse execute(ServicePackagesReadRequest request) {
        return this.execute(request, null, null);
    }

    public ServicePackagesReadResponse execute(ServicePackagesReadRequest request, String username, String password) {
        log.info("Acquiring ServicePackagesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = wsProperties.getWsi()
                    .getUsername();
            password = wsProperties.getWsi()
                    .getPassword();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ServicePackagesReadService port = service.getServicePackagesReadServiceSoap11();
        log.debug("ServicePackagesReadService port acquired successfully.");
        log.info("Initiating ServicePackagesRead call for user: {}", username);
        ServicePackagesReadResponse response = port.servicePackagesRead(request);
        log.info("ServicePackagesRead affected successfully");
        return response;
    }
}

