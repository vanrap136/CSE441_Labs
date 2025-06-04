package com.example.lab07_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edta, edtb;
    Button btnkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        btnkq = findViewById(R.id.btnketqua);

        btnkq.setOnClickListener(v -> {
            Intent myintent = new Intent(MainActivity.this, ketqua.class);
            Bundle bundle1 = new Bundle();
            int a = Integer.parseInt(edta.getText().toString());
            int b = Integer.parseInt(edtb.getText().toString());
            bundle1.putInt("soa", a);
            bundle1.putInt("sob", b);
            myintent.putExtra("mybackage", bundle1);
            startActivity(myintent);
        });
    }
}