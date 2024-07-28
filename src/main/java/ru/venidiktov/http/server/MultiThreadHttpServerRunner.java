package ru.venidiktov.http.server;

public class MultiThreadHttpServerRunner {
    public static void main(String[] args) {
        var httpServer = new MultiThreadServer(9009, 100);
        httpServer.run();
    }
}
