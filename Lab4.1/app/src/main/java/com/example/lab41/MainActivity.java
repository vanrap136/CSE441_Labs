package com.example.lab41;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtC, edtF;
    Button btnCel, btnFar, btnClear;
    DecimalFormat dcf = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtC = findViewById(R.id.edtC);
        edtF = findViewById(R.id.edtF);
        btnCel = findViewById(R.id.btnCel);
        btnFar = findViewById(R.id.btnFar);
        btnClear = findViewById(R.id.btnClear);

        btnFar.setOnClickListener(v -> {
            String strF = edtF.getText().toString();

            if (TextUtils.isEmpty(strF)) {
                Toast.makeText(MainActivity.this, "Please enter Fahrenheit value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double F = Double.parseDouble(strF);
                double C = (F - 32) * 5.0 / 9.0;
                edtC.setText(dcf.format(C));
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid Fahrenheit value", Toast.LENGTH_SHORT).show();
                edtF.setText("");
            }
        });

        btnCel.setOnClickListener(v -> {
            String strC = edtC.getText().toString();

            if (TextUtils.isEmpty(strC)) {
                Toast.makeText(MainActivity.this, "Please enter Celsius value", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double C = Double.parseDouble(strC);
                double F = (C * 9.0 / 5.0) + 32;
                edtF.setText(dcf.format(F));
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid Celsius value", Toast.LENGTH_SHORT).show();
                edtC.setText("");
            }
        });

        btnClear.setOnClickListener(v -> {
            edtC.setText("");
            edtF.setText("");
        });
    }
}