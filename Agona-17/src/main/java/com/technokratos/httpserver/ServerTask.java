package com.technokratos.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class ServerTask {

    public static void main(String[] args) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/", exchange ->
                Thread.startVirtualThread(() -> handleRequest(exchange)));
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) {
        try {
            System.out.println("Handling request in thread: %s".formatted(Thread.currentThread()));
            String response = "Hello from virtual thread!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(response.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}