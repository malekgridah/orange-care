package com.billcom.bscs.services;

import com.billcom.bscs.clients.wsi.ContractsSearchClient;
import com.billcom.bscs.clients.wsi.CustomersSearchClient;
import com.billcom.bscs.commons.BaseCommonsOperations;
import com.billcom.bscs.commons.beans.contract.ContractRequest;
import com.billcom.bscs.commons.beans.contract.ContractResponse;
import com.billcom.bscs.commons.beans.customer.*;
import com.ericsson.contractssearch.ContractsListpartResponse;
import com.ericsson.contractssearch.ContractsSearchRequest;
import com.ericsson.contractssearch.ContractsSearchResponse;
import com.ericsson.customerssearch.InputAttributes;
import com.ericsson.customerssearch.SearchResultListpartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements BaseCommonsOperations {

    private final CustomersSearchClient customersSearchClient;
    private final ContractsSearchClient contractsSearchClient;

    @Autowired
    public CustomerService(CustomersSearchClient customersSearchClient,
                           ContractsSearchClient contractsSearchClient) {
        this.customersSearchClient = customersSearchClient;
        this.contractsSearchClient = contractsSearchClient;
    }

    @Override
    public ContractResponse contractRead(ContractRequest contractRequest) {
        return null;
    }

    @Override
    public CustomerResponse customerRead(CustomerResponse customerResponse) {
        return null;
    }

    @Override
    public CustomersSearchResponse searchCustomers(CustomersSearchRequest request) {
        com.ericsson.customerssearch.CustomersSearchRequest customersSearchRequest = new com.ericsson.customerssearch.CustomersSearchRequest();
        ContractsSearchRequest contractsSearchRequest = new ContractsSearchRequest();
        com.ericsson.customerssearch.CustomersSearchResponse customersSearchResponse;
        ContractsSearchResponse contractsSearchResponse;

        assert request != null;

        if (request.getResType() != null && !request.getResType().isBlank()) {
            if (request.getResNo() != null && !request.getResNo().isBlank()) {
                contractsSearchRequest.setInputAttributes(this.prepareContractsSearchRequest(request));
                contractsSearchResponse = this.contractsSearchClient.execute(contractsSearchRequest, "ADMX", "ADMX");
                return this.prepareCustomersSearchResponse(contractsSearchResponse);
            }
        }

        customersSearchRequest.setInputAttributes(this.prepareCustomersSearchRequest(request));
        customersSearchResponse= this.customersSearchClient.execute(customersSearchRequest,"ADMX", "ADMX");
        return this.prepareCustomersSearchResponse(customersSearchResponse);
    }

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        return null;
    }


    private com.ericsson.contractssearch.InputAttributes prepareContractsSearchRequest(CustomersSearchRequest request) {
        com.ericsson.contractssearch.InputAttributes inputAttributes = new com.ericsson.contractssearch.InputAttributes();

        switch (request.getResType()) {
            case "dirNum" -> inputAttributes.setDirnum(request.getResNo());
            case "smNum" -> inputAttributes.setSmNum(request.getResNo());
            case "portNum" -> inputAttributes.setPortNum(request.getResNo());
            case "devPortNum" -> inputAttributes.setDevPortNum(request.getResNo());
        }

        inputAttributes.setPartyType("C");
        inputAttributes.setCsLevelCode("0");

        if (request.getIncludeResHist() != null && request.getIncludeResHist()) {
            inputAttributes.setSearcher("ContractSearchWithHistory");
        }

        if (request.getIncludeResHist() != null && !request.getIncludeResHist()) {
            inputAttributes.setSearcher("ContractSearchWithoutHistory");
        }

        return inputAttributes;
    }


    private InputAttributes prepareCustomersSearchRequest(CustomersSearchRequest request) {
        InputAttributes inputAttributes = new InputAttributes();

        if (request.getSrchCount() != null && request.getSrchCount() !=0 ) {
            inputAttributes.setSrchCount(BigInteger.valueOf(request.getSrchCount()));
        }

        if (request.getCsStatus() != null && !request.getCsStatus().isBlank() ) {
            inputAttributes.setCsStatus(request.getCsStatus());
        }

        if (request.getAdrIdno() != null && !request.getAdrIdno().isBlank() ) {
            inputAttributes.setAdrIdno(request.getAdrIdno());
        }

        if (request.getAdrLname() != null && !request.getAdrLname().isBlank() ) {
            inputAttributes.setAdrLname(request.getAdrLname());
        }

        if (request.getAdrFname() != null && !request.getAdrFname().isBlank() ) {
            inputAttributes.setAdrFname(request.getAdrFname());
        }

        if (request.getCsCode() != null && !request.getCsCode().isBlank() ) {
            inputAttributes.setCsStatus(request.getCsCode());
        }

        if (request.getCsIdPub() != null && !request.getCsIdPub().isBlank() ) {
            inputAttributes.setCsStatus(request.getCsIdPub());
        }

        if (request.getStartIndex() != null) {
            inputAttributes.setStartIndex(request.getStartIndex());
        }

        if (request.getPaymentResp() != null) {
            inputAttributes.setPaymentResp(request.getPaymentResp());
        }

        if (request.getCsContrResp() != null) {
            inputAttributes.setCsContrResp(request.getCsContrResp());
        }

        if (request.getFlagCase() != null) {
            inputAttributes.setFlagCase(request.getFlagCase());
        }

        if (request.getFlagMatchcode() != null) {
            inputAttributes.setFlagMatchcode(request.getFlagMatchcode());
        }

        inputAttributes.setCsLevelCode("0");
        inputAttributes.setPartyRoleId(1L);
        inputAttributes.setPartyType("C");

        return inputAttributes;
    }


    private CustomersSearchResponse prepareCustomersSearchResponse (ContractsSearchResponse contractsSearchResponse) {
        CustomersSearchResponse customersSearchRes = new CustomersSearchResponse();
        List<CustomersSearch> customers = new ArrayList<>();

        if (contractsSearchResponse != null) {
            List<ContractsListpartResponse> listpartResponses=  contractsSearchResponse.getContracts().getItem();

            if (listpartResponses != null && !listpartResponses.isEmpty()) {

                listpartResponses.forEach(contract -> {
                    CustomersSearch customersSearch = new CustomersSearch();

                    customersSearch.setCsId(contract.getCsId());
                    customersSearch.setCsIdPub(contract.getCsIdPub());
                    customersSearch.setCsCode(contract.getCsCode());
                    customersSearch.setCsStatus("a");
                    customersSearch.setAdrLname(contract.getAdrLname());
                    customersSearch.setAdrFname(contract.getAdrFname());
                    customersSearch.setAdrStreet(contract.getAdrStreet());
                    customersSearch.setAdrStreetno(contract.getAdrStreetno());
                    customersSearch.setAdrCity(contract.getAdrCity());
                    customersSearch.setAdrZip(contract.getAdrZip());

                    customers.add(customersSearch);
                });

                customersSearchRes.setCustomers(customers);
            }

        }
        return customersSearchRes;
    }


    private CustomersSearchResponse prepareCustomersSearchResponse(com.ericsson.customerssearch.CustomersSearchResponse customersSearchResponse) {
        CustomersSearchResponse customersSearchRes = new CustomersSearchResponse();
        List<CustomersSearch> customers = new ArrayList<>();

        if (customersSearchResponse != null) {
            List<SearchResultListpartResponse> listpartResponses=  customersSearchResponse.getSearchResult().getItem();

            if (listpartResponses != null && !listpartResponses.isEmpty()) {

                listpartResponses.forEach(customer -> {
                    CustomersSearch customersSearch = new CustomersSearch();

                    customersSearch.setCsId(customer.getCsId());
                    customersSearch.setCsIdPub(customer.getCsIdPub());
                    customersSearch.setCsCode(customer.getCsCode());
                    customersSearch.setCsStatus(customer.getCsStatus());
                    customersSearch.setAdrLname(customer.getAdrLname());
                    customersSearch.setAdrFname(customer.getAdrFname());
                    customersSearch.setAdrStreet(customer.getAdrStreet());
                    customersSearch.setAdrStreetno(customer.getAdrStreetno());
                    customersSearch.setAdrCity(customer.getAdrCity());
                    customersSearch.setAdrZip(customer.getAdrZip());

                    customers.add(customersSearch);
                });

                customersSearchRes.setCustomers(customers);
            }
        }
        return customersSearchRes;
    }

}


