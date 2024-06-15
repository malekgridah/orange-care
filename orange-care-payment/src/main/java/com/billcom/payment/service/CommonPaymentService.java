package com.billcom.payment.service;

import com.billcom.customer.handling.GetCustomerDetailsRequest;
import com.billcom.customer.handling.GetCustomerDetailsResponse;
import com.billcom.customer.handling.UnexpectedError;
import com.billcom.payment.clients.soap.customer.CustomerHandlingClient;
import com.billcom.payment.clients.soap.sms.SMSNotifierClient;
import com.billcom.payment.clients.soap.sms.SendNotificationRequest;
import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.dtos.postgres.FailedOperationDto;
import com.billcom.payment.commons.dtos.postgres.PayDto;
import com.billcom.payment.commons.dtos.postgres.SmsConfigDto;
import com.billcom.payment.commons.enums.UseCasesIdPub;
import com.billcom.payment.commons.exceptions.AtLeastMandatoryInputValueException;
import com.billcom.payment.commons.exceptions.InvalidInputValueException;
import com.billcom.payment.commons.exceptions.MandatoryInputParameterException;
import com.billcom.payment.commons.exceptions.OperationAlreadyDoneException;
import com.billcom.payment.commons.mappers.postgres.FailedOperationMapper;
import com.billcom.payment.commons.mappers.postgres.PayMapper;
import com.billcom.payment.commons.mappers.postgres.SmsConfigMapper;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceHistRepository;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceRepository;
import com.billcom.payment.commons.repositories.postgres.FailedOperationRepository;
import com.billcom.payment.commons.repositories.postgres.PayRepository;
import com.billcom.payment.commons.repositories.postgres.SmsConfigRepository;
import com.billcom.payment.utils.Constants;
import com.billcom.payment.utils.I18nErrorMessages;
import com.orange.dsi.ws.apinotificationsmsws.SendNotificationResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.text.MessageFormat;

@Service
public class CommonPaymentService {

    private static final Logger logger = LogManager.getLogger(CommonPaymentService.class);

    private final FinTrxInterfaceHistRepository interfaceHistRepository;
    private final FinTrxInterfaceRepository interfaceRepository;
    private final SMSNotifierClient smsNotifierClient;
    private final SmsConfigRepository smsConfigRepository;
    private final SmsConfigMapper smsConfigMapper;
    private final CustomerHandlingClient customerHandlingClient;
    private final PayRepository payRepository;
    private final PayMapper payMapper;
    private final FailedOperationMapper failedOperationMapper;
    private final FailedOperationRepository failedOperationRepository;

    @Autowired
    public CommonPaymentService(FinTrxInterfaceHistRepository interfaceHistRepository,
                                FinTrxInterfaceRepository interfaceRepository,
                                SMSNotifierClient smsNotifierClient,
                                SmsConfigRepository smsConfigRepository,
                                SmsConfigMapper smsConfigMapper,
                                CustomerHandlingClient customerHandlingClient,
                                PayRepository payRepository,
                                PayMapper payMapper,
                                FailedOperationMapper failedOperationMapper,
                                FailedOperationRepository failedOperationRepository) {
        this.interfaceHistRepository = interfaceHistRepository;
        this.interfaceRepository = interfaceRepository;
        this.smsNotifierClient = smsNotifierClient;
        this.smsConfigRepository = smsConfigRepository;
        this.smsConfigMapper = smsConfigMapper;
        this.customerHandlingClient = customerHandlingClient;
        this.payRepository = payRepository;
        this.payMapper = payMapper;
        this.failedOperationMapper = failedOperationMapper;
        this.failedOperationRepository = failedOperationRepository;
    }

    public void checkDocumentInput(Document document) {
        logger.info("Checking document input...");
        if(document.getDocumentId() == null && document.getDocumentCode() == null) {
            throw new AtLeastMandatoryInputValueException(I18nErrorMessages.ONE_ATTRIBUTE_MANDATORY, "document", "documentId", "documentCode");
        }

        if(document.getDocumentId() == null && document.getDocumentCode().isBlank()) {
            throw new InvalidInputValueException("documentCode");
        }
    }

    public void checkActionAlreadyDoneByDocId(Long documentId, String useCaseIdPub, String trsError) {
        if (documentId != null) {
            logger.info("Checking If Action: {}, is Already done for documentId: {}",useCaseIdPub,documentId);
            if (this.interfaceHistRepository.existsByDocumentIdAndAction(documentId, useCaseIdPub) ||
                    this.interfaceRepository.existsByDocumentIdAndAction(documentId, useCaseIdPub)) {
                logger.error("Operation already done for documentId: {}", documentId);
                throw new OperationAlreadyDoneException(trsError, documentId.toString());
            }
            logger.debug("Action not done yet for documentId: {}", documentId);
        }
    }

    public Boolean checkCustomerValidity(Customer customer){
        logger.debug("Checking customer validity...");
        return (customer !=null) && (customer.getCsId() == null) &&
                (customer.getCsIdPub() == null || customer.getCsIdPub().isBlank());
    }

    public String checkStringInputAndReturnValue(String parameter, String value) {
        if (value == null)
            throw new MandatoryInputParameterException(parameter);

        if (value.isBlank())
            throw  new InvalidInputValueException(parameter);

        return value;
    }

    public void checkStringInputValue(String parameter, String value) {
        logger.debug("Checking input parameter: {} - value: {}", parameter, value);
        if (value == null)
            throw new MandatoryInputParameterException(parameter);

        if (value.isBlank())
            throw  new InvalidInputValueException(parameter,value);

    }

    public void loadSMSConfig(String action, String payChannelIdPub, CustomerSMSDetails customerSMSDetails, Double amount, String documentCode) throws DatatypeConfigurationException {

        logger.info("Loading SMS configuration for \n- Action: {}\n -PayChannelIdPub: {}", action, payChannelIdPub);
        if (customerSMSDetails != null && customerSMSDetails.getMsisdn() != null && !customerSMSDetails.getMsisdn().isBlank()) {

            logger.debug("Customer MSISDN is present, proceeding with SMS configuration...");

            SmsConfigDto smsConfigDto;
            if(this.smsConfigRepository.existsByCanal(payChannelIdPub)) {
                smsConfigDto = this.smsConfigMapper.toDto(this.smsConfigRepository
                        .findFirstByCanalAndPrgcode(payChannelIdPub, customerSMSDetails.getPrgcode()));
                logger.debug("SMS configuration found for canal: {}", payChannelIdPub);
            }else {
                payChannelIdPub = "DEFAULT";
                smsConfigDto = this.smsConfigMapper.toDto(this.smsConfigRepository
                        .findFirstByCanalAndPrgcode("DEFAULT", customerSMSDetails.getPrgcode()));
                logger.debug("Default SMS configuration used.");
            }

            if(smsConfigDto != null) {
                if(smsConfigDto.getEnable().equals(BigDecimal.ONE)) {
                    logger.info("SMS configuration is enabled, preparing message...");
                    String formattedTxt = MessageFormat.format(action.equals(UseCasesIdPub.PAYMENT.getAction()) ? smsConfigDto.getText() : smsConfigDto.getTextCancelPay(),amount,documentCode,DatatypeFactory.newInstance().newXMLGregorianCalendar());

                    this.sendSMS(customerSMSDetails.getMsisdn(),payChannelIdPub,formattedTxt);
                }else {
                    logger.warn("SMS configuration is disabled or not found for canal: {}", payChannelIdPub);
                }
            } else {
                logger.warn("Customer details are incomplete or MSISDN is missing. SMS not sent.");
            }
        }
    }

    private void sendSMS(String msisdn, String canal, String smsText) {

        logger.info("Initiating SMS sending process...");
        logger.debug("Notification Details:");
        logger.debug("- Msisdn: {}",msisdn);
        logger.debug("- Canal: {}",canal);
        logger.debug("- Text: {}",smsText);

        SendNotificationResponse response;
        SendNotificationRequest request = new SendNotificationRequest();
        request.setMsisdn(msisdn);
        request.setCanal(canal);
        request.setSmsText(smsText);
        try {
            response = this.smsNotifierClient.sendSms(request);
            if (response != null) {
                logger.info("Notification sending status: {}", response.isIsSuccessful() ? "Successful" : "Failed");
            } else {
                logger.info("Notification sending status: Failed");
            }
        }catch (Exception exception) {
            logger.error("Exception occurred while sending sms", exception);
        }
    }

    public CustomerSMSDetails getCustomerSmsNumberAndPRGCode(Long csId, String csIdPub, String username, String password) {
        logger.info("Start getting customerDetails process...");

        CustomerSMSDetails customerSMSDetails = new CustomerSMSDetails();
        GetCustomerDetailsRequest customerDetailsRequest = new GetCustomerDetailsRequest();
        com.billcom.customer.handling.CustomerReference customerReference = new com.billcom.customer.handling.CustomerReference();
        customerReference.setCsId(csId);
        customerReference.setCsIdPub(csIdPub);
        customerDetailsRequest.setCustomerReference(customerReference);
        logger.debug("Initiating getCustomerDetails with the following parameters:");
        logger.debug("- csId: {}",csId);
        logger.debug("- csIdPub: {}",csIdPub);

        try {
            GetCustomerDetailsResponse customerDetailsResponse = this.customerHandlingClient
                    .getCustomerDetails(customerDetailsRequest,username,password);

            if(customerDetailsResponse.getCustomer() != null && customerDetailsResponse.getCustomer() != null) {
                logger.info("Customer with ID: {} retrieved successfully", csId);
                com.billcom.customer.handling.Customer customer = customerDetailsResponse.getCustomer();
                logger.debug("Customer Details:");

                if (customer.getAddress() != null && customer.getAddress().getAdrSmsno() != null) {
                    customerSMSDetails.setMsisdn(customerDetailsResponse.getCustomer().getAddress().getAdrSmsno());
                    logger.debug("- Customer Msisdn: {}", customerSMSDetails.getMsisdn());
                }

                if(customer.getPrgCode() != null && !customer.getPrgCode().isBlank()) {
                    logger.debug("- Customer Prgcode: {}", customerSMSDetails.getPrgcode());
                    customerSMSDetails.setPrgcode(customer.getPrgCode());
                }
                return customerSMSDetails;
            }
            logger.warn("No customer found for csId: {}", csId);
            return null;
        } catch (UnexpectedError e) {
            ///to do
            throw new RuntimeException(e);
        }
    }

    public void savePaymentOperation(PayDto payDto) {
        logger.info("Attempting to save payment operation for invoice: {}", payDto.getRtCachknum());
        try {
            this.payRepository.save(this.payMapper.toEntity(payDto));
            logger.info("Payment operation for invoice {} saved successfully.", payDto.getRtCachknum());
        } catch (Exception e) {
            logger.error("Failed to save payment operation for invoice {}: {}", payDto.getRtCachknum(), e.getMessage());
            this.saveFailedOperation(payDto);
            throw new RuntimeException(e);
        }
    }

    public void saveFailedOperation(PayDto payDto) {
        logger.info("Starting to save failed operation...");

        FailedOperationDto.FailedOperationDtoBuilder failedOperationDtoBuilder = FailedOperationDto.builder()
                .type(payDto.getOperationType())
                .status(Constants.FAILURE)
                .entryDate(payDto.getEntryDate())
                .invoiceReference(payDto.getRtCachknum());

        logger.debug("Failed operation details:\n- Type: {}\n- Status: {}\n Entry Date: {}\n- Invoice Reference: {}",
                payDto.getOperationType(), Constants.FAILURE, payDto.getEntryDate(), payDto.getRtCachknum());

        try {
            this.failedOperationRepository.save(this.failedOperationMapper.toEntity(failedOperationDtoBuilder.build()));
            logger.info("Failed operation for invoice {} saved successfully.", payDto.getRtCachknum());
        } catch (Exception e) {
            logger.error("Failed to save operation for invoice {}: {}", payDto.getRtCachknum(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
