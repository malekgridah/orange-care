package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.contractservicesread.ContractServicesReadRequest;
import com.ericsson.contractservicesread.ContractServicesReadResponse;
import com.ericsson.contractservicesread.ContractServicesReadService;
import com.ericsson.contractservicesread.ContractServicesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractServicesReadClient {
    private final static Logger log = LogManager.getLogger(ContractServicesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public ContractServicesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private ContractServicesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractServicesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CONTRACT_SERVICES_READ;
            service = new ContractServicesReadService_Service(new URL(serviceUrl));

            log.debug("ContractServicesReadService URL: {}", serviceUrl);
            log.info("Initialization of ContractServicesReadClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractServicesReadClient", e);
            throw new RuntimeException("Failed to initialize ContractServicesReadClient",e);
        }
    }

    public ContractServicesReadResponse execute(ContractServicesReadRequest request, String username, String password) {
        log.info("Acquiring ContractServicesReadService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ContractServicesReadService port = service.getContractServicesReadServiceSoap11();
        log.debug("ContractServicesReadService port acquired successfully.");
        log.info("Initiating ContractServicesRead call for user: {}", username);
        ContractServicesReadResponse response = port.contractServicesRead(request);
        log.info("ContractServicesRead affected successfully");
        return response;
    }
}
