package com.billcom.payment.clients.soap.sms;

import com.orange.dsi.ws.apinotificationsmsws.ApiNotificationSMS;
import com.orange.dsi.ws.apinotificationsmsws.ApiNotificationSMSWs;
import com.orange.dsi.ws.apinotificationsmsws.SendNotificationResponse;
import com.sun.xml.ws.client.BindingProviderProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.xml.ws.BindingProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static com.billcom.payment.utils.WebServicesProperties.API_NOTIFICATION_SMS_URL;

@Component
public class SMSNotifierClient {


    private static final Logger log = LogManager.getLogger(SMSNotifierClient.class);
    @Resource(name = "webServicesProperties")
    private Properties webServicesProperties;

    private ApiNotificationSMSWs apiNotificationSMSWs;

    @PostConstruct
    private void init() {
        try {
            log.info("Initializing ApiNotificationSMS WebService Client...");

            String webServiceEndPoint = webServicesProperties.getProperty(API_NOTIFICATION_SMS_URL);

            log.debug("ApiNotificationSMS WebService Endpoint URL: {}", webServiceEndPoint);
            apiNotificationSMSWs = new ApiNotificationSMSWs(new URL(webServiceEndPoint));

            log.info("Initialization of SMSNotifierClient with the ApiNotificationSMS WebService is successful.");
        } catch (MalformedURLException e) {
            log.error("Failed to initialize SMSNotifierClient", e);
            throw new RuntimeException("Failed to initialize SMSNotifierClient", e);
        }
    }

    public SendNotificationResponse sendSms(SendNotificationRequest request) {
        log.info("Acquiring ApiNotificationSMSWs port...");

        SendNotificationResponse sendNotificationResponse = new SendNotificationResponse();
        ApiNotificationSMS apiNotificationSMS = apiNotificationSMSWs.getApiNotificationSMSWSServiceIpmlPort();

        log.info("ApiNotificationSMSWs port acquired successfully.");

        log.debug("Setting timeout properties...");
        log.debug("- Request timeout: {} ms", 10000);
        log.debug("- Connect timeout: {} ms", 10000);
        ((BindingProvider) apiNotificationSMSWs).getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, 10000);
        ((BindingProvider) apiNotificationSMSWs).getRequestContext().put(BindingProviderProperties.CONNECT_TIMEOUT, 10000);
        log.info("Sending Notification...");
        log.info("- Type: {}", "SMS");
        log.info("- Canal: {}", request.getCanal());
        log.info("- Msisdn:  {}", request.getMsisdn());
        log.info("- Message: {}", request.getSmsText());
        apiNotificationSMS.sendNotification(request.getMsisdn(), "SMS", request.getSmsText(), "", request.getCanal());

        if(sendNotificationResponse.isIsSuccessful())
            log.info("SMS sent successfully.");

        return sendNotificationResponse;
    }
}
