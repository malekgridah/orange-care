package com.billcom.payment.clients.soap.commons;

import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ClientResolver implements HandlerResolver {

    private String user;
    private String pass;

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new ClientHandler(user,pass));
        handlerChain.add(new LoggingHandler());
        return handlerChain;
    }


}
