package com.example.lab13_3;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Phone> {
    private final Activity context;
    private final int idlayout;
    private final List<Phone> mylist;

    public MyArrayAdapter(Activity context, int idlayout, List<Phone> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(idlayout, null);
        Phone myphone = mylist.get(position);

        ImageView imgphone = convertView.findViewById(R.id.imgphone);
        TextView txtnamephone = convertView.findViewById(R.id.txtnamephone);

        imgphone.setImageResource(myphone.getImagephone());
        txtnamephone.setText(myphone.getNamephone());

        return convertView;
    }
}