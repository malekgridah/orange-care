package com.billcom.payment.clients.rest;

import com.billcom.payment.commons.beans.CustomerDetails;
import com.billcom.payment.commons.bscs.RestResponse;
import com.billcom.payment.utils.PaymentApiSettingProperties;
import com.billcom.payment.utils.WebServicesProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class RestExecutorClient {

    private static final Logger logger = LogManager.getLogger(RestExecutorClient.class);

    @Resource(name = "webServicesProperties")
    private Properties webServiceProperties;

    @Resource(name = "appSettingsProperties")
    private Properties appSettingProperties;

    private final RestWSClient restWSClient;

    @Autowired
    public RestExecutorClient(RestWSClient restWSClient) {
        this.restWSClient = restWSClient;
    }

    @PostConstruct
    private void init() {

        String restExecutorUrlLogin =webServiceProperties.getProperty(WebServicesProperties.REST_EXECUTOR_USER);
        String restExecutorUrlPassword =webServiceProperties.getProperty(WebServicesProperties.REST_EXECUTOR_PASS);

        restWSClient.setUserName(restExecutorUrlLogin);
        restWSClient.setPassword(restExecutorUrlPassword);

    }

    public CustomerDetails getCustomerDetails(Long csId, String csIdPub, String cin, String regNo) {
        String restExecutorUrlQueryId = appSettingProperties.getProperty(PaymentApiSettingProperties.REST_EXECUTOR_CUSTOMER_QUERY_ID);
        String restExecutorUrl = webServiceProperties.getProperty(WebServicesProperties.REST_EXECUTOR_URL);
        restWSClient.setWsUrl(restExecutorUrl + restExecutorUrlQueryId);
        CustomerDetails det = new CustomerDetails();
        try {
            Map<String, String> mapRequest = new HashMap<>();

            if (csId != null) {
                mapRequest.put("customerId", csId.toString());
            }

            if (csIdPub != null) {
                mapRequest.put("custNum", csIdPub);
            }

            if (cin != null) {
                mapRequest.put("cin", cin);
            }

            if (regNo != null) {
                mapRequest.put("regNo", regNo);
            }

            RestResponse response = restWSClient.callRestWebService(mapRequest);

            if (response != null) {
                logger.info(
                        "RestExecutor response status: " + response.isSuccessful() + "comment = " + response.getComment());
                System.out.println(
                        "RestExecutor response status: " + response.isSuccessful() + "comment = " + response.getComment());
                if (response.isSuccessful() && !CollectionUtils.isEmpty(response.getRows())) {
                    String prgcode;
                    if (response.getRows().size() == 1) {
                        Long customerId = Long.parseLong(response.getRows().get(0).get("CUSTOMER_ID").toString());
                        prgcode = response.getRows().get(0).get("PRGCODE").toString();
                        det = new CustomerDetails();
                        det.setCustomerId(customerId);
                        det.setPrgcode(prgcode);
                        logger.info("customerId / prgcode " + customerId + " / " + prgcode);
                        System.out.println("customerId / prgcode " + customerId + " / " + prgcode);
                    } else {
                        List<Long> ids = new ArrayList<>();
//                        List<CustomerPrgCode> customerPrgCodes = new ArrayList<>();
//                        CustomerPrgCode customerPrgCode = new CustomerPrgCode();
                        for (Map<String, Object> rowValue : response.getRows()) {
                            ids.add(Long.parseLong(rowValue.get("CUSTOMER_ID").toString()));
//                            customerPrgCode.setCustomerId(Long.parseLong(rowValue.get("CUSTOMER_ID").toString()));
//                            customerPrgCode.setPrgCode(rowValue.get("PRGCODE").toString());
//                            customerPrgCodes.add(customerPrgCode);
                        }
                        prgcode = response.getRows().get(0).get("PRGCODE").toString();
                        det.setCustomerIds(ids);
                        det.setPrgcode(prgcode);
//                        det.setCustomerPrgCodes(customerPrgCodes);
                        logger.info("customerIds" + ids);
                        logger.info("prgcode " + prgcode);
                    }
                    return det;
                }
                logger.info("RestExecutor response emptyRows : " + response.getComment());
            }
        } catch (Exception e) {
            logger.error("getCsIdFromMsisdn - Internal error occurred ", e);
        }
        return det;
    }

    public Long getCsIdFromMsisdn(String msisdn) {
        String restExecutorUrlQueryId = appSettingProperties.getProperty(PaymentApiSettingProperties.REST_EXECUTOR_QUERY_ID);
        String restExecutorUrl = webServiceProperties.getProperty(WebServicesProperties.REST_EXECUTOR_URL);
        restWSClient.setWsUrl(restExecutorUrl + restExecutorUrlQueryId);
        Long csId = null;
        try {
            Map<String, String> mapRequest = new HashMap<>();
            mapRequest.put("MSISDN", msisdn);
            RestResponse response = restWSClient.callRestWebService(mapRequest);

            if (response != null) {
                logger.info("RestExecutor response status: " + response.isSuccessful());
                if (response.isSuccessful() && !CollectionUtils.isEmpty(response.getRows())) {
                    return csId = Long.parseLong(response.getRows().get(0).get("CS_ID").toString());
                }
                logger.info("RestExecutor response emptyRows : " + response.getComment());

            }
        } catch (Exception e) {
            logger.error("getCsIdFromMsisdn - Internal error occurred ", e);
        }
        return csId;
    }

}
