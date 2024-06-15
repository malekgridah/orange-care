package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.currenciesread.CurrenciesReadRequest;
import com.ericsson.currenciesread.CurrenciesReadResponse;
import com.ericsson.currenciesread.CurrenciesReadService;
import com.ericsson.currenciesread.CurrenciesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class CurrenciesReadClient {
    private final static Logger log = LogManager.getLogger(CurrenciesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public CurrenciesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private CurrenciesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing CurrenciesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.CURRENCIES_READ;
            service = new CurrenciesReadService_Service(new URL(serviceUrl));

            log.debug("CurrenciesReadService URL: {}", serviceUrl);
            log.info("Initialization of CurrenciesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize CurrenciesReadClient", e);
            throw new RuntimeException("Failed to initialize CurrenciesReadClient",e);
        }
    }

    public CurrenciesReadResponse execute(CurrenciesReadRequest request, String username, String password) {
        log.info("Acquiring CurrenciesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        CurrenciesReadService port = service.getCurrenciesReadServiceSoap11();
        log.debug("CurrenciesReadService port acquired successfully.");
        log.info("Initiating CurrenciesRead call for user: {}", username);
        CurrenciesReadResponse response = port.currenciesRead(request);
        log.info("CurrenciesRead affected successfully");
        return response;
    }
}
