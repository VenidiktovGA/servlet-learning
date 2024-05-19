package ru.venidiktov.http.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * Реализуем сервер используя Socket
 */
@Slf4j
public class _2SocketServer {
    public static void main(String[] args) {
        /**
         * Для создания сервера есть класс ServerSocket, host созданного сервера будет таким же как и хост машины на
         * которой развернут сервер!
         * В качестве параметра в ServerSocket нужно передать порт на котором будет работать сервер, так же можно передать
         * количество соединений которое может одновременно держать сервер (по умолчанию 50)
         * ServerSocket необходимо закрывать после использования
         *
         * Метод accept() работает в блокирующем режиме, он ждет пока к нему не поступит запрос на установление
         * соединения (Клиент использующий класс Socket) далее происходит установка соединения и обработка запроса.
         * Для того чтобы сервер мог обрабатывать одновременно много запросов нужно запускать его используя многопоточность!
         */
        log.info("Сервер запушен и ждет запросов!");
        try (var serverSocket = new ServerSocket(9999);
             var socket = serverSocket.accept();
             var outputResponse = new DataOutputStream(socket.getOutputStream()); //удобнее работать со стримами через обертку, например PrintWriter
             var incomingRequest = new DataInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in)) {

            String request = incomingRequest.readUTF();
            while (!"stop".equalsIgnoreCase(request)) {
                log.info("---- Запрос от клиента ----\n{}", request);
                log.info("Введите ответ: ");
                var response = scanner.nextLine();
                outputResponse.writeUTF(response);
                request = incomingRequest.readUTF();
            }
        } catch (IOError | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
