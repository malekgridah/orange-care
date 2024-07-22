package com.billcom.customers.beans.overview;

import lombok.Data;

import java.util.List;

@Data
public class CustomerOverviewResponse {
    private Long csId;
    private CsOverview customer;
    private List<CsOverviewAddress> addresses;
    private List<CsOverviewContract> contacts;
}
