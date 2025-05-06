package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<ImageItem> {

    private Context context;
    private List<ImageItem> items;

    public ImageAdapter(Context context, List<ImageItem> items) {
        super(context, R.layout.item_image, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_image, parent, false);

            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageItem currentItem = items.get(position);

        if (currentItem.getBitmap() != null) {
            holder.imageView.setImageBitmap(currentItem.getBitmap());
        } else {
            holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.textView.setText(currentItem.getDescription());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}