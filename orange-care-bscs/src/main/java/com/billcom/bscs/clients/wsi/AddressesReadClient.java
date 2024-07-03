package com.billcom.bscs.clients.wsi;

import com.billcom.bscs.clients.commons.ClientResolver;
import com.billcom.bscs.clients.config.BaseWsWebService;
import com.billcom.bscs.clients.config.WebServiceConfig;
import com.ericsson.addreessesread.AddressesReadRequest;
import com.ericsson.addreessesread.AddressesReadResponse;
import com.ericsson.addreessesread.AddressesReadService;
import com.ericsson.addreessesread.AddressesReadService_Service;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class AddressesReadClient {
    private final static Logger log = LogManager.getLogger(AddressesReadClient.class);

    private final WebServiceConfig webServiceConfig;

    @Autowired
    public AddressesReadClient(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    private AddressesReadService_Service service;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing AddressesReadClient...");

            String serviceUrl = webServiceConfig.getWsiUrl() + BaseWsWebService.ADDRESSES_READ;
            service = new AddressesReadService_Service(new URL(serviceUrl));

            log.debug("AddressesReadService URL: {}", serviceUrl);
            log.info("Initialization of AddressesReadClient is successful.");
        }catch (MalformedURLException e) {
            log.error("Failed to initialize AddressesReadClient", e);
            throw new RuntimeException("Failed to initialize AddressesReadClient",e);
        }
    }

    public AddressesReadResponse execute(AddressesReadRequest request, String username, String password) {
        log.info("Acquiring AddressesReadService port...");
        if(username == null || username.isEmpty()){
            log.debug("User credentials are not provided or Invalid");
            username = webServiceConfig.getWsiUser();
            password = webServiceConfig.getWsiPass();
            log.debug("Using default credentials from properties file - Username: {}, Password: {}", username, "****");
        }else {
            log.debug("Using provided user credentials - Username: {}", username);
        }
        service.setHandlerResolver(new ClientResolver(username,password));
        AddressesReadService port = service.getAddressesReadServiceSoap11();
        log.debug("AddressesReadService port acquired successfully.");
        log.info("Initiating AddressesRead call for user: {}", username);
        AddressesReadResponse response = port.addressesRead(request);
        log.info("AddressesRead affected successfully");
        return response;
    }
}
