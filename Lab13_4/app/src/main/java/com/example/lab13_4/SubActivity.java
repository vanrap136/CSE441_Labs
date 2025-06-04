package com.example.lab13_4;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SubActivity extends Activity {
    Bundle extra;
    TextView txtname2;
    ImageView img2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.childlayout);
        txtname2 = findViewById(R.id.textView2);
        img2 = findViewById(R.id.imageView2);
        extra = getIntent().getExtras();
        assert extra != null;
        int position = extra.getInt("TITLE");
        txtname2.setText("Bạn chọn " + MainActivity.arrayName[position]);
        img2.setImageResource(MainActivity.imageName[position]);
    }
}