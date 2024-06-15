package com.billcom.payment.commons.bscs;

import lombok.Data;

import java.util.Map;

@Data
public class RestRequest {

	private Map<String, String> params;

}
