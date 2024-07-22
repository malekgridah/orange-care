package com.billcom.contracts.clients.wsi;

import com.billcom.contracts.clients.commons.ClientResolver;
import com.billcom.contracts.config.WebServicesProperties;
import com.billcom.contracts.utils.BaseWsWebService;
import com.ericsson.contractresourcesread.ContractResourcesReadRequest;
import com.ericsson.contractresourcesread.ContractResourcesReadResponse;
import com.ericsson.contractresourcesread.ContractResourcesReadService;
import com.ericsson.contractresourcesread.ContractResourcesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractResourcesReadClient {
    private final static Logger log = LogManager.getLogger(ContractResourcesReadClient.class);

    private final WebServicesProperties wsProperties;

    @Autowired
    public ContractResourcesReadClient(WebServicesProperties wsProperties) {
        this.wsProperties = wsProperties;
    }

    private ContractResourcesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractResourcesReadClient...");

            String serviceUrl = wsProperties.getWsi().getUrl() + BaseWsWebService.CONTRACT_RESOURCES_READ;
            service = new ContractResourcesReadService_Service(new URL(serviceUrl));

            log.debug("ContractResourcesReadService URL: {}", serviceUrl);
            log.info("Initialization of ContractResourcesReadClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractResourcesReadClient", e);
            throw new RuntimeException("Failed to initialize ContractResourcesReadClient",e);
        }
    }

    public ContractResourcesReadResponse execute(ContractResourcesReadRequest request) {
        return this.execute(request, null, null);
    }

    public ContractResourcesReadResponse execute(ContractResourcesReadRequest request, String username, String password) {
        log.info("Acquiring ContractResourcesReadService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = wsProperties.getWsi()
                    .getUsername();
            password = wsProperties.getWsi()
                    .getPassword();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ContractResourcesReadService port = service.getContractResourcesReadServiceSoap11();
        log.debug("ContractResourcesReadService port acquired successfully.");
        log.info("Initiating ContractResourcesRead call for user: {}", username);
        ContractResourcesReadResponse response = port.contractResourcesRead(request);
        log.info("ContractResourcesRead affected successfully");
        return response;
    }
}

