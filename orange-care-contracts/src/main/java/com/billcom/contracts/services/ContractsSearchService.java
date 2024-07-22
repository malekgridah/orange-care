package com.billcom.contracts.services;

import com.billcom.contracts.beans.search.ContractSearch;
import com.billcom.contracts.beans.search.ContractsSearchRequest;
import com.billcom.contracts.beans.search.ContractsSearchResponse;
import com.billcom.contracts.clients.wsi.ContractsSearchClient;
import com.billcom.contracts.clients.wsi.RateplansReadClient;
import com.ericsson.contractssearch.ContractsListpartResponse;
import com.ericsson.contractssearch.InputAttributes;
import com.ericsson.contractssearch.TypesRequest;
import com.ericsson.rateplansread.RateplansReadRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractsSearchService {
    private final static Logger log = LogManager.getLogger(ContractsSearchService.class);

    private final ContractsSearchClient contractsSearchClient;
    private final RateplansReadClient rateplansReadClient;

    public ContractsSearchService(ContractsSearchClient contractsSearchClient, RateplansReadClient rateplansReadClient) {
        this.contractsSearchClient = contractsSearchClient;
        this.rateplansReadClient = rateplansReadClient;
    }

    public ContractsSearchResponse contractsSearch(ContractsSearchRequest searchRequest) {
        com.ericsson.contractssearch.ContractsSearchRequest request = new com.ericsson.contractssearch.ContractsSearchRequest();
        com.ericsson.contractssearch.ContractsSearchResponse contractsSearchResponse;

        request.setInputAttributes(this.prepareContractsSearchRequest(searchRequest));
        contractsSearchResponse = this.contractsSearchClient.execute(request, "ADMX", "ADMX");
        return this.prepareContractSearchResponse(contractsSearchResponse,searchRequest);
    }


    private InputAttributes prepareContractsSearchRequest(ContractsSearchRequest searchRequest) {
        InputAttributes inputAttributes = new com.ericsson.contractssearch.InputAttributes();
        TypesRequest typesRequest = new TypesRequest();

        typesRequest.getContractTypeId().add(1L);
        typesRequest.getContractTypeId().add(3L);
        inputAttributes.setTypes(typesRequest);
        inputAttributes.setFlagCase(false);
        inputAttributes.setSearcher("SearchContractsEvenWithoutServices");

        if (searchRequest.getResType() != null && !searchRequest.getResType().isBlank()) {
            if (searchRequest.getResNo() != null && !searchRequest.getResNo().isBlank()) {
                switch (searchRequest.getResType()) {
                    case "dirNum" -> inputAttributes.setDirnum(searchRequest.getResNo());
                    case "smNum" -> inputAttributes.setSmNum(searchRequest.getResNo());
                    case "portNum" -> inputAttributes.setPortNum(searchRequest.getResNo());
                    case "devPortNum" -> inputAttributes.setDevPortNum(searchRequest.getResNo());
                }
            }
        }

        if (searchRequest.getIncludeResHist() != null && searchRequest.getIncludeResHist()) {
            inputAttributes.setSearcher("ContractSearchWithHistory");
        }

        if (searchRequest.getIncludeResHist() != null && !searchRequest.getIncludeResHist()) {
            inputAttributes.setSearcher("SearchContractsEvenWithoutServices");
        }

        if (searchRequest.getFlagCase() != null) {
            inputAttributes.setFlagCase(searchRequest.getFlagCase());
        }

        if (searchRequest.getCsIdPub() != null && !searchRequest.getCsIdPub().isBlank()) {
            inputAttributes.setCsIdPub(searchRequest.getCsIdPub());
        }

        if (searchRequest.getCsCode() != null && !searchRequest.getCsCode().isBlank()) {
            inputAttributes.setCsCode(searchRequest.getCsCode());
        }

        if (searchRequest.getCoCode() != null && !searchRequest.getCoCode().isBlank()) {
            inputAttributes.setCoIdPub(searchRequest.getCoCode());
        }

        if (searchRequest.getCoStatus() != null && !searchRequest.getCoStatus().isBlank()) {
            log.info("coStatus: {}", searchRequest.getCoStatus());
            inputAttributes.setCoStatus(BigInteger.valueOf(Long.parseLong(searchRequest.getCoStatus())));
        }

        if (searchRequest.getMarket() != null && !searchRequest.getMarket().isBlank()) {
            inputAttributes.setSccode(Long.valueOf(searchRequest.getMarket()));
        }

        if (searchRequest.getSubMarket() != null && !searchRequest.getSubMarket().isBlank()) {
            inputAttributes.setSubmId(Long.valueOf(searchRequest.getSubMarket()));
        }

        if (searchRequest.getSrchCount() != null) {
            inputAttributes.setSrchCount(Long.valueOf(searchRequest.getSrchCount()));
        } else {
            inputAttributes.setSrchCount(20L);
        }

        if (searchRequest.getCsFName() != null && !searchRequest.getCsFName().isBlank()) {
            inputAttributes.setAdrFname(searchRequest.getCsFName());
        }

        if (searchRequest.getCsLName() != null && !searchRequest.getCsLName().isBlank()) {
            inputAttributes.setAdrLname(searchRequest.getCsLName());
        }

        if (searchRequest.getCoRpCode() != null && !searchRequest.getCoRpCode().isBlank()) {
            inputAttributes.setRpcode(Long.valueOf(searchRequest.getCoRpCode()));
        }

        return inputAttributes;
    }

    private ContractsSearchResponse prepareContractSearchResponse(com.ericsson.contractssearch.ContractsSearchResponse contractsSearchResponse,
                                                                  ContractsSearchRequest searchRequest) {
        ContractsSearchResponse response = new ContractsSearchResponse();
        List<ContractSearch> contracts = new ArrayList<>();

        if (contractsSearchResponse != null) {
            List<ContractsListpartResponse> contractsListPartResponses = contractsSearchResponse.getContracts().getItem();

            if (contractsListPartResponses != null && !contractsListPartResponses.isEmpty()) {

                contractsListPartResponses.forEach(contract -> {
                    ContractSearch contractSearch = new ContractSearch();

                    contractSearch.setCoIdPub(contract.getCoIdPub());
                    contractSearch.setCoId(contract.getCoId());
                    contractSearch.setStatus(Integer.valueOf(String.valueOf(contract.getCoStatus())));
                    contractSearch.setPublicKey(contract.getCsIdPub());
                    contractSearch.setCsCode(contract.getCsCode());
                    if (contract.getAdrFname() != null) {
                        contractSearch.setCustomer(contract.getAdrFname()+" "+contract.getAdrLname());
                    }
                    if (contract.getAdrName() != null) {
                        contractSearch.setCustomer(contract.getAdrName());
                    }
                    if (searchRequest.getResType() != null && !searchRequest.getResType().isBlank()) {
                        if (searchRequest.getResNo() != null && !searchRequest.getResNo().isBlank()) {
                            switch (searchRequest.getResType()) {
                                case "dirNum" -> contractSearch.setResType("Directory Number");
                                case "smNum" -> contractSearch.setResType("Sim Serial Number");
                                case "portNum" -> contractSearch.setResType("Port Number");
                                case "devPortNum" -> contractSearch.setResType("Device Port Number");
                            }
                        }
                    }
                    if (searchRequest.getResNo() != null && !searchRequest.getResNo().isBlank()) {
                        contractSearch.setResNo(searchRequest.getResNo());
                    }
                    contractSearch.setCity(contract.getAdrCity());
                    contractSearch.setStreet(contract.getAdrStreet());
                    contractSearch.setRateplan(this.getContractRateplan(contract.getRpcode()));
                    contractSearch.setRpCode(contract.getRpcode());
                    contractSearch.setSubMarket(contract.getSubmId().toString());
                    contractSearch.setHomeNetwork(contract.getPlcode().toString());

                    contracts.add(contractSearch);
                });

                response.setContracts(contracts);
            }
        }
        return response;
    }


    public String getContractRateplan(Long rpCode) {
        RateplansReadRequest rateplansRead = new RateplansReadRequest();
        com.ericsson.rateplansread.InputAttributes inputAttributes = new com.ericsson.rateplansread.InputAttributes();

        inputAttributes.setRpcode(rpCode);
        rateplansRead.setInputAttributes(inputAttributes);
        return this.rateplansReadClient.execute(rateplansRead, "ADMX", "ADMX")
                .getNumRp()
                .getItem()
                .get(0)
                .getRpDes();
    }
}
