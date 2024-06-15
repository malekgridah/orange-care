package com.billcom.payment.clients.soap.wsi;

import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.utils.WebServicesProperties;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadRequest;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadResponse;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadService;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadService_Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Component
public class FinancialDocumentDetailReadClient {

    private final static Logger log = LogManager.getLogger(FinancialDocumentDetailReadClient.class);

    @Resource(name = "webServicesProperties")
    private Properties properties;

    private FinancialDocumentDetailReadService_Service service;


    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialDocumentDetailReadClient...");
            String serviceUrl = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_DETAIL_READ_URL);
            service = new FinancialDocumentDetailReadService_Service(new URL(serviceUrl));
            log.debug("FinancialDocumentDetailReadService URL: {}", serviceUrl);
            log.info("Initialization of FinancialDocumentDetailReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialDocumentDetailReadClient", e);
            throw new RuntimeException("Failed to initialize FinancialDocumentDetailReadClient",e);
        }
    }

    public FinancialDocumentDetailReadResponse execute(FinancialDocumentDetailReadRequest request, String username, String password) {
        log.info("Acquiring FinancialDocumentDetailReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_DETAIL_READ_USER);
            password = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_DOCUMENT_DETAIL_READ_PASS);
            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialDocumentDetailReadService port = service.getFinancialDocumentDetailReadServiceSoap11();
        log.debug("FinancialDocumentDetailReadService port acquired successfully.");

        log.info("Initiating financialDocumentDetailRead call for user: {}", username);
        FinancialDocumentDetailReadResponse response = port.financialDocumentDetailRead(request);
        log.info("financialDocumentDetail retrieved successfully");
        return response;
    }
}

