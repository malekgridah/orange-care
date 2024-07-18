package com.billcom.payment.clients.soap.wsi;


import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.config.properties.WebServicesProperties;
import com.ericsson.financialdocumentread.FinancialDocumentReadRequest;
import com.ericsson.financialdocumentread.FinancialDocumentReadResponse;
import com.ericsson.financialdocumentread.FinancialDocumentReadService;
import com.ericsson.financialdocumentread.FinancialDocumentReadService_Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Component
public class FinancialDocumentReadClient {

    private final static Logger log = LogManager.getLogger(FinancialDocumentReadClient.class);

    private final WebServicesProperties endpointProperties;

    @Autowired
    public FinancialDocumentReadClient(WebServicesProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    private FinancialDocumentReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialDocumentReadClient...");
            String serviceUrl = this.endpointProperties.getWsi()
                    .getFinancialDocument()
                    .getRead()
                    .getUrl();
            service = new FinancialDocumentReadService_Service(new URL(serviceUrl));
            log.debug("FinancialDocumentReadService URL: {}", serviceUrl);
            log.info("Initialization of FinancialDocumentReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialDocumentReadClient", e);
            throw new RuntimeException("Failed to initialize FinancialDocumentReadClient",e);
        }
    }

    public FinancialDocumentReadResponse execute(FinancialDocumentReadRequest request, String username, String password) {
        log.info("Acquiring FinancialDocumentReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = this.endpointProperties.getWsi()
                    .getFinancialDocument()
                    .getRead()
                    .getUsername();

            password = this.endpointProperties.getWsi()
                    .getFinancialDocument()
                    .getRead()
                    .getPassword();
            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialDocumentReadService port = service.getFinancialDocumentReadServiceSoap11();
        log.debug("FinancialDocumentReadService port acquired successfully.");

        log.info("Initiating financialDocumentRead call for user: {}", username);
        FinancialDocumentReadResponse response = port.financialDocumentRead(request);
        log.info("financialDocument retrieved successfully");
        return response;
    }
}

