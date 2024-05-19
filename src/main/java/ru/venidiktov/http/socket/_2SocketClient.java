package ru.venidiktov.http.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class _2SocketClient {
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
        InetAddress localhostAddress = Inet4Address.getByName("localhost");
        try (var socket = new Socket(localhostAddress, 9999);
             var outputRequest = new DataOutputStream(socket.getOutputStream()); //удобнее работать со стримами через обертку, например DataOutputStream
             var inputResponse = new DataInputStream(socket.getInputStream()); //удобнее работать со стримами через обертку, например BufferedReader
             var scanner = new Scanner(System.in)) {

            log.info("Введите запрос: ");
            while (scanner.hasNextLine()) {
                var request = scanner.nextLine();
                outputRequest.writeUTF(request);
                log.info("---- Ответ от сервера ----\n{}", inputResponse.readUTF());
                log.info("Введите запрос: ");
            }
        } catch (IOException e) {
            /**
             * При отправке на сервер слова 'stop' сервер закроет соединение и тут на стороне
             * клиент будет выброшен java.io.EOFException!
             */
            throw new RuntimeException(e);
        }
    }
}
