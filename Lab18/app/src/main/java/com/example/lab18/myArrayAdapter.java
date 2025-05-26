package com.example.lab18;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class myArrayAdapter extends ArrayAdapter<Item> {
    Activity context;
    int LayoutId;
    ArrayList<Item> myarray = null;

    public myArrayAdapter(Activity context, int LayoutId, ArrayList<Item> arr) {
        super(context, LayoutId, arr);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.LayoutId = LayoutId;
        this.myarray = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);
        final Item myItem = myarray.get(position);
        TextView tittiende = (TextView) convertView.findViewById(R.id.tittiende);
        TextView titteude = (TextView) convertView.findViewById(R.id.titteude);
        final TextView maso = (TextView) convertView.findViewById(R.id.tittiende); // Assuming tittiende displays maso
        tittiende.setText(myItem.getMaso());
        titteude.setText(myItem.getTieude());

        final ImageButton btnlike = (ImageButton) convertView.findViewById(R.id.btnlike);
        final ImageButton btnunlike = (ImageButton) convertView.findViewById(R.id.btnunlike);

        // Xử lý hiển thị thích và bỏ thích
        if (myItem.getThich() == 1) { // Thích
            btnlike.setVisibility(View.GONE); // Cho ẩn btnlike
            btnunlike.setVisibility(View.VISIBLE); // Cho hiện btnunlike
        } else { // Không thích
            btnlike.setVisibility(View.VISIBLE); // Cho hiện btnlike
            btnunlike.setVisibility(View.GONE); // Cho ẩn btnunlike
        }

        // Xử lý sự kiện khi click vào ImageButton like và Unlike
        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEU_THICH", 1); // Cập nhật trạng thái thích = 1
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{maso.getText().toString()});
                btnlike.setVisibility(View.GONE);
                btnunlike.setVisibility(View.VISIBLE);
                // Cần refresh lại listview yêu thích nếu đang ở tab đó hoặc khi người dùng chuyển tab
                if(context instanceof MainActivity){
                    ((MainActivity)context).addyeuthich(); // Cập nhật lại danh sách yêu thích
                }
            }
        });

        btnunlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEU_THICH", 0); // Cập nhật trạng thái thích = 0
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{maso.getText().toString()});
                btnlike.setVisibility(View.VISIBLE);
                btnunlike.setVisibility(View.GONE);
                // Cần refresh lại listview yêu thích nếu đang ở tab đó hoặc khi người dùng chuyển tab
                if(context instanceof MainActivity){
                    ((MainActivity)context).addyeuthich(); // Cập nhật lại danh sách yêu thích
                }
            }
        });

        // Xử lý click vào mỗi dòng tiêu đề bài hát trên listview
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titteude.setTextColor(Color.RED);
                maso.setTextColor(Color.RED);

                Intent intent = new Intent(context, activitysub.class);
                Bundle bundle = new Bundle();
                bundle.putString("maso", maso.getText().toString());
                intent.putExtra("package", bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}