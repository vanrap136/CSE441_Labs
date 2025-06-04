package com.example.lab08_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/** @noinspection ALL*/
public class SendSMSActivity extends AppCompatActivity {

    EditText edtsms, edtms;
    ImageButton btnsendsms2;
    Button btnback2;
    private static final int REQUEST_SEND_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_smsactivity);

        edtsms = findViewById(R.id.edtsms);
        edtms = findViewById(R.id.edtms);
        btnsendsms2 = findViewById(R.id.btnsendsms2);
        btnback2 = findViewById(R.id.btnback2);

        btnsendsms2.setOnClickListener(v -> {
            String phoneNumber = edtsms.getText().toString();
            String message = edtms.getText().toString();

            if (!phoneNumber.trim().isEmpty() && !message.trim().isEmpty()) {
                if (ContextCompat.checkSelfPermission(SendSMSActivity.this,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SendSMSActivity.this,
                            new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
                } else {
                    sendSMS(phoneNumber, message);
                }
            } else {
                Toast.makeText(SendSMSActivity.this, "Enter Phone Number and Message", Toast.LENGTH_SHORT).show();
            }
        });

        btnback2.setOnClickListener(v -> finish());
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent successfully!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS sending failed!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String phoneNumber = edtsms.getText().toString();
                String message = edtms.getText().toString();
                sendSMS(phoneNumber, message);
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}