package com.example.lab14_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    Context context;
    int LayoutId;
    List<Item> myArray;

    public MyArrayAdapter(Context context, int LayoutId, List<Item> arr) {
        super(context, LayoutId, arr);
        this.context = context;
        this.LayoutId = LayoutId;
        this.myArray = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(LayoutId, null);

        Item item = myArray.get(position);

        TextView txtmaso = convertView.findViewById(R.id.btmaso);
        TextView txttieude = convertView.findViewById(R.id.bttieude);
        ImageButton btnlike = convertView.findViewById(R.id.btnlike);

        txtmaso.setText(item.getMaso());
        txttieude.setText(item.getTieude());

        if (item.getThich() == 1) {
            btnlike.setImageResource(R.drawable.like);
        } else {
            btnlike.setImageResource(R.drawable.unlike);
        }

        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getThich() == 0) {
                    btnlike.setImageResource(R.drawable.like);
                    item.setThich(1);
                    Toast.makeText(context, "Đã thích: " + item.getTieude(), Toast.LENGTH_SHORT).show();
                } else {
                    btnlike.setImageResource(R.drawable.unlike);
                    item.setThich(0);
                    Toast.makeText(context, "Bỏ thích: " + item.getTieude(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
}