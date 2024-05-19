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
public class _1SocketEx {
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
        InetAddress githubAddress = Inet4Address.getByName("github.com");
        try (var socket = new Socket(githubAddress, 80);
             var printWriterRequestOut = new PrintWriter(socket.getOutputStream()); //удобнее работать со стримами через обертку, например PrintWriter
             var bufferedReaderResponseIn = new BufferedReader(new InputStreamReader(socket.getInputStream()))) { //удобнее работать со стримами через обертку, например BufferedReader
            //Составляем запрос к серверу руками
            var request = """
                    GET /VenidiktovGA HTTP/1.1
                    Host: %s
                    """.formatted(githubAddress.getHostName());
            log.info("---- Запрос на сервер ----\n {}", request);
            printWriterRequestOut.println(request);
            printWriterRequestOut.flush();

            log.info("---- Ответ от сервера ----");
            String outputString;
            while ((outputString = bufferedReaderResponseIn.readLine()) != null) {
                log.info(outputString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
