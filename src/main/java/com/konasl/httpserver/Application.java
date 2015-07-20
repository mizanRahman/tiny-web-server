package com.konasl.httpserver;

import com.konasl.httpserver.config.Configuration;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        new HttpServer(Configuration.port).start();
    }
}
