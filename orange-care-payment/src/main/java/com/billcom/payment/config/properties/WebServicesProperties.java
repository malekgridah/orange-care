package com.billcom.payment.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "payment.endpoints")
public class WebServicesProperties {

    private BscsProperties bscs;
    private WsiProperties wsi;
    private OtherProperties other;

    @Data
    public static class BscsProperties {
        private EndpointProperties contractHandling;
        private EndpointProperties customerHandling;
    }

    @Data
    public static class WsiProperties {
        private FinancialAllocationProperties financialAllocation;
        private FinancialDocumentProperties financialDocument;
    }

    @Data
    public static class OtherProperties {
        private EndpointProperties smsNotifier;
        private EndpointProperties restExecutor;

    }

    @Data
    public static class FinancialAllocationProperties {
        private EndpointProperties write;
        private EndpointProperties reverse;
    }

    @Data
    public static class FinancialDocumentProperties {
        private EndpointProperties search;
        private EndpointProperties read;
        private EndpointProperties detailRead;
    }

    @Data
    public static class EndpointProperties {
        private String url;
        private String username;
        private String password;
    }
}





