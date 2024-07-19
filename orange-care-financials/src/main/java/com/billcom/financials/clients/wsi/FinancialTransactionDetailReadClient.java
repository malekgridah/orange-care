package com.billcom.financials.clients.wsi;

import com.billcom.financials.clients.commons.ClientResolver;
import com.billcom.financials.config.WebServicesProperties;
import com.ericsson.financialtransactiondetailread.FinancialTransactionDetailReadRequest;
import com.ericsson.financialtransactiondetailread.FinancialTransactionDetailReadResponse;
import com.ericsson.financialtransactiondetailread.FinancialTransactionDetailReadService;
import com.ericsson.financialtransactiondetailread.FinancialTransactionDetailReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class FinancialTransactionDetailReadClient {
    private final static Logger log = LogManager.getLogger(FinancialTransactionDetailReadClient.class);

    private final WebServicesProperties endpointProperties;

    @Autowired
    public FinancialTransactionDetailReadClient(WebServicesProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    private FinancialTransactionDetailReadService_Service service;


    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialTransactionDetailReadClient...");
            String serviceUrl = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getDetailRead()
                    .getUrl();

            service = new FinancialTransactionDetailReadService_Service(new URL(serviceUrl));
            log.debug("FinancialTransactionDetailReadService URL: {}", serviceUrl);
            log.info("Initialization of FinancialTransactionDetailReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialTransactionDetailReadClient", e);
            throw new RuntimeException("Failed to initialize FinancialTransactionDetailReadClient",e);
        }
    }

    public FinancialTransactionDetailReadResponse execute(FinancialTransactionDetailReadRequest request) {
        return this.execute(request,null,null);
    }

    public FinancialTransactionDetailReadResponse execute(FinancialTransactionDetailReadRequest request, String username, String password) {
        log.info("Acquiring FinancialTransactionDetailReadService port...");

        if (username == null || username.isEmpty()) {
            log.debug("User credentials are not provided or Invalid");
            username = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getDetailRead()
                    .getUsername();

            password = this.endpointProperties.getWsi()
                    .getFinancialTransaction()
                    .getDetailRead()
                    .getPassword();

            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        } else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialTransactionDetailReadService port = service.getFinancialTransactionDetailReadServiceSoap11();
        log.debug("FinancialTransactionDetailReadService port acquired successfully.");
        log.info("Initiating financialtransactiondetailread call for user: {}", username);
        FinancialTransactionDetailReadResponse response = port.financialTransactionDetailRead(request);
        log.info("financial transaction retrieved successfully");
        return response;
    }

}


