package com.example.lab04_2;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText editten, editchieucao, editcannang, editBMI, editchuandoan;
    Button btnBMI;
    DecimalFormat dcf = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editten = findViewById(R.id.editten);
        editchieucao = findViewById(R.id.editchieucao);
        editcannang = findViewById(R.id.editcannang);
        editBMI = findViewById(R.id.editBMI);
        editchuandoan = findViewById(R.id.editchuandoan);
        btnBMI = findViewById(R.id.btnBMI);

        btnBMI.setOnClickListener(v -> {
            String strCao = editchieucao.getText().toString();
            String strNang = editcannang.getText().toString();

            if (TextUtils.isEmpty(strCao)) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập chiều cao", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(strNang)) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double cao = Double.parseDouble(strCao);
                double nang = Double.parseDouble(strNang);

                if (cao <= 0 || nang <= 0) {
                    Toast.makeText(MainActivity.this, "Chiều cao và cân nặng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                double bmi = nang / Math.pow(cao, 2);
                editBMI.setText(dcf.format(bmi));

                String chandoan;
                if (bmi < 18) {
                    chandoan = "Bạn bị gầy";
                } else if (bmi <= 24.9) {
                    chandoan = "Bạn bình thường";
                } else if (bmi <= 29.9) {
                    chandoan = "Bạn bị béo phì độ I";
                } else if (bmi <= 34.9) {
                    chandoan = "Bạn bị béo phì độ II";
                } else {
                    chandoan = "Bạn bị béo phì độ III";
                }
                editchuandoan.setText(chandoan);

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Giá trị nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                editBMI.setText("");
                editchuandoan.setText("");
            }
        });
    }
}