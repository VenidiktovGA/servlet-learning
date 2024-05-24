package ru.venidiktov.http.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * На одной машине на одном порту может работать как TCP так и UDP
 */
@Slf4j
public class _3DatagramServer {
    public static void main(String[] args) throws IOException {
        try (var datagramSocket = new DatagramSocket(9999)) { // Так как это сервер мы передаем порт на котором он работает
            log.info("Сервер запущен");
            byte[] buffer = new byte[512]; // Размер buffer должен быть согласован на клиенте и сервере
            var datagramPacket = new DatagramPacket(buffer, buffer.length); // При получении хост и порт не нужны!
            datagramSocket.receive(datagramPacket); //На данной строчке процесс зависнет и будет ждать прихода данных!
            var request = new String(buffer);//Для получения данные так же можно datagramPacket.getData()
            log.info("Получены данные\n{}", request);
        }
    }
}
