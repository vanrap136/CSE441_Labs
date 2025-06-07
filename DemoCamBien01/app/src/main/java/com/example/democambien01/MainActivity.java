package com.example.democambien01;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.Context;
import android.hardware.SensorEvent;
import android.view.View;
import android.graphics.Color;
import android.widget.TextView; // Cần import TextView
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Khai báo các biến ở đây
    private TextView txtX, txtY, txtZ; // Khai báo các TextView
    private View viewBackground; // Khai báo View (hoặc có thể là LinearLayout, RelativeLayout tùy theo XML của bạn)
    private SensorManager sensorManager; // Khai báo SensorManager
    private Sensor accelerometer; // Khai báo Sensor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đây là mã được thêm/sửa cho EdgeToEdge và Insets, hãy đảm bảo nó là cần thiết.
        // Nếu không, bạn có thể xóa phần này để giữ mã đơn giản hơn.
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các biến
        txtX = findViewById(R.id.txtX);
        txtY = findViewById(R.id.txtY);
        txtZ = findViewById(R.id.txtZ);
        viewBackground = findViewById(R.id.viewBackground);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Đảm bảo sensorManager không null trước khi hủy đăng ký listener
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Sử dụng Locale.getDefault() để tránh cảnh báo Implicitly using the default locale
            txtX.setText("Trục X: " + String.format(java.util.Locale.getDefault(), "%.2f", x) + " m/s²");
            txtY.setText("Trục Y: " + String.format(java.util.Locale.getDefault(), "%.2f", y) + " m/s²");
            txtZ.setText("Trục Z: " + String.format(java.util.Locale.getDefault(), "%.2f", z) + " m/s²");

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);
            if (acceleration > 2.0f) {
                viewBackground.setBackgroundColor(Color.RED);
            } else {
                viewBackground.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Không cần xử lý
    }
}