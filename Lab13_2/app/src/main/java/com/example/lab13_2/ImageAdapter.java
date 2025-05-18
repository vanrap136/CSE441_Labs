package com.example.lab13_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private final Context context;
    private final String[] itemNames;
    private final int[] imageIds;

    public ImageAdapter(Context context, String[] itemNames, int[] imageIds) {
        this.context = context;
        this.itemNames = itemNames;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public Object getItem(int position) {
        return itemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.grid_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(imageIds[position]);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}