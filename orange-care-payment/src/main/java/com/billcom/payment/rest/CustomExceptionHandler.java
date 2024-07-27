package com.billcom.payment.rest;

import com.billcom.payment.commons.beans.BaseWSResponse;
import com.billcom.payment.commons.exceptions.*;
import com.billcom.payment.utils.I18nErrorMessages;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvokeClientException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseWSResponse> handleInvokeClientException(InvokeClientException exception) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(exception.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode("Client Exception");
        log.error(exception);
        exception.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseWSResponse> handleBadRequestException(BadRequestException exception) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(exception.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode("Client Exception");
        log.error(exception);
        exception.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MandatoryInputParameterException.class)
    public ResponseEntity<BaseWSResponse> handleMandatoryInputValueException(MandatoryInputParameterException ex) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(ex.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode(I18nErrorMessages.MANDATORY_OBJECT);
        log.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AtLeastMandatoryInputValueException.class)
    public ResponseEntity<BaseWSResponse> handleAtLeastMandatoryInputValueException(AtLeastMandatoryInputValueException ex) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(ex.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode(ex.getErrorCode());
        log.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationAlreadyDoneException.class)
    public ResponseEntity<BaseWSResponse> handleOperationAlreadyDoneException(OperationAlreadyDoneException ex) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(ex.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode(ex.getErrorCode());
        log.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<BaseWSResponse> handleDataNotFoundException(DataNotFoundException ex) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(ex.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode("NotFound");
        log.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputValueException.class)
    public ResponseEntity<BaseWSResponse> handleInvalidInputValueException(InvalidInputValueException ex) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(ex.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode("NotFound");
        log.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseWSResponse> handleRuntimeException(RuntimeException ex) {
        BaseWSResponse baseWSResponse = new BaseWSResponse();
        baseWSResponse.setComment(ex.getMessage());
        baseWSResponse.setIsSuccessful(Boolean.FALSE);
        baseWSResponse.setErrorCode(I18nErrorMessages.TECHNICAL_PROBLEM);
        log.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }
}
