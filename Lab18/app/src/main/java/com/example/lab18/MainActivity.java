package com.example.lab18;

import android.os.Bundle;

package com.example.karaoke;

import android.app.TabActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends TabActivity {

    public static String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "Arirang.sqlite";

    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myArrayAdapter myarrayAdapter1, myarrayAdapter2, myarrayAdapter3;
    TabHost tab;
    EditText edttim;
    ImageButton btnxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy(); // Copy CSDL arirang.sqlite
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        addcontrol(); // Hàm thêm các controls
        addTim(); // Xử lý tìm kiếm
        addEvents(); // Xử lý sự kiện khi chuyển Tab và các sự kiện khác

        // Hàm kiểm tra và add các Controls vào giao diện
        // Trên 3 tab chúng ta có 3 ListView ứng với 3 danh sách dữ liệu (Dữ liệu tìm kiếm, Danh sách bài hát gốc, Danh sách bài hát yêu thích)
        btnxoa = (ImageButton) findViewById(R.id.btnxoa);
        tab = (TabHost) findViewById(android.R.id.tabhost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search)); // Sử dụng icon search
        tab1.setContent(R.id.tab1);
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list)); // Sử dụng icon list
        tab2.setContent(R.id.tab2);
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favourite)); // Sử dụng icon favourite
        tab3.setContent(R.id.tab3);
        tab.addTab(tab3);

        edttim = (EditText) findViewById(R.id.edttim);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        list1 = new ArrayList<Item>();
        list2 = new ArrayList<Item>();
        list3 = new ArrayList<Item>();

        myarrayAdapter1 = new myArrayAdapter(this, R.layout.listitem, list1);
        lv1.setAdapter(myarrayAdapter1);

        myarrayAdapter2 = new myArrayAdapter(this, R.layout.listitem, list2);
        lv2.setAdapter(myarrayAdapter2);

        myarrayAdapter3 = new myArrayAdapter(this, R.layout.listitem, list3);
        lv3.setAdapter(myarrayAdapter3);

        // Xử lý sự kiện khi chuyển các