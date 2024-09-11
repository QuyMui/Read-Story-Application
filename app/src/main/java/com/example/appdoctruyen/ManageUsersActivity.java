// ManageUsersActivity.java
package com.example.appdoctruyen;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.UserAdapter;
import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.object.TaiKhoan;

import java.util.ArrayList;

public class ManageUsersActivity extends AppCompatActivity {

    private DatabaseDocTruyen database;
    private ArrayList<TaiKhoan> userList;
    private UserAdapter userAdapter;
    private ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        userListView = findViewById(R.id.userListView);
        database = new DatabaseDocTruyen(this);

        loadUsers();
    }

    private void loadUsers() {
        userList = new ArrayList<>();
        Cursor cursor = database.getAllUsers(); // Lấy tất cả người dùng từ database
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String email = cursor.getString(2);
                int role = cursor.getInt(3);

                userList.add(new TaiKhoan(id, username, "", email, role));
            } while (cursor.moveToNext());
        }

        userAdapter = new UserAdapter(this, userList);
        userListView.setAdapter(userAdapter);

        // Xử lý sự kiện khi nhấn giữ vào item
        userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showUserOptionsDialog(position);
                return true;
            }
        });
    }

    private void showUserOptionsDialog(final int position) {
        final TaiKhoan user = userList.get(position);
        CharSequence[] options = {"Cập nhật quyền", "Xóa người dùng"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hành động");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    toggleUserRole(user);
                } else if (which == 1) {
                    deleteUser(user);
                }
            }
        });
        builder.show();
    }

    private void toggleUserRole(TaiKhoan user) {
        int newRole = (user.getmPhanQuyen() == 1) ? 2 : 1; // Đổi quyền giữa User và Admin
        database.updateUserRole(user.getmId(), newRole);
        Toast.makeText(this, "Cập nhật quyền thành công", Toast.LENGTH_SHORT).show();
        loadUsers(); // Tải lại danh sách người dùng
    }

    private void deleteUser(TaiKhoan user) {
        database.deleteUser(user.getmId());
        Toast.makeText(this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
        loadUsers(); // Tải lại danh sách người dùng
    }
}
