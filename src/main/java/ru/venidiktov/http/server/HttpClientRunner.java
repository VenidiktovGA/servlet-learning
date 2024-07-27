package ru.venidiktov.http.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class HttpClientRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1).build()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:9009"))
                    .header("content-type", "text/plain")
                    .header("Accept", "*/*")
                    .version(HttpClient.Version.HTTP_1_1)
                    .POST(HttpRequest.BodyPublishers.ofString("Hello i'm request!"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Response headers: {}", response.headers());
            log.info("Response body: {}", response.body());
        }
    }
}
