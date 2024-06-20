package com.billcom.payment.service;

import com.billcom.payment.clients.soap.wsi.FinancialAllocationReverseClient;
import com.billcom.payment.clients.soap.wsi.FinancialDocumentDetailReadClient;
import com.billcom.payment.clients.soap.wsi.FinancialDocumentSearchClient;
import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.domains.bscs.FinTrxInterface;
import com.billcom.payment.commons.domains.bscs.FinTrxInterfaceHist;
import com.billcom.payment.commons.domains.postgres.Pay;
import com.billcom.payment.commons.dtos.bscs.FinTrxInterfaceCommonDto;
import com.billcom.payment.commons.dtos.postgres.PayDto;
import com.billcom.payment.commons.enums.UseCasesIdPub;
import com.billcom.payment.commons.exceptions.*;
import com.billcom.payment.commons.mappers.bscs.FinTrxCommonMapper;
import com.billcom.payment.commons.mappers.postgres.PayMapper;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceHistRepository;
import com.billcom.payment.commons.repositories.bscs.FinTrxInterfaceRepository;
import com.billcom.payment.commons.repositories.postgres.PayRepository;
import com.billcom.payment.config.properties.SettingsProperties;
import com.billcom.payment.utils.Constants;
import com.billcom.payment.utils.I18nErrorMessages;
import com.ericsson.financialallocationreverse.*;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadRequest;
import com.ericsson.financialdocumentdetailread.FinancialDocumentDetailReadResponse;
import com.ericsson.financialdocumentsearch.DocTypesRequest;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchRequest;
import com.ericsson.financialdocumentsearch.FinancialDocumentSearchResponse;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CancelPayService {

    private static final Logger logger = LogManager.getLogger(CancelPayService.class);

    private final FinancialDocumentDetailReadClient financialDocumentDetailReadClient;
    private final FinancialAllocationReverseClient financialAllocationReverseClient;
    private final FinancialDocumentSearchClient financialDocumentSearchClient;
    private final FinTrxInterfaceHistRepository interfaceHistRepository;
    private final FinTrxInterfaceRepository interfaceRepository;
    private final CommonPaymentService commonPaymentService;
    private final SettingsProperties settingsProperties;
    private final FinTrxCommonMapper trxCommonMapper;
    private final PayRepository payRepository;
    private final PayMapper payMapper;

    @Autowired
    public CancelPayService(FinancialDocumentDetailReadClient financialDocumentDetailReadClient,
                            FinancialAllocationReverseClient financialAllocationReverseClient,
                            FinancialDocumentSearchClient financialDocumentSearchClient,
                            FinTrxInterfaceHistRepository interfaceHistRepository,
                            FinTrxInterfaceRepository interfaceRepository,
                            CommonPaymentService commonPaymentService,
                            SettingsProperties settingsProperties,
                            FinTrxCommonMapper trxCommonMapper,
                            PayRepository payRepository,
                            PayMapper payMapper) {
        this.financialDocumentDetailReadClient = financialDocumentDetailReadClient;
        this.financialAllocationReverseClient = financialAllocationReverseClient;
        this.financialDocumentSearchClient = financialDocumentSearchClient;
        this.interfaceHistRepository = interfaceHistRepository;
        this.commonPaymentService = commonPaymentService;
        this.interfaceRepository = interfaceRepository;
        this.settingsProperties = settingsProperties;
        this.trxCommonMapper = trxCommonMapper;
        this.payRepository = payRepository;
        this.payMapper = payMapper;
    }

    public CancelPayResponse cancelPay(String username, String password, CancelPayRequest cancelPayRequest) {

        Long requestId;

        cancelPayRequest.setHandlingReasonIdPub(this.settingsProperties.getReasonHandlingIdPub());
        logger.info("Begin cancelPay Operation");
        this.checkCancelPayRequest(cancelPayRequest);
        logger.debug("------------------------- Transaction Details -------------------------");
        logger.debug("Handling Reason = {}",cancelPayRequest.getHandlingReasonIdPub());

        if(cancelPayRequest.getTransactionId() != null) {
            logger.debug("TransactionId = {}",cancelPayRequest.getTransactionId());
            logger.debug("------------------------------------------------------------------------");
            logger.info("Checking if cancelPay has already been processed for transactionId: {}", cancelPayRequest.getTransactionId());
            this.checkCancelPayAlreadyDoneByTrs(cancelPayRequest.getTransactionId(), cancelPayRequest.getUseCaseIdPub());
            logger.info("Starting cancellation process for transactionId '{}'.", cancelPayRequest.getTransactionId());
            requestId = this.saveOperation(cancelPayRequest.getDocument() == null ? null : cancelPayRequest.getDocument().getDocumentId(),
                    cancelPayRequest.getTransactionId(), cancelPayRequest.getUseCaseIdPub());
            return this.allocationReverse(cancelPayRequest.getHandlingReasonIdPub(), cancelPayRequest.getTransactionId(), username, password, requestId, cancelPayRequest.getUseCaseIdPub());
        }

        if(cancelPayRequest.getDocument() != null) {

            this.commonPaymentService.checkDocumentInput(cancelPayRequest.getDocument());
            Long transaction;
            Long documentId;
            FinancialDocumentSearchResponse finDocSearchRes;


            if (cancelPayRequest.getDocument().getDocumentId() == null){
                logger.debug("DocumentCode = {}",cancelPayRequest.getDocument().getDocumentCode());
                logger.debug("------------------------------------------------------------------------");
                logger.debug("DocumentId is null, searching Document...");
                finDocSearchRes = this.documentSearch(cancelPayRequest.getDocument().getDocumentCode(), username, password);
                documentId = finDocSearchRes.getDocuments().getItem().get(0).getDocumentId();
                logger.debug("Document found with DocumentId {}", documentId);
            }else {
                logger.debug("DocumentId = {}",cancelPayRequest.getDocument().getDocumentId());
                logger.debug("------------------------------------------------------------------------");
                documentId = cancelPayRequest.getDocument().getDocumentId();
            }
            this.commonPaymentService.checkActionAlreadyDoneByDocId(documentId,UseCasesIdPub.RV_PAYMENT.getAction(),I18nErrorMessages.DOCUMENT_ALREADY_REVERSED);
            FinancialDocumentDetailReadResponse finDocDetailReadRes = this.documentDetailRead(documentId,username,password);
            transaction = finDocDetailReadRes.getItems().getItem().get(0).getTransactionId();
            requestId = this.saveOperation(documentId, transaction, cancelPayRequest.getUseCaseIdPub());

            logger.debug("Operation saved with requestId {}", requestId);
            return this.allocationReverse(cancelPayRequest.getHandlingReasonIdPub(),
                    transaction, username, password, requestId, cancelPayRequest.getUseCaseIdPub());
        }
        logger.warn("Both TransactionId and Document are null. No action taken.");
        return null;
    }

    private void checkCancelPayRequest(CancelPayRequest cancelPayRequest) {
        logger.info("Checking required fields for payment cancellation request...");
        if (cancelPayRequest == null) {
            logger.error("CancelPayRequest is null");
            throw new MandatoryInputObjectException("cancelPayRequest");
        }
        logger.info("Checking handlingReasonIdPub...");
        this.commonPaymentService.checkStringInputValue("handlingReasonIdPub",cancelPayRequest.getHandlingReasonIdPub());
        if (cancelPayRequest.getDocument() == null && cancelPayRequest.getTransactionId() == null ) {
            throw new AtLeastMandatoryInputValueException(I18nErrorMessages.ONE_OBJECT_MANDATORY,"transactionId", "document");
        }
        logger.debug("Finished checking CancelPay Request");
        logger.info("CancelPay Request is valid...");

    }

    private CancelPayResponse allocationReverse(String handlingReasonIdPub,
                                                Long transactionId, String username, String password, Long requestId,String useCase) {

        logger.info("Starting reversing payment process...");

        CancelPayResponse cancelPayResponse = new CancelPayResponse();
        FinancialAllocationReverseRequest financialAllocationReverseRequest = new FinancialAllocationReverseRequest();
        com.ericsson.financialallocationreverse.InputAttributes inputAttributes = new com.ericsson.financialallocationreverse.InputAttributes();
        FinancialAllocationReverseInputDTO inputDTO = new FinancialAllocationReverseInputDTO();

        logger.debug("Initiating allocation reverse with the following parameters:");
        logger.debug("- Original Transaction ID: {}", transactionId);
        logger.debug("- Allocation UseCase: {}", useCase);
        logger.debug("- Handling Reason: {}", handlingReasonIdPub);

        ReferenceDTO referenceDTO = new ReferenceDTO();
        referenceDTO.setPublicKey(useCase);
        ObjectFactory objectFactory = new ObjectFactory();
        inputDTO.getContent().add(objectFactory.createFinancialAllocationReverseInputDTOUseCase(referenceDTO));
        referenceDTO = new ReferenceDTO();
        referenceDTO.setPublicKey(handlingReasonIdPub);
        inputDTO.getContent().add(objectFactory.createFinancialAllocationReverseInputDTOHandlingReason(referenceDTO));
        inputDTO.getContent().add(objectFactory.createFinancialAllocationReverseInputDTOOriginalTransactionId(transactionId));
        inputAttributes.setFinancialAllocationReverseInputDTO(inputDTO);
        financialAllocationReverseRequest.setInputAttributes(inputAttributes);

        try {

            FinancialAllocationReverseResponse allocationReverseResponse = this.financialAllocationReverseClient
                    .execute(financialAllocationReverseRequest, username, password);

            if (allocationReverseResponse == null || allocationReverseResponse.getFinancialAllocationReverseOutputDTO() == null ||
                    allocationReverseResponse.getFinancialAllocationReverseOutputDTO().getTransactions() == null ||
                    allocationReverseResponse.getFinancialAllocationReverseOutputDTO().getTransactions().getTransactionReverseOutDTO() == null ||
                    allocationReverseResponse.getFinancialAllocationReverseOutputDTO().getTransactions().getTransactionReverseOutDTO().isEmpty()) {
                logger.warn("No transaction Reversed");

                throw new DataNotFoundException("ff");
            }

            allocationReverseResponse.getFinancialAllocationReverseOutputDTO()
                    .getTransactions()
                    .getTransactionReverseOutDTO()
                    .forEach((transactionReverseOutDTO) -> {
                        this.saveCancelPay(transactionReverseOutDTO, requestId);
                        CustomerSMSDetails customerSMSDetails= this.commonPaymentService.getCustomerSmsNumberAndPRGCode(transactionReverseOutDTO.getCustomerId(),
                                transactionReverseOutDTO.getCustomerIdPub(),username,password);
                        try {
                            this.commonPaymentService.loadSMSConfig(UseCasesIdPub.RV_PAYMENT.getAction(),
                                    "DEFAULT",customerSMSDetails,transactionReverseOutDTO.getAmount().getAmount(),
                                    transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO().get(0).getDocumentCode());
                        } catch (DatatypeConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                    });

            logger.info("transaction reversed Successfully");

            cancelPayResponse.setIsSuccessful(Boolean.TRUE);
            cancelPayResponse.setComment("Success");    
            cancelPayResponse.setErrorCode("0");

        } catch (SOAPFaultException faultException) {
            logger.debug("Updating payment failed by requestId: {}", requestId);
            this.interfaceRepository.updatePaymentFailedById(LocalDateTime.now(),
                    this.settingsProperties.getTransaction().getFailedStatus(),
                    faultException.getFault().getFaultString(),
                    requestId
            );

            Optional<FinTrxInterface> interfaceCommonDto = this.interfaceRepository.findById(requestId);

            if(interfaceCommonDto.isPresent()) {
                PayDto payDto = PayDto.builder()
                        .operationState(Constants.FAILURE)
                        .operationType(Constants.CANCEL_PAY)
                        .entryDate(LocalDateTime.now())
                        .csId(interfaceCommonDto.get().getCsId())
                        .csIdPub(interfaceCommonDto.get().getCsIdPub())
                        .trsId(interfaceCommonDto.get().getOriginalTransactionId())
                        .btOhxact(interfaceCommonDto.get().getDocumentId())
                        .entryDate(LocalDateTime.now())
                        .build();
                logger.debug("Saving payment with requestId: {}", requestId);
                this.payRepository.save(this.payMapper.toEntity(payDto));
            }
            throw new InvokeClientException(faultException.getFault(), faultException);
        }
        return cancelPayResponse;
    }

    private void saveCancelPay(TransactionReverseOutDTO transactionReverseOutDTO, Long requestId) {

        if (transactionReverseOutDTO.getDocuments() == null ||
                transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO() == null ||
                transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO().isEmpty()) {
            throw new DataNotFoundException("No documents found");
        }

        if (transactionReverseOutDTO.getTransactionId() != null) {
            logger.info("Saving cancel pay operation for request ID: {}", requestId);
            this.getFinTrxInterfaceHistEntity(transactionReverseOutDTO, requestId);
        } else {
            logger.warn("Transaction ID is null, cancel pay operation skipped for request ID: {}", requestId);
        }

    }

    private void getFinTrxInterfaceHistEntity(TransactionReverseOutDTO  transactionReverseOutDTO, Long requestId) {
        logger.info("Updating payment success by request ID: {}", requestId);
        this.interfaceRepository.updatePaymentSuccessByRequestId(LocalDateTime.now(),
                transactionReverseOutDTO.getTransactionId(),
                this.settingsProperties.getTransaction().getSuccessStatus(),
                requestId);

        logger.debug("Retrieving financial transaction interface entity by request ID: {}", requestId);

        FinTrxInterfaceCommonDto interfaceCommonDto = this.trxCommonMapper.toDto(this.interfaceRepository
                .findById(requestId)
                .orElseThrow(() -> new DataNotFoundException(I18nErrorMessages.TECHNICAL_PROBLEM)));

        this.interfaceRepository.deleteById(requestId);

        PayDto payDto = PayDto.builder()
                .csId(transactionReverseOutDTO.getCustomerId())
                .csIdPub(transactionReverseOutDTO.getCustomerIdPub())
                .btOhxact(transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO().get(0).getDocumentId())
                .rtCachknum(transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO().get(0).getDocumentCode())
                .entryDate(LocalDateTime.now())
                .trsId(transactionReverseOutDTO.getTransactionId())
                .canal("DEFAULT")
                .operationType(Constants.CANCEL_PAY)
                .operationState(Constants.SUCCESS)
                .build();
        this.payRepository.save(this.payMapper.toEntity(payDto));

        logger.debug("Searching for payment transaction...");
        logger.debug("- Document ID: {}", payDto.getBtOhxact());
        logger.debug("- Operation Type: {}", Constants.PAY);
        logger.debug("- Operation State: {}", Constants.SUCCESS);

        Optional<Pay> pay = this.payRepository.findPayByBtOhxactAndOperationTypeAndOperationState(payDto.getBtOhxact(),Constants.PAY,Constants.SUCCESS);

        if(pay.isPresent()) {
            Pay payEntity = pay.get();
            logger.info("Updating payment state to CANCELED");
            payEntity.setOperationState(Constants.CANCELED);
            this.payRepository.save(payEntity);
        }

        FinTrxInterfaceHist finInterfaceHist = this.trxCommonMapper.toHistEntity(interfaceCommonDto);
        finInterfaceHist.setId(requestId);
        finInterfaceHist.setGeneratedTransactionId(transactionReverseOutDTO.getTransactionId());
        finInterfaceHist.setCsId(transactionReverseOutDTO.getCustomerId());
        finInterfaceHist.setCsIdPub(transactionReverseOutDTO.getCustomerIdPub());
        finInterfaceHist.setAmount(transactionReverseOutDTO.getAmount().getAmount());
        finInterfaceHist.setBaIdPub(transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO().get(0).getBillingAccountIdPub());
        finInterfaceHist.setBaId(transactionReverseOutDTO.getDocuments().getDocumentReverseOutDTO().get(0).getBillingAccountId());
        finInterfaceHist.setHistDate(LocalDateTime.now());
        this.interfaceHistRepository.save(finInterfaceHist);
        logger.info("Financial transaction history saved successfully for request ID : {}", requestId);

    }

    private FinancialDocumentDetailReadResponse documentDetailRead(Long documentId, String username, String password) {

        logger.info("Initiating document detail read operation...");
        FinancialDocumentDetailReadRequest documentDetailReadRequest = new FinancialDocumentDetailReadRequest();
        com.ericsson.financialdocumentdetailread.InputAttributes inputAttributes = new com.ericsson.financialdocumentdetailread.InputAttributes();
        logger.debug("Retrieving document details with the following parameters:");
        logger.debug("- Document Id: {}", documentId);
        inputAttributes.setDocumentId(documentId);
        documentDetailReadRequest.setInputAttributes(inputAttributes);

        try {
            FinancialDocumentDetailReadResponse documentDetailReadResponse= this.financialDocumentDetailReadClient
                    .execute(documentDetailReadRequest, username, password);

            if (documentDetailReadResponse.getItems() == null ||
                    documentDetailReadResponse.getItems().getItem() == null ||
                    documentDetailReadResponse.getItems().getItem().isEmpty()) {
                logger.warn("No data found for document Id: {}", documentId);
                throw new DataNotFoundException("Financial document not found");
            }else {
                logger.info("Document detail read operation successful for document Id: {}", documentId);
            }
            return documentDetailReadResponse;
        } catch (SOAPFaultException faultException) {
            throw new InvokeClientException(faultException);
        }
    }

    private FinancialDocumentSearchResponse documentSearch(String documentCode, String username, String password) {
        logger.info("Initiating document search operation...");
        FinancialDocumentSearchRequest documentSearchRequest = new FinancialDocumentSearchRequest();
        com.ericsson.financialdocumentsearch.InputAttributes inputAttributes = new com.ericsson.financialdocumentsearch.InputAttributes();
        inputAttributes.setDocumentCode(documentCode);
        DocTypesRequest docTypesRequest = new DocTypesRequest();
        docTypesRequest.getDocType().add("IN");
        inputAttributes.setDocTypes(docTypesRequest);
        documentSearchRequest.setInputAttributes(inputAttributes);
        logger.debug("Searching document with the following parameters:");
        logger.debug("- Document Code: {}", documentCode);
        logger.debug("- Document Type: {}", "IN");

        try {
            FinancialDocumentSearchResponse searchResponse= this.financialDocumentSearchClient
                    .execute(documentSearchRequest, username, password);

            if (searchResponse.isSearchIsComplete() && (searchResponse.getDocuments() == null || searchResponse.getDocuments().getItem() == null ||
                    searchResponse.getDocuments().getItem().isEmpty())) {
                logger.warn("No document found during the search operation for documentCode: {}", documentCode);
                throw new DataNotFoundException("No document found");
            }
            logger.debug("Document with code: {} was found successfully.", documentCode);
            logger.info("The document has been retrieved successfully.");
            return searchResponse;
        } catch (SOAPFaultException faultException) {
            throw new InvokeClientException(faultException);
        }
    }

    private Long saveOperation(Long documentId, Long trx, String useCase) {
        logger.info("Initiating cancelPay request...");
        FinTrxInterfaceCommonDto finTrxInterfaceCommonDto = FinTrxInterfaceCommonDto.builder()
                .action(UseCasesIdPub.RV_PAYMENT.getAction())
                .documentId(documentId)
                .entryDate(LocalDateTime.now())
                .currencyCode("TND")
                .amount(0.0)
                .priority(0L)
                .producer("PaymentApi")
                .transactionReference("0")
                .status(this.settingsProperties.getTransaction().getNewStatus())
                .useCaseCode(useCase)
                .originalTransactionId(trx)
                .build();
        Long requestId = this.interfaceRepository.save(this.trxCommonMapper.toEntity(finTrxInterfaceCommonDto)).getId();
        logger.info("CancelPay request has been successfully initiated.");
        logger.debug("RequestId is: {}", requestId);
        return requestId;
    }

    private void checkCancelPayAlreadyDoneByTrs(Long trsId, String useCase) {
        logger.info("Checking if payment reverse has already been performed...");
        logger.debug("Searching transaction with the following parameters:");
        logger.debug("- Transaction Id: {}", trsId);
        logger.debug("- Transaction Type: {}", useCase);
        if (this.interfaceHistRepository.existsByOriginalTransactionIdAndAction(trsId, useCase) ||
                this.interfaceRepository.existsByOriginalTransactionIdAndAction(trsId, useCase)) {
            logger.warn("Transaction with the following id: {} exists", trsId);
            throw new OperationAlreadyDoneException(I18nErrorMessages.TRANSACTION_ALREADY_REVERSED, trsId.toString());
        }
        logger.info("Transaction action verification completed for Transaction Id: {}", trsId);
    }
}
