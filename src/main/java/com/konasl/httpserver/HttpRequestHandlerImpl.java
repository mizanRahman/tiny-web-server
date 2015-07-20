package com.konasl.httpserver;

import com.konasl.httpserver.config.Configuration;
import com.konasl.httpserver.messages.HttpRequest;
import com.konasl.httpserver.messages.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Mizan on 2/23/2015.
 */
public class HttpRequestHandlerImpl implements HttpRequestHandler {

    @Override
    public void handle(HttpMessageExchange exchange, HttpRequest request) throws IOException {
        byte[] contentBytes = null;
        if (request.getMethod().equals(HttpRequest.RequestMethod.GET)) {
            String filePath = Configuration.rootDirectory + request.getUri();
            Path path = null;
            if (request.getUri().equals("/")) {
                filePath = filePath.concat(Configuration.DEFAULT_HTML_FILE);
            }
            if (new File(filePath).exists() == false) {
                filePath = Configuration.rootDirectory + Configuration.NOT_FOUND_HTML_FILE;
            }
            path = Paths.get(filePath);
            contentBytes = Files.readAllBytes(path);
        }

        HttpResponse response = new HttpResponse();
        response.setVersion(Configuration.HTTP_VERSION);
        response.setHeader("Content-Type", "text/html");
        response.setResponseCode(200);
        response.setResponseReason("OK");
        response.setContent(contentBytes);
        exchange.sendResponse(response);
    }
}
