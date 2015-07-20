package com.konasl.httpserver.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2/24/2015.
 */
public class HttpRequest {

    private final String raw;
    private String method;
    private String uri;
    private String version;
    private Map<String, String> headers = new HashMap<String, String>();

    public static class RequestMethod {
        public static String GET = "GET";
        public static String POST = "POST";
        public static String PUT = "PUT";
        public static String DELETE = "DELETE";
    }

    public HttpRequest(String raw) {
        this.raw = raw;
        parse();
    }

    private void parse() {
        String[] lines = raw.split("\r\n");
        // parse the first line
        String[] firstHeader = lines[0].split(" ", 3);
        method = firstHeader[0].toUpperCase();
        uri = firstHeader[1];
        version = firstHeader[2];

        // parse the headers
        for (int i = 1; i < lines.length; i++) {
            String[] keyVal = lines[i].split(":", 2);
            if (keyVal.length == 2) {
                headers.put(keyVal[0], keyVal[1]);
            }
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getHead(String key) {
        return headers.get(key);
    }
}
