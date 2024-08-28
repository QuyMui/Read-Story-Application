package com.example.appdoctruyen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.object.Truyen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adaptertruyen extends ArrayAdapter<Truyen> {
    private final Context ct;
    private final List<Truyen> arr;
    private final int resource;

    public adaptertruyen(@NonNull Context context, int resource, @NonNull List<Truyen> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.resource = resource;
        this.arr = objects;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Truyen getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtTentruyen;
        ImageView imgtruyen;
    }

    public void sortTruyen(String s) {
        s = s.toUpperCase();
        int k = 0;
        for (int i = 0; i < arr.size(); i++) {
            Truyen t = arr.get(i);
            String ten = t.getTenTruyen().toUpperCase();
            if (ten.contains(s)) {
                arr.set(i, arr.get(k));
                arr.set(k, t);
                k++;
            }
        }
        notifyDataSetChanged();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);

            viewHolder.txtTentruyen = convertView.findViewById(R.id.txtTenTruyen);
            viewHolder.imgtruyen = convertView.findViewById(R.id.imgTruyen);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu
        Truyen truyen = getItem(position);
        viewHolder.txtTentruyen.setText(truyen.getTenTruyen());

        Picasso.get().load(truyen.getAnh())
                .placeholder(R.drawable.baseline_cloud_download_24)
                .error(R.drawable.baseline_image_not_supported_24)
                .into(viewHolder.imgtruyen);

        return convertView;
    }
}
