package com.example.lab12_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtdate;
    EditText edth, edtm, edtnote;
    Button btnadd;
    ListView lv2;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtdate = findViewById(R.id.txtdate);
        edth = findViewById(R.id.edth);
        edtm = findViewById(R.id.edtm);
        edtnote = findViewById(R.id.edtnote);
        btnadd = findViewById(R.id.btnadd);
        lv2 = findViewById(R.id.lv2);

        // Hiển thị ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txtdate.setText(dateFormat.format(calendar.getTime()));

        arrayList = new ArrayList<>();
        // Sử dụng layout tùy chỉnh cho item của ListView
        adapter = new ArrayAdapter<>(this, R.layout.list_item_2, R.id.item_note, arrayList);
        lv2.setAdapter(adapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = edth.getText().toString().trim();
                String minute = edtm.getText().toString().trim();
                String note = edtnote.getText().toString().trim();

                if (hour.isEmpty() || minute.isEmpty() || note.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int h = Integer.parseInt(hour);
                    int m = Integer.parseInt(minute);
                    if (h > 23 || m > 59 || h < 0 || m < 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Lỗi nhập liệu");
                        builder.setMessage("Giờ phải từ 0-23 và phút phải từ 0-59.");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                        return;
                    }
                    String time = String.format(Locale.getDefault(), "%02d:%02d", h, m);
                    arrayList.add(time + " - " + note);
                    adapter.notifyDataSetChanged();

                    // Xóa nội dung đã nhập
                    edth.setText("");
                    edtm.setText("");
                    edtnote.setText("");

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số cho giờ và phút!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TODO: Thêm chức năng xóa item khi long click (nếu cần)
    }
}