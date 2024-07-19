package com.billcom.financials.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "financial.endpoints")
public class WebServicesProperties {
    private WsiProperties wsi;

    @Data
    public static class WsiProperties {
        private FinancialTransactionProperties financialTransaction;
        private FinancialDocumentProperties financialDocument;
    }

    @Data
    public static class FinancialTransactionProperties {
        private EndpointProperties search;
        private EndpointProperties read;
        private EndpointProperties detailRead;
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
