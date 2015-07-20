package com.konasl.httpserver;

import com.konasl.httpserver.messages.HttpRequest;

import java.io.IOException;

/**
 * Created by Mizan on 2/23/2015.
 */
public interface HttpRequestHandler {
    public void handle(HttpMessageExchange exchange, HttpRequest request) throws IOException;
}
