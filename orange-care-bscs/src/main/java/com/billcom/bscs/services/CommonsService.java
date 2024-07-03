package com.billcom.bscs.services;

import com.billcom.bscs.clients.wsi.RateplansReadClient;
import com.ericsson.rateplansread.InputAttributes;
import com.ericsson.rateplansread.RateplansReadRequest;
import com.ericsson.rateplansread.RateplansReadResponse;
import org.springframework.stereotype.Service;

@Service
public class CommonsService {

    private final RateplansReadClient rateplansReadClient;

    public CommonsService(RateplansReadClient rateplansReadClient) {
        this.rateplansReadClient = rateplansReadClient;
    }


    public String getRateplan(Long rpCode) {
        RateplansReadRequest readRequest = new RateplansReadRequest();
        RateplansReadResponse readResponse = new RateplansReadResponse();
        InputAttributes inputAttributes = new InputAttributes();

        inputAttributes.setRpcode(rpCode);
        readRequest.setInputAttributes(inputAttributes);
        return this.rateplansReadClient.execute(readRequest, "ADMX", "ADMX")
                .getNumRp()
                .getItem()
                .get(0)
                .getRpDes();
    }
}
