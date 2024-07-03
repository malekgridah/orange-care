package com.billcom.bscs.commons.beans.contract.search;

import lombok.Data;

import java.util.List;

@Data
public class ContractsSearchResponse {
    List<ContractSearch> contracts;
}
