package com.example.lab07_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ketqua extends AppCompatActivity {

    TextView txtketqua;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketqua);

        txtketqua = findViewById(R.id.txtketqua);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        Bundle yourbundle = intent.getBundleExtra("mybackage");
        assert yourbundle != null;
        int a = yourbundle.getInt("soa");
        int b = yourbundle.getInt("sob");
        String kq;

        if (a == 0 && b == 0) {
            kq = "Vô số nghiệm";
        } else if (a == 0) {
            kq = "Vô nghiệm";
        } else {
            DecimalFormat dcf = new DecimalFormat("0.##");
            double x = (double) -b / a;
            kq = "x = " + dcf.format(x);
        }

        txtketqua.setText(kq);

        btnBack.setOnClickListener(v -> finish());
    }
}