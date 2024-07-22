package com.billcom.contracts.clients.wsi;

import com.billcom.contracts.clients.commons.ClientResolver;
import com.billcom.contracts.config.WebServicesProperties;
import com.billcom.contracts.utils.BaseWsWebService;
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

    private final WebServicesProperties wsProperties;

    @Autowired
    public ContractDevicesReadClient(WebServicesProperties wsProperties) {
        this.wsProperties = wsProperties;
    }

    private ContractDevicesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractDevicesReadClient...");

            String serviceUrl = wsProperties.getWsi().getUrl() + BaseWsWebService.CONTRACT_DEVICES_READ;
            service = new ContractDevicesReadService_Service(new URL(serviceUrl));

            log.debug("ContractDevicesReadService URL: {}", serviceUrl);
            log.info("Initialization of ContractDevicesReadClient is successful.");
        } catch(MalformedURLException e) {
            log.error("Failed to initialize ContractDevicesReadClient", e);
            throw new RuntimeException("Failed to initialize ContractDevicesReadClient",e);
        }
    }

    public ContractDevicesReadResponse execute(ContractDevicesReadRequest request) {
        return this.execute(request, null, null);
    }

    public ContractDevicesReadResponse execute(ContractDevicesReadRequest request, String username, String password) {
        log.info("Acquiring ContractDevicesReadService port...");
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
        ContractDevicesReadService port = service.getContractDevicesReadServiceSoap11();
        log.debug("ContractDevicesReadService port acquired successfully.");
        log.info("Initiating ContractDevicesRead call for user: {}", username);
        ContractDevicesReadResponse response = port.contractDevicesRead(request);
        log.info("ContractDevicesRead affected successfully");
        return response;
    }
}
