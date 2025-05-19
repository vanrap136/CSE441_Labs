package com.example.lab16;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edta, edtb;
    Button btntong, btnclear;
    TextView txtkq, txtlichsu;
    SharedPreferences myprefs;
    SharedPreferences.Editor myedit;
    String lichsu = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        btntong = findViewById(R.id.btntong);
        btnclear = findViewById(R.id.btnclear);
        txtkq = findViewById(R.id.txtkq);
        txtlichsu = findViewById(R.id.txtlichsu);

        // Khởi tạo SharedPreferences
        myprefs = getSharedPreferences("mysave", MODE_PRIVATE);
        myedit = myprefs.edit();

        // Lấy lịch sử đã lưu (nếu có)
        lichsu = myprefs.getString("ls", "");
        txtlichsu.setText(lichsu);

        // Xử lý sự kiện nút TONG
        btntong.setOnClickListener(v -> {
            String stra = edta.getText().toString();
            String strb = edtb.getText().toString();
            if (!stra.isEmpty() && !strb.isEmpty()) {
                int a = Integer.parseInt(stra);
                int b = Integer.parseInt(strb);
                int kq = a + b;
                txtkq.setText(String.valueOf(kq));
                lichsu = stra + " + " + strb + " = " + kq + "\n" + lichsu;
                txtlichsu.setText(lichsu);
            } else {
                txtkq.setText("Nhập đủ A và B");
            }
        });

        // Xử lý sự kiện nút CLEAR
        btnclear.setOnClickListener(v -> {
            lichsu = "";
            txtlichsu.setText(lichsu);
            txtkq.setText("");
            // Xóa lịch sử khỏi SharedPreferences
            myedit.putString("ls", "").apply();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Lưu lịch sử vào SharedPreferences khi Activity tạm dừng
        myedit.putString("ls", lichsu).apply();
    }
}