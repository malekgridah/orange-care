package com.billcom.payment.commons.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CustomerReference {

    public CustomerReference() {}

    private Long csId;
    private String csIdPub;
    private List<Long> csIds;

    public CustomerReference(Long id, String idPub)
    {
        csId = id;
        csIdPub = idPub;
    }

    @Override
    public String toString() {
        return "CustomerReference [csId=" + csId + ", csIdPub=" + csIdPub + ", csIds=" + csIds + "]";
    }
}
