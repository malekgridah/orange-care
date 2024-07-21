package com.billcom.financials.commons.beans.document;

import com.billcom.financials.commons.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentDetailsResponse extends OperationResponse {
    private DocumentDetails documentDetails;
}
