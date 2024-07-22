package com.billcom.contracts.beans.overview;

import lombok.Data;

import java.util.List;

@Data
public class ContractResources {
    private String smSerialNum;
    private String portNum;
    private List<ContractDirectoryNumbers> dirNums;
}
