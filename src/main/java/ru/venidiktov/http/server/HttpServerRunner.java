package ru.venidiktov.http.server;

public class HttpServerRunner {
    public static void main(String[] args) {
        var httpServer = new HttpServer(9009);
        httpServer.run();
    }
}
