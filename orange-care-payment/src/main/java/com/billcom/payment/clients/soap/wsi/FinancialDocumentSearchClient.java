package com.billcom.payment.clients.soap.wsi;

import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.utils.WebServicesProperties;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchRequest;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchResponse;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchService;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchService_Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Component
public class FinancialDocumentSearchClient {

    private final static Logger log = LogManager.getLogger(FinancialDocumentSearchClient.class);

    @Resource(name = "webServicesProperties")
    private Properties properties;

    private FinancialDocumentSearchService_Service service;


    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialDocumentSearchClient...");
            String serviceUrl = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_SEARCH_URL);
            service = new FinancialDocumentSearchService_Service(new URL(serviceUrl));
            log.debug("FinancialDocumentSearchService URL: {}", serviceUrl);
            log.info("Initialization of FinancialDocumentSearchClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialDocumentSearchClient", e);
            throw new RuntimeException("Failed to initialize FinancialDocumentSearchClient",e);
        }
    }

    public FinancialDocumentSearchResponse execute(FinancialDocumentSearchRequest request) {

        String username = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_SEARCH_USER);
        String password = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_SEARCH_PASS);
        return this.execute(request,username,password);
    }

    public FinancialDocumentSearchResponse execute(FinancialDocumentSearchRequest request, String username, String password) {
        log.info("Acquiring FinancialDocumentSearchService port...");

        if (username == null || username.isEmpty()) {
            log.debug("User credentials are not provided or Invalid");
            username = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_DETAIL_READ_USER);
            password = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_DETAIL_READ_PASS);
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
