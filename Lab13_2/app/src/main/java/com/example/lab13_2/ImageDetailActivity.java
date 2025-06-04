package com.example.lab13_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageDetailActivity extends AppCompatActivity {

    ImageView imageViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageViewDetail = findViewById(R.id.imageViewDetail);

        // Lấy ID resource của ảnh được truyền từ GridViewActivity
        int imageResource = getIntent().getIntExtra("image_resource", 0);

        // Hiển thị ảnh
        imageViewDetail.setImageResource(imageResource);
    }
}