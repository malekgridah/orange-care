package com.billcom.payment.commons.bscs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class RestResponse {

    private List<Map<String, Object>> rows;
    private boolean successful;
    private String comment;

    public RestResponse() {
        super();
        this.rows = new ArrayList<>();
        this.comment = "";
    }

}
