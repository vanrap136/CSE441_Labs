package com.example.lab13_3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Phone> mangphone;
    MyArrayAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        mangphone = new ArrayList<>();

        mangphone.add(new Phone("Điện thoại iPhone 12", R.drawable.ip));
        mangphone.add(new Phone("Điện thoại SamSung S20", R.drawable.samsung));
        mangphone.add(new Phone("Điện thoại Nokia 6", R.drawable.cellphone));
        mangphone.add(new Phone("Điện thoại Bphone 2020", R.drawable.wp));
        mangphone.add(new Phone("Điện thoại Oppo 5", R.drawable.sky));
        mangphone.add(new Phone("Điện thoại Vsmart joy2", R.drawable.htc));
        mangphone.add(new Phone("Điện thoại LG", R.drawable.lg))

        adapter = new MyArrayAdapter(this, R.layout.layout_listview, mangphone);
        lv.setAdapter(adapter);

        // Xử lý sự kiện click vào item của ListView
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            intent.putExtra("ten_dien_thoai", mangphone.get(position).getNamephone());
            startActivity(intent);
        });
    }
}