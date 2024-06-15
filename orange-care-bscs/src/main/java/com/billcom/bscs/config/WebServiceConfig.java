package com.billcom.bscs.config;//package com.billcom.bscs.config;
//
//import com.billcom.payment.commons.domains.postgres.User;
//import com.billcom.payment.commons.repositories.postgres.UserRepository;
//import com.billcom.payment.soap.ws.PaymentApiSoapWSServiceIpml;
//import com.billcom.payment.utils.I18nErrorMessages;
//import jakarta.xml.ws.Endpoint;
//import lombok.extern.log4j.Log4j2;
//import org.apache.cxf.Bus;
//import org.apache.cxf.ext.logging.LoggingFeature;
//import org.apache.cxf.ext.logging.LoggingInInterceptor;
//import org.apache.cxf.ext.logging.LoggingOutInterceptor;
//import org.apache.cxf.ext.logging.event.PrettyLoggingFilter;
//import org.apache.cxf.ext.logging.slf4j.Slf4jEventSender;
//import org.apache.cxf.interceptor.security.AuthenticationException;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.metrics.MetricsProvider;
//import org.apache.cxf.ws.security.SecurityConstants;
//import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
//import org.apache.wss4j.common.ext.WSPasswordCallback;
//import org.apache.wss4j.dom.WSConstants;
//import org.apache.wss4j.dom.handler.WSHandlerConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.security.auth.callback.Callback;
//import javax.security.auth.callback.CallbackHandler;
//import javax.security.auth.callback.UnsupportedCallbackException;
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//@Log4j2
//@Configuration
//public class WebServiceConfig{
//
////    private final PaymentApi paymentApi;
//    private final PaymentApiSoapWSServiceIpml paymentApiSoap;
//    private final Bus bus;
//    private final MetricsProvider metricsProvider;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public WebServiceConfig(PaymentApiSoapWSServiceIpml paymentApiSoap,
//                            Bus bus,
//                            MetricsProvider metricsProvider,
//                            UserRepository userRepository) {
////        this.paymentApi = paymentApi;
//        this.paymentApiSoap = paymentApiSoap;
//        this.bus = bus;
//        this.metricsProvider = metricsProvider;
//        this.userRepository = userRepository;
//    }
//
//    @Bean
//    public Endpoint paymentEndpoint() {
//        final EndpointImpl endpoint = new EndpointImpl(bus, paymentApiSoap);
//        endpoint.publish("/PaymentApi");
//        endpoint.getInInterceptors().add(inInterceptor());
////        endpoint.getInInterceptors().add(loggingInInterceptor());
////        endpoint.getInFaultInterceptors().add(loggingInInterceptor());
////        endpoint.getOutInterceptors().add(loggingOutInterceptor());
////        endpoint.getOutFaultInterceptors().add(loggingOutInterceptor());
////        endpoint.getFeatures().add(loggingFeature());
//        endpoint.getProperties().put(SecurityConstants.ENABLE_NONCE_CACHE,"false");
//        endpoint.getProperties().put(SecurityConstants.ENABLE_TIMESTAMP_CACHE,"false");
//        endpoint.getProperties().put(SecurityConstants.ENABLE_SAML_ONE_TIME_USE_CACHE,"false");
//
//        return endpoint;
//    }
//
//    @Bean
//    public WSS4JInInterceptor inInterceptor() {
//        return new WSS4JInInterceptor(wss4jProperties());
//    }
//
//    @Bean
//    public CallbackHandler passwordCallbackHandler() {
//        return callbacks -> {
//            for (Callback callback : callbacks) {
//                if (callback instanceof WSPasswordCallback passwordCallback) {
//                    String username = passwordCallback.getIdentifier();
//                    User user = userRepository.findUserByLogin(username)
//                            .orElseThrow(() -> new AuthenticationException(this.setAuthException(I18nErrorMessages.AUTHENTICATION_USER_NOT_FOUND, username)));
//                    if (user.getLogin().equals(username) && user.getEnabled().equals(1)) {
//                        passwordCallback.setPassword(user.getPassword());
//                        log.error("password is "+ passwordCallback.getPassword());
//                        if (!passwordCallback.getPassword().equals(user.getPassword())) {
//                            throw new AuthenticationException(this.setAuthException(I18nErrorMessages.AUTHENTICATION_WRONG_PASSWORD, username));
//                        }
//                        return;
//                    } else {
//                        throw new AuthenticationException(this.setAuthException(I18nErrorMessages.AUTHENTICATION_USER_DISABLED, username));
//                    }
//                }
//                throw new UnsupportedCallbackException(callbacks[0], "Unrecognized Callback");
//            }
//        };
//    }
//
//    private String setAuthException(String errorCode,Object ...errorArgs) {
//        Locale locale = Locale.getDefault();
//        ResourceBundle messages=ResourceBundle.getBundle(I18nErrorMessages.RESOURCE_BUNDLE_NAME, locale);
//        String errorComment = MessageFormat.format(messages.getString(errorCode), errorArgs);
//        return errorCode + " : " + errorComment;
//    }
//
//    public Map<String, Object> wss4jProperties() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
//        properties.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
//        properties.put(WSHandlerConstants.PW_CALLBACK_REF, passwordCallbackHandler());
//        properties.put(WSHandlerConstants.TIMESTAMP_PRECISION, "seconds");
//        properties.put(WSHandlerConstants.TTL_USERNAMETOKEN, "1500");
//        return properties;
//    }
//
//    @Bean
//    public LoggingOutInterceptor loggingOutInterceptor() {
//        LoggingOutInterceptor interceptor = new LoggingOutInterceptor(new PrettyLoggingFilter(new Slf4jEventSender()));
//        interceptor.setPrettyLogging(true);
//        return interceptor;
//    }
//
//    @Bean
//    public LoggingInInterceptor loggingInInterceptor() {
//        LoggingInInterceptor interceptor = new LoggingInInterceptor(new PrettyLoggingFilter(new Slf4jEventSender()));
//        interceptor.setPrettyLogging(true);
//        return interceptor;
//    }
//
//    @Bean
//    public LoggingFeature loggingFeature() {
//        LoggingFeature interceptor = new LoggingFeature();
//        interceptor.setPrettyLogging(true);
//        return interceptor;
//    }
//
//
//}
