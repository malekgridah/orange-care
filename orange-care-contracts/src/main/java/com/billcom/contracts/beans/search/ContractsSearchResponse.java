package com.billcom.contracts.beans.search;

import lombok.Data;

import java.util.List;

@Data
public class ContractsSearchResponse {
    List<ContractSearch> contracts;
}
