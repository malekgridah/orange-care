package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.countriesread.CountriesReadRequest;
import com.ericsson.countriesread.CountriesReadResponse;
import com.ericsson.countriesread.CountriesReadService;
import com.ericsson.countriesread.CountriesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;


@Component
public class CountriesReadClient {
    private final static Logger log = LogManager.getLogger(CountriesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public CountriesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private CountriesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing CountriesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.COUNTRIES_READ;
            service = new CountriesReadService_Service(new URL(serviceUrl));

            log.debug("CountriesReadService URL: {}", serviceUrl);
            log.info("Initialization of CountriesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize CountriesReadClient", e);
            throw new RuntimeException("Failed to initialize CountriesReadClient",e);
        }
    }

    public CountriesReadResponse execute(CountriesReadRequest request, String username, String password) {
        log.info("Acquiring CountriesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        CountriesReadService port = service.getCountriesReadServiceSoap11();
        log.debug("CountriesReadService port acquired successfully.");
        log.info("Initiating CountriesRead call for user: {}", username);
        CountriesReadResponse response = port.countriesRead(request);
        log.info("CountriesRead affected successfully");
        return response;
    }
}
