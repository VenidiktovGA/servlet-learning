package ru.venidiktov.http.server;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Многопоточный сервер, многопоточный за счет Thread пула
 */
@Slf4j
public class MultiThreadServer {
    private final int port;
    private final ExecutorService pool; //Пул потоков (как в tomcat)
    @Setter
    private boolean stopped; //Флаг остановки сервера

    public MultiThreadServer(int port, int poolSize) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        try (var server = new ServerSocket(port)) { // Создаем сервер (процесс слушающий порт)
            while (!stopped) { //Пока сервер не остановлен
                log.info("Socket доступен");
                Socket socket = server.accept(); /*Сервер ожидает запроса клиента*/
                log.info("Соединение с Socket");
                pool.submit(() -> processSocket(socket)); //Ожидаем запрос и обрабатываем его в параллельном потоке
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось запустить сервер!", e);
        }
    }

    /**
     * Обработка запроса пришедшего на сервер.
     * Если при обработке запроса произошло ошибка мы ее "проглатываем" не пробрасываем выше, так как
     * пробрасывание ошибки выше остановит веь сервер!
     */
    private void processSocket(Socket socket) {
        try (socket; // Поместили сюда чтобы после использования socket был закрыт
             var inputStream = new DataInputStream(socket.getInputStream());
             var outputStream = new DataOutputStream(socket.getOutputStream())) {
            processRequest(inputStream); // Упрощенная заглушка по обработке запроса
            Thread.sleep(10000); //Задержка для наглядности многопоточности сервера
            createResponse(outputStream); // Упрощенная заглушка по выдаче ответа
        } catch (IOException e) {
            log.error("Не удалось обработать запрос!", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Упрощенная заглушка по обработке запроса
     */
    private void processRequest(InputStream inputStream) throws IOException {
        log.info("Request: {}", inputStream.toString());
    }

    /**
     * Ответ сервера на запрос
     */
    private void createResponse(OutputStream outputStream) throws IOException {
        byte[] body = "Hello i'm server".getBytes();
        byte[] headers = """
                HTTP/1.1 200 OK
                content-type: text/plain
                content-length: %s
                """.formatted(body.length).getBytes(); // Последний перенос строки обязателен иначе будет EOF
        outputStream.write(headers);
        outputStream.write("\n".getBytes()); // Пустая строка
        outputStream.write(body);
    }
}
