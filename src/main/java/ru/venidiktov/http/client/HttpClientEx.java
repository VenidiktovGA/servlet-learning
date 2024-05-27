package ru.venidiktov.http.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Класс HttpClient был сделан для того что бы выступать в роли http клиента к http серверу
 * Класс HttpClient потокобезопасный, один экземпляр можно использовать во всем приложении!
 */
@Slf4j
public class HttpClientEx {
    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * HttpClient удобно настраивается через паттерн builder
         * Метод newHttpClient() создает http клиента с дефолтными настройками
         */
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1) //По умолчанию версия 2
                .build();

        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://google.com"))
                .GET() // Можно выбрать метод запроса, например .POST(HttpRequest.BodyPublishers.ofString())
                .build();
        /**
         * Метод sendAsync() отправляет запрос асинхронно используя CompletableFuture
         * BodyHandlers это метод который обрабатывает ответ от сервера, можно создавать свои BodyHandlers
         */
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        log.info("Ответ от google.com пришел со статусом = {}", response.statusCode());
    }
}
