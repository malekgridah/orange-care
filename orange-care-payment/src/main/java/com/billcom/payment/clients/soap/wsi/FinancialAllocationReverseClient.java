package com.billcom.payment.clients.soap.wsi;

import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.utils.WebServicesProperties;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseRequest;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseResponse;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseService;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseService_Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Component
public class FinancialAllocationReverseClient {

    private final static Logger log = LogManager.getLogger(FinancialAllocationReverseClient.class);

    @Resource(name = "webServicesProperties")
    private Properties properties;

    private FinancialAllocationReverseService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialAllocationReverseClient...");

            String serviceUrl = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_ALLOCATION_REVERSE_URL);
            service = new FinancialAllocationReverseService_Service(new URL(serviceUrl));

            log.debug("FinancialAllocationReverseService URL: {}", serviceUrl);
            log.info("Initialization of FinancialAllocationReverseClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialAllocationReverseClient", e);
            throw new RuntimeException("Failed to initialize FinancialAllocationReverseClient",e);
        }
    }

    public FinancialAllocationReverseResponse execute(FinancialAllocationReverseRequest request, String username, String password) {
        log.info("Acquiring FinancialAllocationReverseService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_ALLOCATION_REVERSE_USER);
            password = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_ALLOCATION_REVERSE_PASS);
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialAllocationReverseService port = service.getFinancialAllocationReverseServiceSoap11();
        log.debug("FinancialAllocationReverseService port acquired successfully.");
        log.info("Initiating FinancialAllocationReverse call for user: {}", username);
        FinancialAllocationReverseResponse response = port.financialAllocationReverse(request);
        log.info("AllocationReverse affected successfully");
        return response;
    }
}
