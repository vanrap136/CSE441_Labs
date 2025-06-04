package com.example.lab05_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btntieptuc, btngiai, btnthoat;
    EditText edita, editb, editc;
    TextView txtkq;
    DecimalFormat dcf = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btntieptuc = findViewById(R.id.btntieptuc);
        btngiai = findViewById(R.id.btngiai);
        btnthoat = findViewById(R.id.btnthoat);
        edita = findViewById(R.id.edita);
        editb = findViewById(R.id.editb);
        editc = findViewById(R.id.editc);
        txtkq = findViewById(R.id.txtkq);

        btngiai.setOnClickListener(v -> {
            String sa = edita.getText().toString();
            String sb = editb.getText().toString();
            String sc = editc.getText().toString();

            if (TextUtils.isEmpty(sa) || TextUtils.isEmpty(sb) || TextUtils.isEmpty(sc)) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ hệ số a, b, c", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double a = Double.parseDouble(sa);
                double b = Double.parseDouble(sb);
                double c = Double.parseDouble(sc);
                String kq;

                if (a == 0) {
                    if (b == 0) {
                        if (c == 0) {
                            kq = "PT vô số nghiệm";
                        } else {
                            kq = "PT vô nghiệm";
                        }
                    } else {
                        kq = "PT có 1 No: x = " + dcf.format(-c / b);
                    }
                } else {
                    double delta = b * b - 4 * a * c;
                    if (delta < 0) {
                        kq = "PT vô nghiệm";
                    } else if (delta == 0) {
                        double x = -b / (2 * a);
                        kq = "PT có nghiệm kép: x1 = x2 = " + dcf.format(x);
                    } else {
                        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                        kq = "PT có 2 No: x1 = " + dcf.format(x1) + "; x2 = " + dcf.format(x2);
                    }
                }
                txtkq.setText(kq);

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Hệ số nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                txtkq.setText("");
            }
        });

        btntieptuc.setOnClickListener(v -> {
            edita.setText("");
            editb.setText("");
            editc.setText("");
            txtkq.setText("");
            edita.requestFocus();
        });

        btnthoat.setOnClickListener(v -> finish());
    }
}