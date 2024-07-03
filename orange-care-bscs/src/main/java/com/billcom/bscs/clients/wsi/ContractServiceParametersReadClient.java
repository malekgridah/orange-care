package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.contractservice.parametersread.ContractServiceParametersReadRequest;
import com.ericsson.contractservice.parametersread.ContractServiceParametersReadResponse;
import com.ericsson.contractservice.parametersread.ContractServiceParametersReadService;
import com.ericsson.contractservice.parametersread.ContractServiceParametersReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractServiceParametersReadClient {
    private final static Logger log = LogManager.getLogger(ContractServiceParametersReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public ContractServiceParametersReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private ContractServiceParametersReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractServiceParametersReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CONTRACT_SERVICE_PARAMETERS_READ;
            service = new ContractServiceParametersReadService_Service(new URL(serviceUrl));

            log.debug("ContractServiceParametersReadService URL: {}", serviceUrl);
            log.info("Initialization of ContractServiceParametersReadClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractServiceParametersReadClient", e);
            throw new RuntimeException("Failed to initialize ContractServiceParametersReadClient",e);
        }
    }

    public ContractServiceParametersReadResponse execute(ContractServiceParametersReadRequest request, String username, String password) {
        log.info("Acquiring ContractServiceParametersReadService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ContractServiceParametersReadService port = service.getContractServiceParametersReadServiceSoap11();
        log.debug("ContractServiceParametersReadService port acquired successfully.");
        log.info("Initiating ContractServiceParametersRead call for user: {}", username);
        ContractServiceParametersReadResponse response = port.contractServiceParametersRead(request);
        log.info("ContractServiceParametersRead affected successfully");
        return response;
    }
}
