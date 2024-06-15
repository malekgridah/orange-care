package com.billcom.payment.service;

import com.billcom.payment.commons.beans.*;
import com.billcom.payment.commons.dtos.postgres.LogDto;
import com.billcom.payment.commons.mappers.postgres.LogMapper;
import com.billcom.payment.commons.repositories.postgres.*;
import com.billcom.payment.utils.I18nErrorMessages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraceLogService {

    private static final Logger logger= LogManager.getLogger(TraceLogService.class);
    private final LogRepository logRepository;
    private final LogMapper logMapper;

    @Autowired
    public TraceLogService(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    public TraceLogResponse traceLog(TraceLogRequest traceLogReq) {

        TraceLogResponse traceLogResponse = new TraceLogResponse();

        if (traceLogReq.getTraceLogBean() == null) {
            traceLogResponse.setComment("request vide");
            traceLogResponse.setErrorCode(I18nErrorMessages.TECHNICAL_PROBLEM);
            traceLogResponse.setIsSuccessful(false);
            return traceLogResponse;
        }

        LogDto logDto = LogDto.builder()
                .description(traceLogReq.getTraceLogBean().getDescription())
                .errorCode(traceLogReq.getTraceLogBean().getErrorCode())
                .logDate(traceLogReq.getTraceLogBean().getDate())
                .montant(traceLogReq.getTraceLogBean().getAmount())
                .operation(traceLogReq.getTraceLogBean().getOperation())
                .platform(traceLogReq.getTraceLogBean().getPlatform())
                .rechargeId(traceLogReq.getTraceLogBean().getRechargeId())
                .refFacture(traceLogReq.getTraceLogBean().getRefFacture())
                .status(traceLogReq.getTraceLogBean().getStatus())
                .trsId(traceLogReq.getTraceLogBean().getTrsId())
                .userLog(traceLogReq.getTraceLogBean().getUser())
                .build();

        logRepository.save(logMapper.toEntity(logDto));

        traceLogResponse.setComment("");
        traceLogResponse.setErrorCode("0");
        traceLogResponse.setIsSuccessful(Boolean.TRUE);

        return traceLogResponse;
    }
}
