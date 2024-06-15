package com.billcom.payment.utils;

public interface PaymentApiSettingProperties {

   String INIT_TRANSACTION = "payment.setting.transaction.new.status";
   String SUCCESS_TRANSACTION = "payment.setting.transaction.success.status";
   String FAILED_TRANSACTION = "payment.setting.transaction.failed.status";
   String BSCS_JNDI_NAME = "payment.setting.jdbc.bscs";
   String POSTGRES_JNDI_NAME = "payment.setting.jdbc.postgres";
   String RETRY_FAILED_JOB = "payment.setting.retry.failed.job.fixed.delay";
   String REST_EXECUTOR_QUERY_ID = "payment.setting.rest.executor.query.id";
   String REVERSE_HANDLING_REASON = "payment.setting.reverse.handling.reason.pub";
   String REST_EXECUTOR_CUSTOMER_QUERY_ID = "payment.setting.rest.executor.customer.query.id";
   String PRGCODE_INCLUDE = "payment.setting.prgcode.include";
   String PRGCODE_EXCLUDE = "payment.setting.prgcode.exclude";
   String APP_LOG_LEVEL = "payment.setting.log.level";
}
