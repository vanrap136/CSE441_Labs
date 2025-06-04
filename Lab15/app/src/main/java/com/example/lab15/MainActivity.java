package com.example.lab15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtmalop, edttenlop, edtsiso;
    Button btninsert, btndelete, btnupdate, btnquery;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtmalop = findViewById(R.id.edtmalop);
        edttenlop = findViewById(R.id.edttenlop);
        edtsiso = findViewById(R.id.edtsiso);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
        lv = findViewById(R.id.lv);

        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        // Tạo hoặc mở database
        mydatabase = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);

        // Tạo bảng tblop nếu chưa tồn tại
        try {
            String sql = "CREATE TABLE IF NOT EXISTS tblop (malop TEXT PRIMARY KEY, tenlop TEXT, siso INTEGER)";
            mydatabase.execSQL(sql);
            Toast.makeText(this, "Tạo bảng thành công!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi tạo bảng: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Xử lý sự kiện nút INSERT
        btninsert.setOnClickListener(v -> {
            String malop = edtmalop.getText().toString();
            String tenlop = edttenlop.getText().toString();
            String sisoStr = edtsiso.getText().toString();

            if (malop.isEmpty() || tenlop.isEmpty() || sisoStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int siso = Integer.parseInt(sisoStr);
                ContentValues values = new ContentValues();
                values.put("malop", malop);
                values.put("tenlop", tenlop);
                values.put("siso", siso);

                long rowId = mydatabase.insert("tblop", null, values);

                if (rowId != -1) {
                    Toast.makeText(MainActivity.this, "Thêm lớp thành công!", Toast.LENGTH_SHORT).show();
                    edtmalop.setText("");
                    edttenlop.setText("");
                    edtsiso.setText("");
                    loadData(); // Tải lại dữ liệu sau khi thêm
                } else {
                    Toast.makeText(MainActivity.this, "Lỗi thêm lớp!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Sỉ số phải là số!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút DELETE
        btndelete.setOnClickListener(v -> {
            String malopToDelete = edtmalop.getText().toString();
            if (malopToDelete.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập Mã lớp để xóa!", Toast.LENGTH_SHORT).show();
                return;
            }

            int rowsDeleted = mydatabase.delete("tblop", "malop=?", new String[]{malopToDelete});

            if (rowsDeleted > 0) {
                Toast.makeText(MainActivity.this, "Xóa lớp thành công!", Toast.LENGTH_SHORT).show();
                edtmalop.setText("");
                loadData(); // Tải lại dữ liệu sau khi xóa
            } else {
                Toast.makeText(MainActivity.this, "Không tìm thấy lớp có Mã lớp: " + malopToDelete, Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút UPDATE
        btnupdate.setOnClickListener(v -> {
            String malopToUpdate = edtmalop.getText().toString();
            String tenlop = edttenlop.getText().toString();
            String sisoStr = edtsiso.getText().toString();

            if (malopToUpdate.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập Mã lớp để cập nhật!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tenlop.isEmpty() || sisoStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin cập nhật!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int siso = Integer.parseInt(sisoStr);
                ContentValues values = new ContentValues();
                values.put("tenlop", tenlop);
                values.put("siso", siso);

                int rowsUpdated = mydatabase.update("tblop", values, "malop=?", new String[]{malopToUpdate});

                if (rowsUpdated > 0) {
                    Toast.makeText(MainActivity.this, "Cập nhật lớp thành công!", Toast.LENGTH_SHORT).show();
                    edtmalop.setText("");
                    edttenlop.setText("");
                    edtsiso.setText("");
                    loadData(); // Tải lại dữ liệu sau khi cập nhật
                } else {
                    Toast.makeText(MainActivity.this, "Không tìm thấy lớp có Mã lớp: " + malopToUpdate, Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Sỉ số phải là số!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút QUERY
        btnquery.setOnClickListener(v -> {
            loadData(); // Gọi hàm loadData để hiển thị tất cả dữ liệu
        });

        // Tải dữ liệu ban đầu khi Activity được tạo
        loadData();
    }

    private void loadData() {
        mylist.clear();
        Cursor cursor = mydatabase.query("tblop", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String malop = cursor.getString(cursor.getColumnIndexOrThrow("malop"));
                String tenlop = cursor.getString(cursor.getColumnIndexOrThrow("tenlop"));
                int siso = cursor.getInt(cursor.getColumnIndexOrThrow("siso"));
                mylist.add(malop + " - " + tenlop + " - " + siso);
            } while (cursor.moveToNext());
        }

        cursor.close();

        myadapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        if (mydatabase != null && mydatabase.isOpen()) {
            mydatabase.close();
        }
        super.onDestroy();
    }
}