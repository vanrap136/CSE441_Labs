package com.example.lab07_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

    Button btnBackToMain;
    TextView txtChild;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        btnBackToMain = findViewById(R.id.button2);
        txtChild = findViewById(R.id.textView2);
        txtChild.setText("This is Child Activity"); // Đặt text cho TextView (nếu bạn muốn)

        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(child_activity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}