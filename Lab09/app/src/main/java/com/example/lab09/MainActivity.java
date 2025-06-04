package com.example.lab09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnplay, btnstop;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnplay = findViewById(R.id.imgstart);
        btnstop = findViewById(R.id.imgstop);

        btnplay.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, MyService.class);
            startService(intent1);
            if (flag) {
                btnplay.setImageResource(R.drawable.stop);
                flag = false;
            } else {
                btnplay.setImageResource(R.drawable.play);
                flag = true;
                stopService(intent1); // Tạm dừng khi nhấn play lần nữa (tùy chọn)
            }
        });

        btnstop.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, MyService.class);
            stopService(intent2);
            btnplay.setImageResource(R.drawable.play);
            flag = true;
        });
    }
}