package com.example.appdoctruyen.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.object.HistoryItem;

import java.util.List;

public class AdapterHistory extends BaseAdapter {
        private Context context;
        private List<HistoryItem> historyItems;

        public AdapterHistory(Context context, List<HistoryItem> historyItems) {
            this.context = context;
            this.historyItems = historyItems;
        }

        @Override
        public int getCount() {
            return historyItems.size();
        }

        @Override
        public Object getItem(int position) {
            return historyItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
            }

            // Lấy các thành phần trong layout
            ImageView imgTruyen = convertView.findViewById(R.id.imgTruyen);
            TextView txtTenTruyen = convertView.findViewById(R.id.txtTenTruyen);
            TextView txtThoiGianDoc = convertView.findViewById(R.id.txtThoiGianDoc);

            // Lấy dữ liệu từ đối tượng HistoryItem
            HistoryItem item = historyItems.get(position);
            txtTenTruyen.setText(item.getStoryTitle());
            txtThoiGianDoc.setText(item.getReadTime()); // Lưu ý: Cần đảm bảo thuộc tính này tồn tại trong class HistoryItem

            // Tải hình ảnh cho imgTruyen nếu cần (có thể sử dụng thư viện như Glide hoặc Picasso)
            // Glide.with(context).load(item.getImageUrl()).into(imgTruyen); // Nếu bạn có URL hình ảnh

            return convertView;
        }
    }

