package com.example.lab08_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btncallphone, btnsendsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncallphone = findViewById(R.id.btncallphone);
        btnsendsms = findViewById(R.id.btnsendsms);

        btncallphone.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, CallPhoneActivity.class);
            startActivity(intent1);
        });

        btnsendsms.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, SendSMSActivity.class);
            startActivity(intent2);
        });
    }
}