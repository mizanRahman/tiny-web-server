package com.konasl.httpserver;

import com.konasl.httpserver.messages.HttpResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Created by Lenovo on 2/24/2015.
 */
public class HttpMessageExchange {

    private MessageExchange messageExchange;
    private CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();

    public HttpMessageExchange(MessageExchange messageExchange) {
        this.messageExchange = messageExchange;
    }

    public void sendResponse(HttpResponse response) throws IOException {
        writeLine(response.getVersion() + " " + response.getResponseCode() + " " + response.getResponseReason());
        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            writeLine(header.getKey() + ": " + header.getValue());
        }
        writeLine("");
        messageExchange.getChannel().write(ByteBuffer.wrap(response.getContent()));
    }

    private void writeLine(String line) throws IOException {
        messageExchange.getChannel().write(encoder.encode(CharBuffer.wrap(line + "\r\n")));
    }


}
