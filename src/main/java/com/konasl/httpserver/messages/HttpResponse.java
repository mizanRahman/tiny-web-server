package com.konasl.httpserver.messages;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2/24/2015.
 */
public class HttpResponse {

    private String version;
    private int responseCode;
    private String responseReason;
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private byte[] content;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseReason() {
        return responseReason;
    }

    public void setResponseReason(String responseReason) {
        this.responseReason = responseReason;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }


}
