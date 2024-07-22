package com.billcom.contracts.clients.commons;

import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class ClientHandler implements SOAPHandler<SOAPMessageContext> {
    private static final Logger log = LogManager.getLogger(ClientHandler.class);
    private String user;
    private String pass;

    @Override
    public Set<QName> getHeaders() {
        return new HashSet<>();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        log.info("Handling SOAP message...");

        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (isOutbound) {
            try {

                SOAPMessage soapMessage = context.getMessage();
                SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();

                String prefix ="wsse";
                String uri="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

                // Create UsernameToken SOAP header
                SOAPFactory soapFactory = SOAPFactory.newInstance();
                QName securityHeaderQName = new QName(uri, "Security", "wsse");
                SOAPHeaderElement securityHeaderElement = header.addHeaderElement(securityHeaderQName);

                QName usernameTokenQName = new QName(uri, "UsernameToken", "wsse");
                SOAPElement usernameTokenElement = securityHeaderElement.addChildElement(usernameTokenQName);
                usernameTokenElement.addAttribute(QName.valueOf("wsu:Id"), "UsernameToken-2");
                usernameTokenElement.addAttribute(QName.valueOf("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

                QName usernameQName = new QName(uri, "Username", "wsse");
                SOAPElement usernameElement = usernameTokenElement.addChildElement(usernameQName);
                usernameElement.addTextNode(user);

                QName passwordQName = new QName(uri, "Password", "wsse");
                SOAPElement passwordElement = usernameTokenElement.addChildElement(passwordQName);
                passwordElement.addAttribute(QName.valueOf("Type"),
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                passwordElement.addTextNode(pass);

                soapMessage.saveChanges();


            }catch (SOAPException e) {
                log.error("Error processing outbound message", e);
                throw new RuntimeException(e);
            }
        }else {
            log.debug("Processing inbound message...");
            //inbound
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext soapMessageContext) {
        log.error("Handling SOAP fault");
        return false;
    }

    @Override
    public void close(MessageContext messageContext) {
        log.debug("Closing SOAP message handling");
    }
}
