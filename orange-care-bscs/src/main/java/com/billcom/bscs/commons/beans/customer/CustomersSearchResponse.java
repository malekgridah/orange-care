package com.billcom.bscs.commons.beans.customer;

import lombok.Data;

import java.util.List;

@Data
public class CustomersSearchResponse {
    List<CustomersSearch> customers;
}
