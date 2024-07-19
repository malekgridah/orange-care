package com.billcom.financials.clients.wsi;

import com.billcom.financials.clients.commons.ClientResolver;
import com.billcom.financials.config.WebServicesProperties;
import com.ericsson.financialtransactionsearch.FinancialTransactionSearchRequest;
import com.ericsson.financialtransactionsearch.FinancialTransactionSearchResponse;
import com.ericsson.financialtransactionsearch.FinancialTransactionSearchService;
import com.ericsson.financialtransactionsearch.FinancialTransactionSearchService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class FinancialTransactionSearchClient {
    private final static Logger log = LogManager.getLogger(FinancialTransactionSearchClient.class);

    private final WebServicesProperties endpointProperties;

    @Autowired
    public FinancialTransactionSearchClient(WebServicesProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    private FinancialTransactionSearchService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialTransactionSearchClient...");
            String serviceUrl = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getSearch()
                    .getUrl();

            service = new FinancialTransactionSearchService_Service(new URL(serviceUrl));
            log.debug("FinancialTransactionSearchService URL: {}", serviceUrl);
            log.info("Initialization of FinancialTransactionSearchClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialTransactionSearchClient", e);
            throw new RuntimeException("Failed to initialize FinancialTransactionSearchClient",e);
        }
    }

    public FinancialTransactionSearchResponse execute(FinancialTransactionSearchRequest request) {
        return this.execute(request,null,null);
    }

    public FinancialTransactionSearchResponse execute(FinancialTransactionSearchRequest request, String username, String password) {
        log.info("Acquiring FinancialTransactionSearchService port...");

        if (username == null || username.isEmpty()) {
            log.debug("User credentials are not provided or Invalid");
            username = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getSearch()
                    .getUsername();

            password = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getSearch()
                    .getPassword();

            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        } else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialTransactionSearchService port = service.getFinancialTransactionSearchServiceSoap11();
        log.debug("FinancialTransactionSearchService port acquired successfully.");
        log.info("Initiating financialTransactionSearch call for user: {}", username);
        FinancialTransactionSearchResponse response = port.financialTransactionSearch(request);
        log.info("financial transaction retrieved successfully");
        return response;
    }

}

