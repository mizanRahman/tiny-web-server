package com.konasl.httpserver.exception;

/**
 * Created by Mizan on 2/24/2015.
 */
public class EventLoopIOException extends RuntimeException {
    public EventLoopIOException(String s) {
        super(s);
    }
}
