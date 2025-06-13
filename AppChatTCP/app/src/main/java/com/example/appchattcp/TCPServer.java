package com.example.appchattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner; // Import Scanner để đọc input từ console

public class TCPServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Scanner consoleInput = new Scanner(System.in); // Sử dụng Scanner

        try {
            serverSocket = new ServerSocket(5555); // Cổng lắng nghe
            System.out.println("Server đang chờ kết nối trên cổng 5555...");

            clientSocket = serverSocket.accept(); // Chấp nhận kết nối từ client
            System.out.println("Đã kết nối từ client: " + clientSocket.getInetAddress().getHostAddress());

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true); // true để tự động flush

            // Luồng để nhận tin nhắn từ client
            BufferedReader finalIn = in;
            Thread readClientThread = new Thread(() -> {
                String clientMessage;
                try {
                    while ((clientMessage = finalIn.readLine()) != null) {
                        System.out.println("Client: " + clientMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Client đã ngắt kết nối hoặc lỗi đọc: " + e.getMessage());
                } finally {
                    try {
                        finalIn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readClientThread.start();

            // Luồng để gửi tin nhắn từ server (console) đến client
            String serverResponse;
            while (true) {
                System.out.print("Server: ");
                serverResponse = consoleInput.nextLine(); // Đọc input từ console
                if ("exit".equalsIgnoreCase(serverResponse)) { // Thêm điều kiện thoát
                    System.out.println("Server đang đóng.");
                    break;
                }
                out.println(serverResponse); // Gửi tin nhắn đến client
            }

        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Đóng tất cả các tài nguyên
            try {
                consoleInput.close();
                if (out != null) out.close();
                if (in != null) in.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}