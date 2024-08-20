package com.billcom.contracts.services;

import com.billcom.contracts.beans.create.ContractCreateRequest;
import com.billcom.contracts.beans.create.ContractCreateResponse;
import org.springframework.stereotype.Service;

@Service
public class CreateContractService {


    public ContractCreateResponse createContract(ContractCreateRequest request) {
        return new ContractCreateResponse();
    }
}

