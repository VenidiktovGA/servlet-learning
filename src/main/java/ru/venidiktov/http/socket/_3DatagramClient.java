package ru.venidiktov.http.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Класс DatagramSocket работает с UDP, для отправки и получения данных используются datagram пакеты
 */
@Slf4j
public class _3DatagramClient {
    public static void main(String[] args) throws IOException {
        try (var datagramSocket = new DatagramSocket()) { // Так как это клиент мы не передаем порт
            /**
             * DatagramPacket пакет принимающий:
             * массив байт (buffer),
             * length - размер buffer он не может быть больше чем buffer.length
             * offset - сколько нужно отступать от массива buffer и далее взять данные
             * InetAddress - хост сервера
             * порт программы на хосту
             * Так как хост и порт можно указать в Datagram пакете то через один и тот же DatagramSocket \
             * можно отправлять данные на разные сервера!
             *
             * buffer это массив в который кто то пишет данные а кто то читает из него
             */
            var buffer = "It is datagram package transmit by UDP".getBytes(); // Размер buffer должен быть согласован на клиенте и сервере
            var localhost = InetAddress.getByName("localhost");
            var datagramPacket = new DatagramPacket(buffer, buffer.length, localhost, 9999);
            datagramSocket.send(datagramPacket);
            log.info("Данные отправлены");
        }
    }
}
