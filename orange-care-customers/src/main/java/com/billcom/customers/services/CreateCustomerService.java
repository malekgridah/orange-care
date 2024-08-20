package com.billcom.customers.services;

import com.billcom.customer.handling.*;
import com.billcom.customers.beans.create.CreateCustomerRequest;
import com.billcom.customers.beans.create.CreateCustomerResponse;
import com.billcom.customers.clients.bscs.CustomerHandlingClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreateCustomerService {

    private final CustomerHandlingClient customerHandling;

    @Autowired
    public CreateCustomerService(CustomerHandlingClient customerHandling) {
        this.customerHandling = customerHandling;
    }

    public CreateCustomerResponse create(CreateCustomerRequest request) {
        CreateCustomerResponse response = null;
        EntityResponse entityResponse;
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomer(this.prepareCustomer(request));

        try {
            entityResponse = this.customerHandling.execute(customerRequest);
        } catch (UnexpectedError e) {
            throw new RuntimeException(e);
        }

        if (entityResponse != null && entityResponse.isIsSuccessful()) {
            response = new CreateCustomerResponse();
            response.setCsId(entityResponse.getId());
            response.setCsIdPub(entityResponse.getIdPub());
        }

        return response;
    }

    private Customer prepareCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();

        customer.setAddress(this.prepareAddress(request));
        customer.setPaymentArrangement(this.preparePaymentArrangement(request));
        customer.setPrgCode(request.getPrgCode());
        customer.setCostId(request.getCostId());
        customer.setCsBillcycle(request.getCsBillcycle());
        customer.setCsDunning(request.getCsDunning());
        customer.setCsStatus(request.getCsStatus());
        customer.setWpCode(request.getWpCode());
        customer.setRpcode(request.getRpCode());
        customer.setRpcodePub(request.getRpCodePub());
        customer.setCsDealerid(request.getCsDealerId());
        customer.setCsPassword("0000");
        customer.setCustcatCode(request.getCustCatCode());
        customer.setRsCode(request.getRsCode());
        customer.setPaymentResp(true);

        return customer;
    }

    private PaymentArrangement preparePaymentArrangement(CreateCustomerRequest request) {
        PaymentArrangement paymentArrangement = new PaymentArrangement();
        paymentArrangement.setCspPmntId(request.getPaymentArrangement().getCspPmntId());
        paymentArrangement.setCspSeqno(0L);
        paymentArrangement.setCspActUsed(request.getPaymentArrangement().getCspActUsed());

        return paymentArrangement;
    }


    private Address prepareAddress(CreateCustomerRequest request) {
        Address address = new Address();

        DateBean dateBean = new DateBean();

//        try {
//            GregorianCalendar gregorianCalendar = new GregorianCalendar();
//            gregorianCalendar.setTime(request.getAddress().getAdrBirthdate().);
//            dateBean.setDate(DatatypeFactory
//                    .newInstance().newXMLGregorianCalendar(.toString()));
//        } catch (DatatypeConfigurationException e) {
//            throw new RuntimeException(e);
//        }
//
//        address.setAdrBirthdt(dateBean);
        address.setAdrCity(request.getAddress().getAdrCity());
        address.setAdrCusttype(request.getAddress().getAdrCustType());
        address.setAdrFname(request.getAddress().getAdrFName());
        address.setAdrLname(request.getAddress().getAdrLName());
        address.setAdrName(request.getAddress().getAdrLName() +" "+ request.getAddress().getAdrLName());
        address.setAdrCounty(request.getAddress().getAdrCountry());
        address.setAdrStreet(request.getAddress().getAdrStreet());
        address.setAdrStreetno(request.getAddress().getAdrStreetNo());
        address.setAdrEmail(request.getAddress().getAdrEmail());
        address.setTtlId(request.getAddress().getTtlId());
        address.setAdrZip(request.getAddress().getAdrZip());
        address.setAdrIdno(request.getAddress().getAdrIdNo());
        address.setAdrNationalityPub(request.getAddress().getAdrNationalityPub());
//        address.setAdrSex(request.getAddress().getAdrSex());
        address.setIdtypeCode(request.getAddress().getIdTypeCode());
        address.setMasCodePub(request.getAddress().getMasCodePub());
        address.setAdrSeq(0L);

        return address;
    }
}
