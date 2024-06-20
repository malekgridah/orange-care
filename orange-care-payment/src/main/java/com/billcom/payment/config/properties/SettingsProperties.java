package com.billcom.payment.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "payment.settings")
public class SettingsProperties {
    private TransactionProperties transaction;
    private RestExecutorProperties restExecutor;
    private SuppressAllocProperties suppressAlloc;
    private PrgCodeProperties prgcode;

    private String reasonHandlingIdPub;
    private String retryFailedJobDelay;
    private String documentSearchCount;
    private String logLevel;
    private List<String> entityCodeChannels;

    @Data
    public static class TransactionProperties {
        private String newStatus;
        private String failedStatus;
        private String successStatus;
    }

    @Data
    public static class RestExecutorProperties {
        private String queryId;
        private String customerQueryId;
    }

    @Data
    public static class SuppressAllocProperties {
        private String payment;
        private String otherCases;
        private String advancePayment;
    }

    @Data
    public static class PrgCodeProperties {
        private List<String> include;
        private List<String> exclude;
    }
}
