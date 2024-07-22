package com.billcom.customers.beans.search;

import lombok.Data;

import java.util.List;

@Data
public class CustomersSearchResponse {
    List<CustomersSearch> customers;
}
