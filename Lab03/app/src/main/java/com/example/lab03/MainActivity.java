package com.example.lab03;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends Activity {

    EditText edt1, edt2, edt3;
    Button btncong, btntru, btnnhan, btnchia;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);

        btncong = findViewById(R.id.btncong);
        btntru = findViewById(R.id.btntru);
        btnnhan = findViewById(R.id.btnnhan);
        btnchia = findViewById(R.id.btnchia);

        btncong.setOnClickListener(v -> {
            int a = Integer.parseInt(edt1.getText().toString());
            int b = Integer.parseInt(edt2.getText().toString());
            edt3.setText("a + b = " + (a + b));
        });

        btntru.setOnClickListener(v -> {
            int a = Integer.parseInt(edt1.getText().toString());
            int b = Integer.parseInt(edt2.getText().toString());
            edt3.setText("a - b = " + (a - b));
        });

        btnnhan.setOnClickListener(v -> {
            int a = Integer.parseInt(edt1.getText().toString());
            int b = Integer.parseInt(edt2.getText().toString());
            edt3.setText("a * b = " + (a * b));
        });

        btnchia.setOnClickListener(v -> {
            int a = Integer.parseInt(edt1.getText().toString());
            int b = Integer.parseInt(edt2.getText().toString());
            if (b == 0) {
                edt3.setText("B phải khác 0");
            } else {
                edt3.setText("a / b = " + (a / b));
            }
        });
    }
}
