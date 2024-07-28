package ru.venidiktov.http.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class MultiThreadHttpClientRunner {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        try (var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1).build()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9009"))
                    .header("content-type", "text/plain")
                    .header("Accept", "*/*")
                    .version(HttpClient.Version.HTTP_1_1)
                    .POST(HttpRequest.BodyPublishers.ofString("Hello i'm request!"))
                    .build();

            CompletableFuture<HttpResponse<String>> response1 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            CompletableFuture<HttpResponse<String>> response2 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            CompletableFuture<HttpResponse<String>> response3 = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            log.info("Response1 body: {}", response1.get().body()); //Для того что бы ожидать ответ по запросу
            log.info("Response2 body: {}", response2.get().body()); //Для того что бы ожидать ответ по запросу
            log.info("Response3 body: {}", response3.get().body()); //Для того что бы ожидать ответ по запросу
        }
    }
}
