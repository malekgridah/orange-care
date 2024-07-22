package com.billcom.contracts.clients.wsi;

import com.billcom.contracts.clients.commons.ClientResolver;
import com.billcom.contracts.config.WebServicesProperties;
import com.billcom.contracts.utils.BaseWsWebService;
import com.ericsson.contractssearch.ContractsSearchRequest;
import com.ericsson.contractssearch.ContractsSearchResponse;
import com.ericsson.contractssearch.ContractsSearchService;
import com.ericsson.contractssearch.ContractsSearchService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContractsSearchClient {
    private final static Logger log = LogManager.getLogger(ContractsSearchClient.class);

    private final WebServicesProperties wsProperties;

    @Autowired
    public ContractsSearchClient(WebServicesProperties wsProperties) {
        this.wsProperties = wsProperties;
    }

    private ContractsSearchService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ContractsSearchClient...");

            String serviceUrl = wsProperties.getWsi().getUrl() + BaseWsWebService.CONTRACTS_SEARCH;
            service = new ContractsSearchService_Service(new URL(serviceUrl));

            log.debug("ContractsSearchService URL: {}", serviceUrl);
            log.info("Initialization of ContractsSearchClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize ContractsSearchClient", e);
            throw new RuntimeException("Failed to initialize ContractsSearchClient",e);
        }
    }

    public ContractsSearchResponse execute(ContractsSearchRequest request) {
        return this.execute(request, null, null);
    }

    public ContractsSearchResponse execute(ContractsSearchRequest request, String username, String password) {
        log.info("Acquiring ContractsSearchService port...");
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
        ContractsSearchService port = service.getContractsSearchServiceSoap11();
        log.debug("ContractsSearchService port acquired successfully.");
        log.info("Initiating ContractsSearch call for user: {}", username);
        ContractsSearchResponse response = port.contractsSearch(request);
        log.info("ContractsSearch affected successfully");
        return response;
    }
}
