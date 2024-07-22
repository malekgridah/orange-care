package com.billcom.bscs.beans;

import lombok.Data;

import java.util.List;

@Data
public class CurrenciesResponse {
    List<Currency> currencies;
}
