package com.billcom.payment.clients.rest;

import com.billcom.payment.commons.bscs.RestRequest;
import com.billcom.payment.commons.bscs.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.DatatypeConverter;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@Component
public class RestWSClient {
  private static final Logger logger = LogManager.getLogger(RestWSClient.class);
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

  private String wsUrl;
  private String userName;
  private String password;


  private HttpHeaders buildHttpRestHeader(final String userName, final String password) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    String auth = userName + ":" + password;
    String s = DatatypeConverter.printBase64Binary(auth.getBytes());
    headers.add(HTTP_HEADER_AUTHORIZATION, "Basic " + s);
    return headers;
  }

  public RestResponse callRestWebService(Map<String, String> mapRequest) {

    RestTemplate restTemplate = new RestTemplate();
    RestRequest request = new RestRequest();
    request.setParams(mapRequest);
    HttpHeaders headers = buildHttpRestHeader(getUserName(), getPassword());
    String requestBody = null;
    try {
      requestBody = mapper.writeValueAsString(request);
        logger.error("Request body: {}", requestBody);
    } catch (JsonProcessingException e) {
      logger.error("Error occurred when preparing request", e);
    }
    HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> result = restTemplate.exchange(wsUrl, HttpMethod.PUT, httpEntity, String.class);
    RestResponse response = null;
      try {
          response = mapper.readValue(result.getBody(), RestResponse.class);
          logger.info("web service result: response.isSuccessful {}", response.isSuccessful());
      } catch (IOException e) {
          logger.error("Error occurred when mapping object", e);
      }
      return response;
  }
}
