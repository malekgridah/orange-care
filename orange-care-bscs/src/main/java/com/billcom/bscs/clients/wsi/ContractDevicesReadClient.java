package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.contractdevicesread.ContractDevicesReadRequest;
import com.ericsson.contractdevicesread.ContractDevicesReadResponse;
import com.ericsson.contractdevicesread.ContractDevicesReadService;
import com.ericsson.contractdevicesread.ContractDevicesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractDevicesReadClient {
    private final static Logger log = LogManager.getLogger(ContractDevicesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public ContractDevicesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private ContractDevicesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractDevicesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CONTRACT_DEVICES_READ;
            service = new ContractDevicesReadService_Service(new URL(serviceUrl));

            log.debug("ContractDevicesReadService URL: {}", serviceUrl);
            log.info("Initialization of ContractDevicesReadClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractDevicesReadClient", e);
            throw new RuntimeException("Failed to initialize ContractDevicesReadClient",e);
        }
    }

    public ContractDevicesReadResponse execute(ContractDevicesReadRequest request, String username, String password) {
        log.info("Acquiring ContractDevicesReadService port...");
        if (username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        } else{
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        ContractDevicesReadService port = service.getContractDevicesReadServiceSoap11();
        log.debug("ContractDevicesReadService port acquired successfully.");
        log.info("Initiating ContractDevicesRead call for user: {}", username);
        ContractDevicesReadResponse response = port.contractDevicesRead(request);
        log.info("ContractDevicesRead affected successfully");
        return response;
    }
}
