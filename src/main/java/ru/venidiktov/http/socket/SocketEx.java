package ru.venidiktov.http.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

@Slf4j
public class SocketEx {
    public static void main(String[] args) throws IOException {
        /**
         * При создании Socket (Гнездо) можно указать DNS имя ресурса и TCP порт (так как Socket работает с TCP)
         * службы к которой хотим обратиться
         * После использования необходимо закрывать ресурс Socket (так как Socket реализует интерфейс Closeable)!
         *
         * Socket для отправки и получения информации использует пакет java.io
         * OutputStream для отправки запроса (После использования необходимо закрывать ресурс)
         * InputStream для получения ответа (После использования необходимо закрывать ресурс)
         *
         * InetAddress - класс для удобного представления адресов ip4 или ip6
         *
         * По умолчанию порт для http = 80, https = 443
         *
         * В данном примере получаем в ответ 301 и соединение сразу не закрывается
         */
        InetAddress inetAddressGitHub = Inet4Address.getByName("github.com");
        try (var socket = new Socket(inetAddressGitHub, 80);
             var printWriter = new PrintWriter(socket.getOutputStream()); //удобнее работать со стримами через обертку, например PrintWriter
             var bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) { //удобнее работать со стримами через обертку, например BufferedReader
            //Составляем запрос к серверу руками
            var request = """
                    GET /VenidiktovGA HTTP/1.1
                    Host: %s
                    """.formatted(inetAddressGitHub.getHostName());
            log.warn("---- Запрос на сервер ----\n {}", request);
            printWriter.println(request);
            printWriter.flush();

            log.info("---- Читаем ответ от сервера ----");
            String outputString;
            while ((outputString = bufferedReader.readLine()) != null) {
                log.info(outputString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
