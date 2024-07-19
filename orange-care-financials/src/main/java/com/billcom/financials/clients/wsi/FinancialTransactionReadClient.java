package com.billcom.financials.clients.wsi;

import com.billcom.financials.clients.commons.ClientResolver;
import com.billcom.financials.config.WebServicesProperties;
import com.ericsson.financialtransactionread.FinancialTransactionReadRequest;
import com.ericsson.financialtransactionread.FinancialTransactionReadResponse;
import com.ericsson.financialtransactionread.FinancialTransactionReadService;
import com.ericsson.financialtransactionread.FinancialTransactionReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class FinancialTransactionReadClient {
    private final static Logger log = LogManager.getLogger(FinancialTransactionReadClient.class);

    private final WebServicesProperties endpointProperties;

    @Autowired
    public FinancialTransactionReadClient(WebServicesProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    private FinancialTransactionReadService_Service service;


    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialTransactionReadClient...");
            String serviceUrl = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getRead()
                    .getUrl();

            service = new FinancialTransactionReadService_Service(new URL(serviceUrl));
            log.debug("FinancialTransactionReadService URL: {}", serviceUrl);
            log.info("Initialization of FinancialTransactionReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialTransactionReadClient", e);
            throw new RuntimeException("Failed to initialize FinancialTransactionReadClient",e);
        }
    }

    public FinancialTransactionReadResponse execute(FinancialTransactionReadRequest request) {
        return this.execute(request,null,null);
    }

    public FinancialTransactionReadResponse execute(FinancialTransactionReadRequest request, String username, String password) {
        log.info("Acquiring FinancialTransactionReadService port...");

        if (username == null || username.isEmpty()) {
            log.debug("User credentials are not provided or Invalid");
            username = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getRead()
                    .getUsername();

            password = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getRead()
                    .getPassword();

            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        } else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialTransactionReadService port = service.getFinancialTransactionReadServiceSoap11();
        log.debug("FinancialTransactionReadService port acquired successfully.");
        log.info("Initiating financialtransactionread call for user: {}", username);
        FinancialTransactionReadResponse response = port.financialTransactionRead(request);
        log.info("financial transaction retrieved successfully");
        return response;
    }

}


