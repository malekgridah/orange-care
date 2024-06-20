package com.billcom.payment.clients.soap.wsi;

import com.billcom.payment.clients.soap.commons.ClientResolver;
import com.billcom.payment.config.properties.WebServicesProperties;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseRequest;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseResponse;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseService;
import com.ericsson.financialallocationreverse.FinancialAllocationReverseService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class FinancialAllocationReverseClient {

    private final static Logger log = LogManager.getLogger(FinancialAllocationReverseClient.class);

    private final WebServicesProperties endpointProperties;

    @Autowired
    public FinancialAllocationReverseClient(WebServicesProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    private FinancialAllocationReverseService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing FinancialAllocationReverseClient...");

            String serviceUrl = this.endpointProperties.getWsi()
                    .getFinancialAllocation()
                    .getReverse()
                    .getUrl();

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
            username = this.endpointProperties.getWsi()
                    .getFinancialAllocation()
                    .getReverse()
                    .getUsername();

            password = this.endpointProperties.getWsi()
                    .getFinancialAllocation()
                    .getReverse()
                    .getPassword();
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
