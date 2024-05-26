package ru.venidiktov.http.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Класс URL отображает URL в http
 */
@Slf4j
public class UrlEx {
    public static void main(String[] args) throws IOException {
        checkGoogle();
        log.info("\n");

        /**
         * Через URL мы можем обращаться к файлам на машине,
         * но есть более удобные варианты для работы с файлами
         */
        var fileUrl = new URL("file:./src/main/java/ru/venidiktov/http/socket/_1SocketEx.java");
        URLConnection urlConnection = fileUrl.openConnection();
        log.info("Содержимое файла _1SocketEx.java");
        log.info(new String(urlConnection.getInputStream().readAllBytes()));
    }

    private static void checkGoogle() throws IOException {
        var googleUrl = new URL("https://google.com");
        URLConnection urlConnection = googleUrl.openConnection();

        /**
         * Класс URL по умолчанию работает с GET методом, можно и работать с другими методами но не очень удобно
         */
//        urlConnection.setDoInput(true); //Говорим что мы хотим отправить тело в запроса
//        try(var outputStream = urlConnection.getOutputStream()) {
//            outputStream.write();// Пишем данные в тело запроса
//        }

        log.info("google.com ответил с кодом {}", ((HttpURLConnection) urlConnection).getResponseCode());
    }
}
