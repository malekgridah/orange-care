package com.billcom.payment.clients.soap.wsi;

import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.utils.WebServicesProperties;
import com.ericsson.financialallocationwrite.FinancialAllocationWriteRequest;
import com.ericsson.financialallocationwrite.FinancialAllocationWriteResponse;
import com.ericsson.financialallocationwrite.FinancialAllocationWriteService;
import com.ericsson.financialallocationwrite.FinancialAllocationWriteService_Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Component
public class FinancialAllocationWriteClient {

    private final static Logger log = LogManager.getLogger(FinancialAllocationWriteClient.class);

    @Resource(name = "webServicesProperties")
    private Properties properties;

    private FinancialAllocationWriteService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialAllocationWriteClient...");
            String serviceUrl = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_ALLOCATION_WRITE_URL);
            service = new FinancialAllocationWriteService_Service(new URL(serviceUrl));
            log.debug("FinancialAllocationWriteService URL: {}", serviceUrl);
            log.info("Initialization of FinancialAllocationWriteClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize FinancialAllocationWriteClient", e);
            throw new RuntimeException("Failed to initialize FinancialAllocationWriteClient",e);
        }
    }


    public FinancialAllocationWriteResponse execute(FinancialAllocationWriteRequest request, String username, String password) {
        log.info("Acquiring FinancialAllocationWriteService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_ALLOCATION_WRITE_USER);
            password = properties.getProperty(WebServicesProperties.WSI_FINANCIAL_ALLOCATION_WRITE_PASS);
            log.debug("Using default credentials from configuration files - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }

        service.setHandlerResolver(new ClientResolver(username,password));
        FinancialAllocationWriteService port = service.getFinancialAllocationWriteServiceSoap11();
        log.debug("FinancialAllocationWriteService port acquired successfully.");
        log.info("Initiating FinancialAllocationWrite call for user: {}", username);
        FinancialAllocationWriteResponse writeResponse = port.financialAllocationWrite(request);
        log.info("AllocationWrite affected successfully");
        return writeResponse;
    }
}
