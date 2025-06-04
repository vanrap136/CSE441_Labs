package com.example.lab07_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    EditText edtAA, edtBB;
    Button btnsendtong, btnsendhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        edtAA = findViewById(R.id.edtAA);
        edtBB = findViewById(R.id.edtBB);
        btnsendtong = findViewById(R.id.btnsendtong);
        btnsendhieu = findViewById(R.id.btnsendhieu);

        Intent intent = getIntent();
        int a = intent.getIntExtra("soa", 0);
        int b = intent.getIntExtra("sob", 0);
        edtAA.setText(String.valueOf(a));
        edtBB.setText(String.valueOf(b));

        btnsendtong.setOnClickListener(v -> {
            Intent intent1 = new Intent();
            int sum = a + b;
            intent1.putExtra("kq", sum);
            setResult(33, intent1);
            finish();
        });

        btnsendhieu.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            int sub = a - b;
            intent2.putExtra("kq", sub);
            setResult(34, intent2);
            finish();
        });
    }
}