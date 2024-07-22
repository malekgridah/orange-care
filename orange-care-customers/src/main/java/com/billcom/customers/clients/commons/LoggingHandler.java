package com.billcom.customers.clients.commons;

import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Set;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger log = LogManager.getLogger(LoggingHandler.class);

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        try {
            logSOAPMessage(smc);
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean handleFault(SOAPMessageContext smc) {
        try {
            logSOAPMessage(smc);
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void close(MessageContext messageContext) {
    }

    private void logSOAPMessage(SOAPMessageContext smc) throws SOAPException {

        Boolean isOutbound = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        log.debug("{} SOAP Message :\n{}",isOutbound ? "Outbound" : "Inbound",
                getFormattedMessageAsString(smc.getMessage()));
    }

    private String getFormattedMessageAsString(SOAPMessage message) throws SOAPException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(message.getSOAPPart().getEnvelope());
            java.io.StringWriter sw = new java.io.StringWriter();
            StreamResult result = new StreamResult(sw);
            transformer.transform(source, result);
            return sw.toString();
        } catch (Exception e) {
            throw new SOAPException("Error formatting SOAP message", e);
        }
    }
}

