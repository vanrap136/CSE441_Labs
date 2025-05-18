package com.example.lab13_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class GridViewActivity extends AppCompatActivity {

    TextView selection;
    GridView gridView;
    String[] items = {"Q-Mobile", "Iphone", "New Ipad", "Samsung", "Nokia", "Sony Ericsson",
            "LG", "Q-Mobile", "HTC", "Blackberry", "G Phone", "FPT - Phone", "HK Phone"};

    // Thêm mảng chứa ID của các resource hình ảnh tương ứng
    int[] imageIds = {
            R.drawable.image1, // Thay thế bằng ID resource thực tế của ảnh 1
            R.drawable.image2, // Thay thế bằng ID resource thực tế của ảnh 2
            R.drawable.image3 // Thay thế bằng ID resource thực tế của ảnh 3
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selection = findViewById(R.id.selection);
        gridView = findViewById(R.id.gridView1);

        // Tạo Adapter tùy chỉnh để hiển thị cả text và hình ảnh
        ImageAdapter adapter = new ImageAdapter(this, items, imageIds);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = items[position];
            selection.setText("Bạn đã chọn: " + selectedItem);
            Toast.makeText(GridViewActivity.this, "Đã chọn: " + selectedItem, Toast.LENGTH_SHORT).show();

            // Chuyển sang Activity hiển thị ảnh chi tiết
            Intent intent = new Intent(GridViewActivity.this, ImageDetailActivity.class);
            intent.putExtra("image_resource", imageIds[position]);
            startActivity(intent);
        });
    }
}