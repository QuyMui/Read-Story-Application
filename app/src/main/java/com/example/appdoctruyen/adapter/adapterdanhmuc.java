package com.example.appdoctruyen.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.object.DanhMuc;

import java.util.ArrayList;

public class adapterdanhmuc extends ArrayAdapter<DanhMuc> {
        public adapterdanhmuc(Context context, ArrayList<DanhMuc> categories) {
            super(context, 0, categories);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
            }

            DanhMuc danhMuc = getItem(position);

            TextView txtCategoryName = convertView.findViewById(R.id.txtCategoryName);
            txtCategoryName.setText(danhMuc.getTenDanhMuc());

            return convertView;
        }
    }


