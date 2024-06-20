package com.billcom.payment.clients.soap.wsi;

import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.config.properties.WebServicesProperties;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchRequest;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchResponse;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchService;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class FinancialDocumentSearchClient {

    private final static Logger log = LogManager.getLogger(FinancialDocumentSearchClient.class);

    private final WebServicesProperties endpointProperties;

    @Autowired
    public FinancialDocumentSearchClient(WebServicesProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    private FinancialDocumentSearchService_Service service;


    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialDocumentSearchClient...");
            String serviceUrl = this.endpointProperties.getWsi()
                    .getFinancialDocument()
                    .getSearch()
                    .getUrl();

            service = new FinancialDocumentSearchService_Service(new URL(serviceUrl));
            log.debug("FinancialDocumentSearchService URL: {}", serviceUrl);
            log.info("Initialization of FinancialDocumentSearchClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialDocumentSearchClient", e);
            throw new RuntimeException("Failed to initialize FinancialDocumentSearchClient",e);
        }
    }

    public FinancialDocumentSearchResponse execute(FinancialDocumentSearchRequest request) {

        String username = this.endpointProperties.getWsi()
                .getFinancialDocument()
                .getSearch()
                .getUsername();

        String password = this.endpointProperties.getWsi()
                .getFinancialDocument()
                .getSearch()
                .getPassword();

        return this.execute(request,username,password);
    }

    public FinancialDocumentSearchResponse execute(FinancialDocumentSearchRequest request, String username, String password) {
        log.info("Acquiring FinancialDocumentSearchService port...");

        if (username == null || username.isEmpty()) {
            log.debug("User credentials are not provided or Invalid");
            username = this.endpointProperties.getWsi()
                    .getFinancialDocument()
                    .getSearch()
                    .getUsername();

            password = this.endpointProperties.getWsi()
                    .getFinancialDocument()
                    .getSearch()
                    .getPassword();

            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        } else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialDocumentSearchService port = service.getFinancialDocumentSearchServiceSoap11();
        log.debug("FinancialDocumentSearchService port acquired successfully.");
        log.info("Initiating financialDocumentSearch call for user: {}", username);
        FinancialDocumentSearchResponse response = port.financialDocumentSearch(request);
        log.info("financialDocument retrieved successfully");
        return response;
    }

}
