package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.contractread.ContractReadRequest;
import com.ericsson.contractread.ContractReadResponse;
import com.ericsson.contractread.ContractReadService;
import com.ericsson.contractread.ContractReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractReadClient {
    private final static Logger log = LogManager.getLogger(ContractReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public ContractReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private ContractReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CONTRACT_READ;
            service = new ContractReadService_Service(new URL(serviceUrl));

            log.debug("ContractReadService URL: {}", serviceUrl);
            log.info("Initialization of ContractReadClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractReadClient", e);
            throw new RuntimeException("Failed to initialize ContractReadClient",e);
        }
    }

    public ContractReadResponse execute(ContractReadRequest request, String username, String password) {
        log.info("Acquiring ContractReadService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ContractReadService port = service.getContractReadServiceSoap11();
        log.debug("ContractReadService port acquired successfully.");
        log.info("Initiating ContractRead call for user: {}", username);
        ContractReadResponse response = port.contractRead(request);
        log.info("ContractRead affected successfully");
        return response;
    }
}
