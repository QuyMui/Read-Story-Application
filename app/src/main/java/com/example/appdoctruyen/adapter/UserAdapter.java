// UserAdapter.java
package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.object.TaiKhoan;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<TaiKhoan> userList;

    public UserAdapter(Context context, List<TaiKhoan> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_item, null);
        }

        TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView roleTextView = convertView.findViewById(R.id.roleTextView);

        TaiKhoan user = userList.get(position);
        usernameTextView.setText(user.getmTenTaiKhoan());
        emailTextView.setText(user.getmEmail());
        roleTextView.setText(user.getmPhanQuyen() == 2 ? "Admin" : "User");

        return convertView;
    }
}
