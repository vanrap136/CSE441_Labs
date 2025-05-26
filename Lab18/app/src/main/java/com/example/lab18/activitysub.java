package com.example.lab18;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.ContentValues;
import android.view.View;

public class activitysub extends Activity {

    TextView txtmaso, txttenbaihat, txtloibaihat, txttacgia;
    ImageButton btnyeuThich;
    String masoBaiHat; // Để lưu mã số bài hát từ MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        txtmaso = (TextView) findViewById(R.id.txtmaso);
        txttenbaihat = (TextView) findViewById(R.id.txttenbaihat);
        txtloibaihat = (TextView) findViewById(R.id.txtloibaihat);
        txttacgia = (TextView) findViewById(R.id.txttacgia);
        btnyeuThich = (ImageButton) findViewById(R.id.btnyeuThich);

        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("package");
        masoBaiHat = packageFromCaller.getString("maso");

        // Lấy thông tin chi tiết bài hát từ database dựa vào masoBaiHat
        // Đây là phần bạn cần tự implement dựa trên cấu trúc database của bạn
        // Ví dụ:
        // Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH=?", new String[]{masoBaiHat});
        // if (cursor.moveToFirst()) {
        //     txtmaso.setText(cursor.getString(cursor.getColumnIndex("MABH")));
        //     txttenbaihat.setText(cursor.getString(cursor.getColumnIndex("TENBH")));
        //     txtloibaihat.setText(cursor.getString(cursor.getColumnIndex("LOIBH")));
        //     txttacgia.setText(cursor.getString(cursor.getColumnIndex("TACGIA")));
        //     int thich = cursor.getInt(cursor.getColumnIndex("YEU_THICH"));
        //     if (thich == 1) {
        //         btnyeuThich.setImageResource(R.drawable.favourite); // Icon đã thích
        //     } else {
        //         btnyeuThich.setImageResource(R.drawable.love); // Icon chưa thích
        //     }
        // }
        // cursor.close();

        // Tạm thời set cứng dữ liệu để test nếu bạn chưa kết nối DB
        txtmaso.setText(masoBaiHat);
        txttenbaihat.setText("999 ĐÓA HỒNG");
        txtloibaihat.setText("Vàng em chiều nay anh\nđến thăm em bài\nbướm vàng đậu đây, để\nbuồn nhung nhớ...");
        txttacgia.setText("NHẠC HOA");
        btnyeuThich.setImageResource(R.drawable.love); // Mặc định là chưa thích

        btnyeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                // Kiểm tra trạng thái hiện tại của nút yêu thích để cập nhật
                // Bạn cần một cách để lưu trạng thái "thích" của bài hát này
                // Ví dụ, bạn có thể truy vấn lại DB hoặc truyền trạng thái từ MainActivity
                // Tạm thời, tôi sẽ giả định rằng nếu click, nó sẽ chuyển trạng thái
                // Điều này cần logic cụ thể hơn dựa trên dữ liệu thật từ DB
                // Ví dụ đơn giản: nếu là icon 'love' thì chuyển sang 'favourite' và ngược lại
                // Và cập nhật vào DB
                if (btnyeuThich.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.love).getConstantState())) {
                    btnyeuThich.setImageResource(R.drawable.favourite);
                    values.put("YEU_THICH", 1);
                } else {
                    btnyeuThich.setImageResource(R.drawable.love);
                    values.put("YEU_THICH", 0);
                }
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{masoBaiHat});
            }
        });
    }
}