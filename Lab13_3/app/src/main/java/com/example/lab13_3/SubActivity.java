package com.example.lab13_3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    TextView tvSelected, tvPhoneName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tvSelected = findViewById(R.id.textViewSelected);
        tvPhoneName = findViewById(R.id.textViewPhoneName);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ten_dien_thoai")) {
            String phoneName = intent.getStringExtra("ten_dien_thoai");
            tvPhoneName.setText(phoneName);
        }
    }
}