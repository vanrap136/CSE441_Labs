package com.example.lab05_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editnamduonglich;
    Button btnDoi;
    TextView txtNamAmLich;

    String[] can = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
    String[] chi = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editnamduonglich = findViewById(R.id.editnamduonglich);
        btnDoi = findViewById(R.id.button1);
        txtNamAmLich = findViewById(R.id.textview5);

        btnDoi.setOnClickListener(v -> {
            String strNamDuong = editnamduonglich.getText().toString();

            if (TextUtils.isEmpty(strNamDuong)) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập năm dương lịch", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int namDuong = Integer.parseInt(strNamDuong);
                int canIndex = namDuong % 10;
                int chiIndex = namDuong % 12;

                String namAmLich = can[canIndex] + " " + chi[chiIndex];
                txtNamAmLich.setText(namAmLich);

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Năm dương lịch không hợp lệ", Toast.LENGTH_SHORT).show();
                txtNamAmLich.setText("");
            }
        });
    }
}