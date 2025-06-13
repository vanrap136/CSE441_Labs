package com.example.appudpsendreceive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException; // Thêm import này

public class MainActivity extends AppCompatActivity {

    private EditText edtMessage;
    private TextView txtResponse;
    private DatagramSocket clientSocket; // Khai báo socket ở cấp độ lớp

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view từ layout
        edtMessage = findViewById(R.id.edtMessage);
        txtResponse = findViewById(R.id.txtResponse);
        Button btnSend = findViewById(R.id.btnSend);

        // Thiết lập sự kiện click cho nút Gửi
        btnSend.setOnClickListener(v -> {
            String message = edtMessage.getText().toString();
            if (message.isEmpty()) {
                txtResponse.setText("Vui lòng nhập tin nhắn.");
                return;
            }
            new SendUDPTask().execute(message); // Truyền tin nhắn vào AsyncTask
        });
    }

    // AsyncTask để xử lý gửi/nhận UDP trong luồng nền
    @SuppressLint("StaticFieldLeak")
    private class SendUDPTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... messages) {
            String messageToSend = messages[0];
            String response;
            try {
                // Khởi tạo socket nếu chưa có
                if (clientSocket == null || clientSocket.isClosed()) {
                    clientSocket = new DatagramSocket();
                    clientSocket.setSoTimeout(5000); // Thời gian chờ phản hồi 5 giây
                }

                // Địa chỉ IP của server hoặc địa chỉ broadcast
                // Thay đổi "192.168.1.255" thành địa chỉ IP của server UDP
                // hoặc địa chỉ broadcast phù hợp với mạng của bạn (ví dụ: "192.168.0.255")
                // Nếu dùng trình giả lập và server trên cùng máy tính: "10.0.2.2"
                InetAddress address = InetAddress.getByName("192.168.1.255"); // <-- THAY ĐỔI ĐỊA CHỈ IP NÀY!
                int port = 12345; // Cổng của server UDP

                byte[] data = messageToSend.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                clientSocket.send(packet);

                // Chuẩn bị nhận phản hồi
                byte[] buffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(responsePacket); // Chờ phản hồi

                response = new String(responsePacket.getData(), 0, responsePacket.getLength());

            } catch (SocketTimeoutException e) {
                response = "Lỗi: Server không phản hồi trong 5 giây.";
                e.printStackTrace();
            } catch (IOException e) {
                response = "Lỗi kết nối UDP: " + e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                response = "Lỗi không xác định: " + e.getMessage();
                e.printStackTrace();
            }
            return response;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            // Cập nhật giao diện người dùng với kết quả
            txtResponse.setText("Phản hồi: " + result);
            edtMessage.setText(""); // Xóa nội dung EditText sau khi gửi
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đóng DatagramSocket khi Activity bị hủy để giải phóng tài nguyên
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
    }
}