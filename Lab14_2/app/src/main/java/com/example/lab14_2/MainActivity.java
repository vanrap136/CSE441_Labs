package com.example.lab14_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edittim;
    ListView lv1, lv2, lv3;
    TabHost tab;
    ArrayList<Item> list1, list2, list3;
    MyArrayAdapter myarray1, myarray2, myarray3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        setupTab();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setupTab() {
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);

        tab.setOnTabChangedListener(tabId -> {
            if (tabId.equalsIgnoreCase("t1")) {
                list1.clear();
                list1.add(new Item("52300", "Em là ai", 0));
                list1.add(new Item("52600", "Bài ca đất Phương Nam", 1));
                list1.add(new Item("52567", "Buồn của Anh", 0));
                myarray1.notifyDataSetChanged();
            } else if (tabId.equalsIgnoreCase("t2")) {
                list2.clear();
                list2.add(new Item("57236", "Gửi em ở cuối sông hồng", 0));
                list2.add(new Item("51548", "Quê hương tuổi thơ tôi", 0));
                list2.add(new Item("51748", "Em gì ơi", 1));
                myarray2.notifyDataSetChanged();
            } else if (tabId.equalsIgnoreCase("t3")) {
                list3.clear();
                list3.add(new Item("57689", "Hát với dòng sông", 1));
                list3.add(new Item("58716", "Say tình _ Remix", 0));
                list3.add(new Item("58916", "Người hãy quên em đi", 0));
                myarray3.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void addControls() {
        tab = findViewById(R.id.tabhost);
        tab.setup();
        edittim = findViewById(R.id.edittim);
        lv1 = findViewById(R.id.tab1);
        lv2 = findViewById(R.id.tab2);
        lv3 = findViewById(R.id.tab3);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myarray1 = new MyArrayAdapter(MainActivity.this, R.layout.listitem, list1);
        myarray2 = new MyArrayAdapter(MainActivity.this, R.layout.listitem, list2);
        myarray3 = new MyArrayAdapter(MainActivity.this, R.layout.listitem, list3);

        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }
}