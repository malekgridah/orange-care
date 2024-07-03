package com.billcom.bscs.services.customer;

import com.billcom.bscs.clients.wsi.AddressesReadClient;
import com.billcom.bscs.clients.wsi.ContractsSearchClient;
import com.billcom.bscs.clients.wsi.CustomerReadClient;
import com.billcom.bscs.commons.beans.customer.overview.*;
import com.billcom.bscs.services.CommonsService;
import com.ericsson.addreessesread.AddressesReadRequest;
import com.ericsson.addreessesread.AddressesReadResponse;
import com.ericsson.contractssearch.ContractsSearchRequest;
import com.ericsson.contractssearch.ContractsSearchResponse;
import com.ericsson.customerread.CustomerReadRequest;
import com.ericsson.customerread.CustomerReadResponse;
import com.ericsson.customerread.InputAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerOverviewService {

    private final CustomerReadClient customerReadClient;
    private final ContractsSearchClient contractsSearchClient;
    private final AddressesReadClient addressesReadClient;
    private final CommonsService commonsService;

    @Autowired
    public CustomerOverviewService(CustomerReadClient customerReadClient,
                                   ContractsSearchClient contractsSearchClient,
                                   AddressesReadClient addressesReadClient,
                                   CommonsService commonsService) {
        this.customerReadClient = customerReadClient;
        this.contractsSearchClient = contractsSearchClient;
        this.addressesReadClient = addressesReadClient;
        this.commonsService = commonsService;
    }

    public CustomerOverviewResponse overview(CustomerOverviewRequest request) {
        CustomerOverviewResponse response = new CustomerOverviewResponse();
        response.setCsId(request.getCsId());
        response.setCustomer(this.customerRead(request.getCsId()));
        response.setContacts(this.customerContracts(request.getCsId()));
        response.setAddresses(this.customerAddresses(request.getCsId()));
        return response;
    }

    private CsOverview customerRead(Long csId) {
        CsOverview customer = new CsOverview();
        CustomerReadRequest customerReadRequest = new CustomerReadRequest();
        InputAttributes inputAttributes = new InputAttributes();

        inputAttributes.setCsId(csId);
        inputAttributes.setSyncWithDb(true);
        customerReadRequest.setInputAttributes(inputAttributes);

        CustomerReadResponse readResponse = this.customerReadClient
                .execute(customerReadRequest, "ADMX", "ADMX");

        customer.setCsIdPub(readResponse.getCsIdPub());
        customer.setCsCode(readResponse.getCsCode());
        customer.setCsPassword(readResponse.getCsPassword());
        customer.setCsBillcycle(readResponse.getCsBillcycleDesc());
        customer.setCsStatusDate(readResponse.getCsStatusDate()
                .toGregorianCalendar()
                .toZonedDateTime()
                .toLocalDateTime());

        return customer;
    }

    private List<CsOverviewContract> customerContracts(Long csId) {
        List<CsOverviewContract> contracts = new ArrayList<>();
        ContractsSearchResponse searchResponse;
        ContractsSearchRequest searchRequest = new ContractsSearchRequest();
        com.ericsson.contractssearch.InputAttributes inputAttributes = new com.ericsson.contractssearch.InputAttributes();

        inputAttributes.setCsId(csId);
        inputAttributes.setSearcher("SimpleContractSearch");
        searchRequest.setInputAttributes(inputAttributes);

        searchResponse = this.contractsSearchClient.execute(searchRequest, "ADMX", "ADMX");

        if (searchResponse.getContracts() != null && !searchResponse.getContracts().getItem().isEmpty()) {
            searchResponse.getContracts().getItem()
                    .forEach(contract -> {
                        CsOverviewContract csOverviewContract = new CsOverviewContract();
                        csOverviewContract.setCoId(contract.getCoId());
                        csOverviewContract.setCoIdPub(contract.getCoIdPub());
                        csOverviewContract.setCoStatus(Integer.parseInt(contract.getCoStatus().toString()));
                        csOverviewContract.setDirNum(contract.getDirnum());
                        csOverviewContract.setRpCode(contract.getRpcode());
                        csOverviewContract.setRateplan(this.commonsService.getRateplan(contract.getRpcode()));
                        if (contract.getCoActivated() != null) {
                            csOverviewContract.setCoActDate(contract.getCoActivated()
                                    .toGregorianCalendar()
                                    .toZonedDateTime()
                                    .toLocalDateTime());
                        }
                        contracts.add(csOverviewContract);
                    });
        }
        return contracts;
    }

    private List<CsOverviewAddress> customerAddresses(Long csId) {
        List<CsOverviewAddress> customerAddresses = new ArrayList<>();
        AddressesReadResponse readResponse;
        AddressesReadRequest readRequest = new AddressesReadRequest();
        com.ericsson.addreessesread.InputAttributes inputAttributes = new com.ericsson.addreessesread.InputAttributes();

        inputAttributes.setCsId(csId);
        readRequest.setInputAttributes(inputAttributes);

        readResponse = this.addressesReadClient.execute(readRequest, "ADMX", "ADMX");

        if (readResponse.getListOfAllAddresses() != null) {
            readResponse.getListOfAllAddresses().getItem().
                    forEach(address -> {
                        CsOverviewAddress csAddress = new CsOverviewAddress();

                        csAddress.setAdrSeq(address.getAdrSeq());
                        csAddress.setAdrCity(address.getAdrCity());
                        csAddress.setAdrStreet(address.getAdrStreet());
                        csAddress.setAdrLName(address.getAdrLname());
                        csAddress.setAdrFame(address.getAdrFname());
                        csAddress.setCountryId(address.getCountryId());
                        csAddress.setTtlId(address.getTtlId());
                        csAddress.setAdrZip(address.getAdrZip());
                        csAddress.setAdrNationality(csAddress.getAdrNationality());
                        csAddress.setDocTypeId(address.getIdtypeCode());
                        csAddress.setAdrEmail(address.getAdrEmail());
                        csAddress.setIdNo(address.getAdrIdno());
                        if (address.getAdrBirthdt() != null) {
                            csAddress.setAdrBirthDate(address.getAdrBirthdt().toGregorianCalendar().toZonedDateTime().toLocalDate());
                        }
                        customerAddresses.add(csAddress);
                    });
        }
        return customerAddresses;
    }






}
