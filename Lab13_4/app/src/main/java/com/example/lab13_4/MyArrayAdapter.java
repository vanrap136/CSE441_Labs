package com.example.lab13_4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Image> {
    Context context;
    int layoutId;
    List<Image> arr;

    public MyArrayAdapter(Context context, int layoutId, List<Image> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.arr = arr;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layoutId, null);

        ImageView imgView = convertView.findViewById(R.id.imageView);
        TextView txtView = convertView.findViewById(R.id.textView);

        Image image = arr.get(position);
        imgView.setImageResource(image.getImg());
        txtView.setText(image.getName());

        return convertView;
    }
}