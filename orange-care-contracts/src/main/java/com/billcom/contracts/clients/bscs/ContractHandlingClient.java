package com.billcom.contracts.clients.bscs;

import com.billcom.contract.handling.*;
import com.billcom.contract.handling.ContractHandlingService;
import com.billcom.contracts.clients.commons.ClientResolver;
import com.billcom.contracts.config.WebServicesProperties;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractHandlingClient {
    private final static Logger log = LogManager.getLogger(ContractHandlingClient.class);

    private final WebServicesProperties wsProperties;

    @Autowired
    public ContractHandlingClient(WebServicesProperties wsProperties) {
        this.wsProperties = wsProperties;
    }

    private ContractHandlingService service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractHandlingClient...");

            String serviceUrl = wsProperties.getContractHandling().getUrl();
            service = new ContractHandlingService(new URL(serviceUrl));

            log.debug("ContractHandlingService URL: {}", serviceUrl);
            log.info("Initialization of ContractHandlingClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractHandlingClient", e);
            throw new RuntimeException("Failed to initialize ContractHandlingClient",e);
        }
    }

    public CreateContractResponse execute(CreateContractRequest request) throws UnexpectedError {
        return this.execute(request, null, null);
    }

    public CreateContractResponse execute(CreateContractRequest request, String username, String password) throws UnexpectedError {
        log.info("Acquiring ContractHandlingService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = wsProperties.getContractHandling()
                    .getUsername();
            password = wsProperties.getContractHandling()
                    .getPassword();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ContractHandling port = service.getContractHandling();
        log.debug("ContractHandlingService port acquired successfully.");
        log.info("Initiating ContractHandling call for user: {}", username);
        CreateContractResponse response = port.createContract(request);
        log.info("ContractHandling affected successfully");
        return response;
    }
}
