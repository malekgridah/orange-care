package com.billcom.payment.service;

import com.billcom.payment.clients.soap.wsi.FinancialAllocationWriteClient;
import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.domains.bscs.FinTrxInterfaceHist;
import com.billcom.payment.commons.dtos.bscs.FinTrxInterfaceCommonDto;
import com.billcom.payment.commons.dtos.postgres.PayDto;
import com.billcom.payment.commons.enums.PaymentChannels;
import com.billcom.payment.commons.enums.UseCasesIdPub;
import com.billcom.payment.commons.exceptions.*;
import com.billcom.payment.commons.mappers.bscs.FinTrxCommonMapper;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceHistRepository;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceRepository;
import com.billcom.payment.utils.Constants;
import com.billcom.payment.utils.I18nErrorMessages;
import com.billcom.payment.utils.PaymentApiSettingProperties;
import com.ericsson.financialallocationwrite.*;
import com.ericsson.financialallocationwrite.Money;
import jakarta.annotation.Resource;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Service
public class PayService {
    private static final Logger logger = LogManager.getLogger(PayService.class);

    private static final Boolean SUPPRESS_ALLOC = true;

    @Resource(name = "appSettingsProperties")
    private Properties properties;

    private final FinTrxInterfaceHistRepository finTrxInterfaceHistRepository;
    private final FinTrxInterfaceRepository finTrxInterfaceRepository;
    private final FinancialAllocationWriteClient finAllWriteClient;
    private final CommonPaymentService commonPaymentService;
    private final FinTrxCommonMapper finTrxCommonMapper;

    @Autowired
    public PayService(FinTrxInterfaceHistRepository finTrxInterfaceHistRepository,
                      FinTrxInterfaceRepository finTrxInterfaceRepository,
                      FinancialAllocationWriteClient finAllWriteClient,
                      CommonPaymentService commonPaymentService,
                      FinTrxCommonMapper finTrxCommonMapper) {
        this.finTrxInterfaceHistRepository = finTrxInterfaceHistRepository;
        this.finTrxInterfaceRepository = finTrxInterfaceRepository;
        this.commonPaymentService = commonPaymentService;
        this.finTrxCommonMapper = finTrxCommonMapper;
        this.finAllWriteClient = finAllWriteClient;
    }

    public PayResponse pay(String username, String password, PayRequest payRequest) {

        logger.info("Begin pay Operation");
        PayResponse payResponse = new PayResponse();

        this.checkPayRequest(payRequest);

        Long request_id = null;

        try {

            FinancialAllocationWriteRequest request = new FinancialAllocationWriteRequest();
            InputAttributes inputAttributes = new InputAttributes();
            FinancialAllocationWriteInputDTO inputDTO = allocationWriteInputDTO(payRequest.getPayBean());

            inputAttributes.setFinancialAllocationWriteInputDTO(inputDTO);
            request.setInputAttributes(inputAttributes);

            request_id = this.saveOperation(payRequest.getPayBean());

            List<TransactionWriteOutDTO> transactionWriteOutDTOList = finAllWriteClient.execute(request,username,password)
                    .getFinancialAllocationWriteOutputDTO()
                    .getTransactions()
                    .getTransactionWriteOutDTO();

            TransactionWriteOutDTO transactionWriteOutDTO = transactionWriteOutDTOList.get(0);

            if(transactionWriteOutDTO.getTransactionId() != null) {
                this.finTrxInterfaceRepository.updatePaymentSuccessByRequestId(LocalDateTime.now(),
                        transactionWriteOutDTO.getTransactionId(),
                        properties.getProperty(PaymentApiSettingProperties.SUCCESS_TRANSACTION),
                        request_id);

                FinTrxInterfaceCommonDto finTrxInterface = this.finTrxCommonMapper.toDto(this.finTrxInterfaceRepository
                        .findById(request_id)
                        .orElseThrow(() -> new RuntimeException("")));

                this.finTrxInterfaceRepository.deleteById(request_id);

                FinTrxInterfaceHist finTrxInterfaceHist = this.finTrxCommonMapper.toHistEntity(finTrxInterface);
                finTrxInterfaceHist.setHistDate(LocalDateTime.now());
                finTrxInterfaceHist.setId(request_id);
                finTrxInterfaceHist.setCsIdPub(transactionWriteOutDTO.getCustomerIdPub());
                finTrxInterfaceHist.setCsId(transactionWriteOutDTO.getCustomerId());
                finTrxInterfaceHist.setBaId(transactionWriteOutDTO.getDocuments().getDocumentWriteOutDTO().get(0).getBillingAccountId());
                finTrxInterfaceHist.setBaIdPub(transactionWriteOutDTO.getDocuments().getDocumentWriteOutDTO().get(0).getBillingAccountIdPub());
                this.finTrxInterfaceHistRepository.save(finTrxInterfaceHist);

                PayDto.PayDtoBuilder payDto = PayDto.builder();
                payDto.canal(payRequest.getPayBean().getPayChannelIdPub())
                        .csId(transactionWriteOutDTO.getCustomerId())
                        .csIdPub(transactionWriteOutDTO.getCustomerIdPub())
                        .paymentMode(payRequest.getPayBean().getPayMethodIdPub())
                        .trsId(transactionWriteOutDTO.getTransactionId())
                        .operationType(Constants.PAY)
                        .operationState(Constants.SUCCESS)
                        .entryDate(LocalDateTime.now())
                        .transxCode(payRequest.getPayBean().getUseCaseIdPub())
                        .glaCash(payRequest.getPayBean().getGlAccountCash())
                        .glaDis(payRequest.getPayBean().getGlAccountDis())
                        .rtCarem(payRequest.getPayBean().getRemark())
                        .rtCauserName(payRequest.getPayBean().getUsernameGPS())
                        .validThroughDate(payRequest.getPayBean().getValidThruDate())
                        .bankName(payRequest.getPayBean().getBankName())
                        .cspAccNo(payRequest.getPayBean().getAccountNo())
                        .cspAccOwner(payRequest.getPayBean().getAccountOwner())
                        .btOhxact(transactionWriteOutDTO.getDocuments().getDocumentWriteOutDTO().get(0).getDocumentId())
                        .rtCachknum(transactionWriteOutDTO.getDocuments().getDocumentWriteOutDTO().get(0).getDocumentCode());

                this.commonPaymentService.savePaymentOperation(payDto.build());

                CustomerSMSDetails customerSMSDetails= this.commonPaymentService.getCustomerSmsNumberAndPRGCode(transactionWriteOutDTO.getCustomerId(),
                        transactionWriteOutDTO.getCustomerIdPub(),username,password);

                this.commonPaymentService.loadSMSConfig(UseCasesIdPub.PAYMENT.getAction(), payRequest.getPayBean().getPayChannelIdPub(),
                        customerSMSDetails,transactionWriteOutDTO.getAmount().getAmount(),
                        transactionWriteOutDTO.getDocuments().getDocumentWriteOutDTO().get(0).getDocumentCode());
            }

            payResponse.setIsSuccessful(Boolean.TRUE);
            payResponse.setComment("Transaction is "+transactionWriteOutDTO.getTransactionId());

        }catch (SOAPFaultException faultException) {
            logger.error(faultException);
            this.finTrxInterfaceRepository.updatePaymentFailedById(LocalDateTime.now(),
                    properties.getProperty(PaymentApiSettingProperties.FAILED_TRANSACTION),
                    faultException.getFault().getFaultString(),
                    request_id
                    );
            PayDto.PayDtoBuilder payDto = PayDto.builder();
            payDto.btOhxact(payRequest.getPayBean().getDocument() != null ?payRequest.getPayBean().getDocument().getDocumentId() : null)
                    .rtCachknum(payRequest.getPayBean().getDocument().getDocumentCode())
                    .operationType(Constants.PAY)
                    .csId(payRequest.getPayBean().getCustomer().getCsId())
                    .csIdPub(payRequest.getPayBean().getCustomer().getCsIdPub())
                    .entryDate(LocalDateTime.now())
                    .operationState(Constants.FAILURE)
                    .paymentMode(payRequest.getPayBean().getPayMethodIdPub())
                    .transxCode(payRequest.getPayBean().getUseCaseIdPub())
                    .canal(payRequest.getPayBean().getPayChannelIdPub())
                    .trsId(payRequest.getPayBean().getTrsId())
                    .cspAccOwner(payRequest.getPayBean().getAccountOwner())
                    .validThroughDate(payRequest.getPayBean().getValidThruDate())
                    .rtCauserName(payRequest.getPayBean().getUsernameGPS())
                    .transxCode(payRequest.getPayBean().getUseCaseIdPub())
                    .glaDis(payRequest.getPayBean().getGlAccountDis())
                    .glaCash(payRequest.getPayBean().getGlAccountCash())
                    .cspAccNo(payRequest.getPayBean().getAccountNo())
                    .rtCarem(payRequest.getPayBean().getRemark());

            this.commonPaymentService.savePaymentOperation(payDto.build());

            throw new InvokeClientException(faultException.getFault(), faultException);
        } catch (DataAccessException exception) {
            logger.error(exception.getMessage());
            payResponse.setComment("failed saving");
            payResponse.setIsSuccessful(false);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        return payResponse;
    }

    private void checkPayRequest(PayRequest payRequest) {
        logger.info("Checking required fields for payment request...");

        if (payRequest == null || payRequest.getPayBean() == null){
            logger.error("payRequest is null");
            throw new BadRequestException("payRequest");
        }

        PayBean payBean = payRequest.getPayBean();

        this.checkPaymentChannel(payRequest.getPayBean().getPayChannelIdPub());
        this.checkCustomer(payBean);

        // PaymentRefund
        if (payBean.getDocument() == null && payBean.getUseCaseIdPub().equals(UseCasesIdPub.PAYMENT_REFUND.getValue()))
            throw new MandatoryInputObjectException("document");


        if (payBean.getDocument() != null && payBean.getUseCaseIdPub().equals(UseCasesIdPub.PAYMENT_REFUND.getValue()))
            this.commonPaymentService.checkDocumentInput(payBean.getDocument());


        if (payBean.getOperation().equals("REST") && payBean.getPayChannelIdPub().equals(PaymentChannels.FRANCHISE.toString()) ||
                payBean.getPayChannelIdPub().equals(PaymentChannels.BOUTIQUE.toString())) {
            this.checkGPSRequest(payBean);
        }

        // this is in paymentRefund
        if (payBean.getUseCaseIdPub().equals(UseCasesIdPub.PAYMENT_REFUND.getValue())) {
            if (payBean.getTrsId() == null) {
                throw new MandatoryInputParameterException("trsId");
            }
        }

        //to do when poste
        if (payBean.getPayChannelIdPub().equals((PaymentChannels.POSTE.toString())) && payBean.getReferenceDate() == null) {
            throw new MandatoryInputParameterException("ReferenceDate");
        }

        this.commonPaymentService.checkActionAlreadyDoneByDocId(payRequest.getPayBean().getDocument().getDocumentId(),
                UseCasesIdPub.PAYMENT.getAction(),I18nErrorMessages.DOCUMENT_ALREADY_PAID);
    }

    private void checkGPSRequest(PayBean payBean) {

        logger.info("checking GPS parameters...");
        if(payBean.getEntityCode() == null)
            throw new MandatoryInputParameterException("entityCode");

        this.commonPaymentService.checkStringInputValue("username GPS",payBean.getUsernameGPS());
    }

    private Money checkAmount(com.billcom.payment.commons.beans.Money amount){
        logger.info("Checking amount...");
        if (amount == null)
            throw new MandatoryInputObjectException("amount");

        if (amount.getAmount() == null)
            throw new MandatoryInputParameterException("amount");

        if (amount.getAmount().isNaN())
            throw new InvalidInputValueException("amount");

        this.checkCurrency(amount.getCurrency());

        Money money = new Money();
        money.setAmount(amount.getAmount());
        money.setCurrency(amount.getCurrency());
        return money;
    }


    private void checkCurrency(String currency) {
        logger.debug("Checking input parameter: {} - value: {}", "currency", currency);
        if (currency == null)
            throw new MandatoryInputParameterException("currency");

        if (currency.isBlank())
            throw  new InvalidInputValueException("currency", currency);

        if (currency.length() > 3 )
            throw  new InvalidInputValueException("currency", currency);

    }

    private void checkCustomer(PayBean payBean) {
        logger.info("checking customer parameters...");
        if (!PaymentChannels.ORG_MONEY.toString().equals(payBean.getPayChannelIdPub().toUpperCase()) &&
                !PaymentChannels.ESHOP.toString().equals(payBean.getPayChannelIdPub().toUpperCase()) &&
                !PaymentChannels.DCE.toString().equals(payBean.getPayChannelIdPub().toUpperCase()) &&
                !PaymentChannels.DFI.toString().equals(payBean.getPayChannelIdPub().toUpperCase()))

            if ((payBean.getCustomer() == null))
                throw new MandatoryInputObjectException("customer");

            else if (this.commonPaymentService.checkCustomerValidity(payBean.getCustomer()))
                throw new AtLeastMandatoryInputValueException(I18nErrorMessages.ONE_ATTRIBUTE_MANDATORY,"customer", "csId", "csIdPub");
    }

    private void checkPaymentChannel(String paymentChannel) {
        logger.info("checking paymentChannel...");
        if (paymentChannel == null)
            throw new MandatoryInputParameterException("paymentChannel");

        if(paymentChannel.isBlank())
            throw new InvalidInputValueException("paymentChannel");

        try {
            PaymentChannels.valueOf(paymentChannel);
            logger.info("Payment channel '{}' is valid.", paymentChannel);
        }catch (IllegalArgumentException e) {
            logger.error(e);
            throw new InvalidInputValueException("paymentChannel",e);
        }
    }

    private FinancialAllocationWriteInputDTO allocationWriteInputDTO(PayBean payBean) throws DatatypeConfigurationException {
        FinancialAllocationWriteInputDTO writeInputDTO = new FinancialAllocationWriteInputDTO();
        ObjectFactory finAllocWriteObjectFact = new ObjectFactory();
        ReferenceDTO referenceDTO = new ReferenceDTO();
        referenceDTO.setPublicKey(payBean.getUseCaseIdPub());

        writeInputDTO.getContent()
                .add(finAllocWriteObjectFact.createFinancialAllocationWriteInputDTOUseCase(referenceDTO));

        writeInputDTO.getContent()
                .add(finAllocWriteObjectFact.createFinancialAllocationWriteInputDTOSuppressAlloc(SUPPRESS_ALLOC));
        TransactionListRequest transactionListRequest = new TransactionListRequest();

        transactionListRequest.getTransactionWriteInDTO()
                .add(this.transactionWriteInDTO(payBean));

        writeInputDTO.getContent()
                .add(finAllocWriteObjectFact.createFinancialAllocationWriteInputDTOTransactions(transactionListRequest));
        return writeInputDTO;
    }

    private TransactionWriteInDTO transactionWriteInDTO(PayBean payBean) throws DatatypeConfigurationException {
        ObjectFactory finAllocWriteObjectFact = new ObjectFactory();

        TransactionWriteInDTO transactionWriteInDTO = new TransactionWriteInDTO();

        logger.debug("------------------------- Transaction Details -------------------------");
        logger.debug("payMethodIdPub = {}",payBean.getPayMethodIdPub());
        logger.debug("documentId = {}",payBean.getDocument().getDocumentId());
        logger.debug("documentCode = {}",payBean.getDocument().getDocumentCode());
        logger.debug("amount = {}",payBean.getAmount().getAmount());
        logger.debug("------------------------------------------------------------------------");

        ReferenceDTO referenceDTO = new ReferenceDTO();
        if (payBean.getCustomer() != null) {
            logger.debug("customer");

            if(payBean.getCustomer().getCsIdPub() != null && !payBean.getCustomer().getCsIdPub().isBlank()) {
                logger.debug("csIdPub = {}",payBean.getCustomer().getCsIdPub());
                referenceDTO.setPublicKey(payBean.getCustomer().getCsIdPub());
            }

            if (payBean.getCustomer().getCsId() != null) {
                logger.debug("csId = {}", payBean.getCustomer().getCsId());
                referenceDTO.setPrivateKey(payBean.getCustomer().getCsId());
            }
            transactionWriteInDTO.getContent()
                    .add(finAllocWriteObjectFact.createTransactionWriteInDTOCustomer(referenceDTO));
        }


        referenceDTO = new ReferenceDTO();
        referenceDTO.setPublicKey(payBean.getPayChannelIdPub());
        logger.debug("payChannelIdPub = {}",payBean.getPayChannelIdPub());
        transactionWriteInDTO.getContent()
                .add(finAllocWriteObjectFact.createTransactionWriteInDTOPaymentChannel(referenceDTO));

        referenceDTO = new ReferenceDTO();
        referenceDTO.setPublicKey(this.commonPaymentService.checkStringInputAndReturnValue("payMethodIdPub", payBean.getPayMethodIdPub()));
        logger.debug("payMethodIdPub = {}",payBean.getPayMethodIdPub());
        transactionWriteInDTO.getContent()
                .add(finAllocWriteObjectFact.createTransactionWriteInDTOPaymentMethod(referenceDTO));

        transactionWriteInDTO.getContent()
                .add(finAllocWriteObjectFact.createTransactionWriteInDTOAmount(this.checkAmount(payBean.getAmount())));

        if (payBean.getUseCaseIdPub().equals(UseCasesIdPub.PAYMENT_REFUND.getValue())) {
            transactionWriteInDTO.getContent()
                    .add(finAllocWriteObjectFact.createFinancialAllocationWriteInputDTOOriginalTransactionId(payBean.getTrsId()));
        }


        transactionWriteInDTO.getContent()
                .add(finAllocWriteObjectFact.createTransactionWriteInDTOReferenceKey(
                        this.commonPaymentService.checkStringInputAndReturnValue("referenceKey", payBean.getReferenceKey())));

        if (payBean.getReferenceDate() != null) {
            transactionWriteInDTO.getContent()
                    .add(finAllocWriteObjectFact.createTransactionWriteInDTOReferenceDate(DatatypeFactory
                    .newInstance().newXMLGregorianCalendar(payBean.getReferenceDate().toGregorianCalendar())));
        }

        if (payBean.getDocument() != null) {
            DocumentListRequest documentListRequest = new DocumentListRequest();
            documentListRequest.getDocumentDTO()
                    .add(this.writeDocumentDTO(payBean.getDocument()));
            transactionWriteInDTO.getContent()
                    .add(finAllocWriteObjectFact.createTransactionWriteInDTODocuments(documentListRequest));
        }

        return transactionWriteInDTO;
    }

    private DocumentDTO writeDocumentDTO(Document document){
        DocumentDTO documentDTO = new DocumentDTO();
        if (document.getDocumentId() != null)
            documentDTO.setDocumentId(document.getDocumentId());

        if (document.getDocumentCode() != null && !document.getDocumentCode().isBlank())
            documentDTO.setCode(document.getDocumentCode());

        return documentDTO;
    }

    private Long saveOperation(PayBean payBean) {
        FinTrxInterfaceCommonDto finTrxInterfaceCommonDto = FinTrxInterfaceCommonDto.builder()
                .action(UseCasesIdPub.PAYMENT.getAction())
                .amount(payBean.getAmount().getAmount())
                .baId(payBean.getBillingAccount() != null ? payBean.getBillingAccount().getBillingAccountId() : null)
                .baIdPub(payBean.getBillingAccount() != null ? payBean.getBillingAccount().getBillingAccountCode() : null)
                .csId(payBean.getCustomer() != null ? payBean.getCustomer().getCsId() : null)
                .csIdPub(payBean.getCustomer() != null ? payBean.getCustomer().getCsIdPub() : null)
                .currencyCode(payBean.getAmount().getCurrency())
                .documentId(payBean.getDocument().getDocumentId())
                .entryDate(LocalDateTime.now())
                .priority(0L)
                .producer("PaymentApi")
                .paymentChannelCode(payBean.getPayChannelIdPub())
                .paymentMethodCode(payBean.getPayMethodIdPub())
                .reference1(payBean.getEntityCode() != null ? payBean.getEntityCode() : null )
                .reference11(payBean.getEntityName())
                .reference12(payBean.getUsernameGPS())
                .status(properties.getProperty(PaymentApiSettingProperties.INIT_TRANSACTION))
                .suppressAlloc(SUPPRESS_ALLOC ? "X" : null)
                .transactionReference("0")
                .useCaseCode(payBean.getUseCaseIdPub())
                .build();

        return this.finTrxInterfaceRepository.save(this.finTrxCommonMapper.toEntity(finTrxInterfaceCommonDto)).getId();
    }
}
