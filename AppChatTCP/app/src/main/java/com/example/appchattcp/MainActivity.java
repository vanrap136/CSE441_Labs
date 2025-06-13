package com.example.appchattcp; // THAY THẾ BẰNG TÊN GÓI CỦA BẠN

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private EditText edtMessage;
    private TextView txtChat;

    private Socket socket;
    private PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view từ layout
        edtMessage = findViewById(R.id.edtMessage);
        Button btnSend = findViewById(R.id.btnSend);
        txtChat = findViewById(R.id.txtChat);

        // Thiết lập sự kiện click cho nút Gửi
        btnSend.setOnClickListener(this::sendMessage);

        // Kết nối đến server TCP trong một luồng nền
        new ConnectTask().execute();
    }

    // AsyncTask để xử lý kết nối mạng trong luồng nền
    @SuppressLint("StaticFieldLeak")
    private class ConnectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Thay đổi địa chỉ IP của server và cổng
                // Nếu chạy trên trình giả lập và server trên cùng máy tính: "10.0.2.2"
                // Nếu chạy trên thiết bị thật và server trên máy tính khác: "địa chỉ IP của máy server"
                socket = new Socket("192.168.1.100", 5555); // <-- THAY ĐỔI ĐỊA CHỈ IP NÀY!
                out = new PrintWriter(socket.getOutputStream(), true); // true để tự động flush

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;

                // Vòng lặp liên tục đọc tin nhắn từ server
                while ((message = in.readLine()) != null) {
                    final String receivedMessage = message; // Để truy cập trong runOnUiThread
                    runOnUiThread(() -> txtChat.append("\nServer: " + receivedMessage));
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> txtChat.append("\nLỗi kết nối: " + e.getMessage()));
            } finally {
                // Đảm bảo đóng socket nếu vòng lặp kết thúc hoặc có lỗi
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    // Phương thức gửi tin nhắn khi nút Gửi được nhấn
    public void sendMessage(View view) {
        final String message = edtMessage.getText().toString();
        if (!message.isEmpty() && out != null) {
            // Gửi tin nhắn trong một luồng nền để tránh NetworkOnMainThreadException
            new Thread(() -> {
                out.println(message);
                runOnUiThread(() -> {
                    txtChat.append("\nBạn: " + message);
                    edtMessage.setText(""); // Xóa nội dung EditText sau khi gửi
                });
            }).start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đóng socket khi Activity bị hủy để giải phóng tài nguyên
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}