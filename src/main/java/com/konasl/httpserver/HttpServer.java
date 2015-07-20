package com.konasl.httpserver;

import com.konasl.httpserver.messages.HttpRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by Mizan on 2/23/2015.
 */
public class HttpServer extends NioServer {

    protected HttpServer(int port) {
        super(port);
    }

    public void start() {
        Thread eventLoop = new Thread(this);
        eventLoop.start();
    }

    @Override
    protected void messageReceived(MessageExchange exchange) throws IOException {
        HttpMessageExchange httpMessageExchange = new HttpMessageExchange(exchange);
        String requestMessage = new String(exchange.getRequestMessageBuffer().array(), StandardCharsets.UTF_8);
        HttpRequest request = new HttpRequest(requestMessage);
        HttpRequestHandler requestHandler = new HttpRequestHandlerImpl();
        requestHandler.handle(httpMessageExchange, request);
    }

    @Override
    protected void started() {
        System.out.println("Server started @" + new Date());

    }

    @Override
    protected void stopped() {
        System.out.println("Server stopped @" + new Date());
    }
}
