package com.example.lab14_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edta, edtb;
    Button btncong;
    ListView lv1;
    ArrayList<String> list;
    ArrayAdapter<String> myarray;
    TabHost tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btncong.setOnClickListener(v -> {
            int a = Integer.parseInt(edta.getText().toString());
            int b = Integer.parseInt(edtb.getText().toString());
            String c = a + " + " + b + " = " + (a + b);
            list.add(c);
            myarray.notifyDataSetChanged();
            edta.setText("");
            edtb.setText("");
        });
    }

    private void addControls() {
        tab = findViewById(R.id.tabhost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Tính toán");
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Lịch sử");
        tab.addTab(tab2);

        edta = findViewById(R.id.edta);
        edtb = findViewById(R.id.edtb);
        btncong = findViewById(R.id.btncong);
        lv1 = findViewById(R.id.lv1);
        list = new ArrayList<>();
        myarray = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        lv1.setAdapter(myarray);
    }
}