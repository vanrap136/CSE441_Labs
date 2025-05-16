package com.example.lab07_3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB, edtKQ;
    Button btnrequest;
    static final int REQUEST_CODE_SUB = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKQ = findViewById(R.id.edtKQ);
        btnrequest = findViewById(R.id.btnrequest);

        btnrequest.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, SubActivity.class);
            int a = Integer.parseInt(edtA.getText().toString());
            int b = Integer.parseInt(edtB.getText().toString());
            myintent.putExtra("soa", a);
            myintent.putExtra("sob", b);
            startActivityForResult(myintent, REQUEST_CODE_SUB);
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SUB) {
            if (resultCode == 33) {
                int sum = data.getIntExtra("kq", 0);
                edtKQ.setText("Tổng 2 số là: " + sum);
            }
            if (resultCode == 34) {
                int sub = data.getIntExtra("kq", 0);
                edtKQ.setText("Hiệu 2 số là: " + sub);
            }
        }
    }
}