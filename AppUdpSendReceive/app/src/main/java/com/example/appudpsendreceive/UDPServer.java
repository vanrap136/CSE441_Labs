package com.example.appudpsendreceive;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try (Scanner ignored = new Scanner(System.in)) {
            int port = 12345; // Cổng mà server sẽ lắng nghe
            socket = new DatagramSocket(port); // Tạo socket lắng nghe trên cổng 12345
            System.out.println("Server UDP đang chờ gói tin trên cổng " + port + "...");

            byte[] buffer = new byte[1024]; // Buffer để nhận dữ liệu
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                // Nhận gói tin từ client
                socket.receive(packet);
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());

                // Lấy thông tin về client gửi tin nhắn
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                System.out.println("Nhận được từ " + clientAddress.getHostAddress() + ":" + clientPort + ": " + receivedMessage);

                // Chuẩn bị phản hồi
                String responseMessage = "Server nhận: '" + receivedMessage + "'";
                byte[] responseData = responseMessage.getBytes();

                // Tạo gói tin phản hồi và gửi lại cho client vừa gửi tin nhắn
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                socket.send(responsePacket);
                System.out.println("Đã gửi phản hồi đến " + clientAddress.getHostAddress() + ":" + clientPort);
            }
        } catch (SocketException e) {
            System.err.println("Lỗi Socket: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Lỗi I/O khi nhận/gửi gói tin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng socket và scanner khi kết thúc hoặc có lỗi
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("Server UDP đã đóng.");
            }
        }
    }
}